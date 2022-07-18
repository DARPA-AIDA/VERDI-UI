package com.ncc.verdi.data;

import java.io.IOException;
import java.time.Instant;
import java.util.Collection;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.Set;
import java.util.function.BiConsumer;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.ncc.verdi.model.ClaimFrameProvenanceObjectValues;
import com.ncc.verdi.model.InlineResponse2001;
import com.ncc.verdi.model.TA3Event;

import org.apache.http.HttpHost;
import org.apache.jena.rdf.model.RDFNode;
import org.apache.lucene.search.TotalHits;
import org.elasticsearch.ElasticsearchStatusException;
import org.elasticsearch.action.admin.indices.delete.DeleteIndexRequest;
import org.elasticsearch.action.bulk.BulkRequest;
import org.elasticsearch.action.bulk.BulkResponse;
import org.elasticsearch.action.get.GetRequest;
import org.elasticsearch.action.get.GetResponse;
import org.elasticsearch.action.index.IndexRequest;
import org.elasticsearch.action.search.SearchRequest;
import org.elasticsearch.action.search.SearchResponse;
import org.elasticsearch.action.update.UpdateRequest;
import org.elasticsearch.action.update.UpdateResponse;
import org.elasticsearch.client.RequestOptions;
import org.elasticsearch.client.RestClient;
import org.elasticsearch.client.RestClientBuilder;
import org.elasticsearch.client.RestHighLevelClient;
import org.elasticsearch.client.indices.CreateIndexRequest;
import org.elasticsearch.client.indices.GetIndexRequest;
import org.elasticsearch.common.xcontent.XContentBuilder;
import org.elasticsearch.common.xcontent.XContentFactory;
import org.elasticsearch.index.query.BoolQueryBuilder;
import org.elasticsearch.index.query.Operator;
import org.elasticsearch.index.query.QueryBuilder;
import org.elasticsearch.index.query.QueryBuilders;
import org.elasticsearch.index.query.TermQueryBuilder;
import org.elasticsearch.script.Script;
import org.elasticsearch.search.SearchHit;
import org.elasticsearch.search.builder.SearchSourceBuilder;
import org.elasticsearch.search.sort.FieldSortBuilder;
import org.elasticsearch.search.sort.SortOrder;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.lang.NonNull;
import org.springframework.stereotype.Component;

@Component
public class ESHelper {
    //=-=-=-=-=-=-=-=-=-=-=-=-=-=-=
    // Elasticsearch Constants
    // ----------------------------
    public enum Status {
        IN_PROGRESS, COMPLETE, NEEDS_UPDATE;

        public boolean isStatus(final String toTest) {
            return this.toString().equals(toTest);
        }
    }
    public static final String UPDATE_TIME_FIELD = "updateTime";
    public static final String STATUS_INDEX = "status", STATUS_FIELD = STATUS_INDEX;
    public static final String STATUS_ID = "1";
    public static final String ALIAS_FIELD = "alias";
    public static final String TYPES_SUFFIX = "-types";
    public static final String NAMES_SUFFIX = "-arg-names";
    public static final String ARG_TYPES_SUFFIX = "-arg-types";
    public static final String ID_FIELD = "id";
    public static final List<String> CATEGORIES = List.of("Event", "Relation");
    public static final List<String> SUFFIXES = List.of(TYPES_SUFFIX, NAMES_SUFFIX, ARG_TYPES_SUFFIX);
    public static final String NEPTUNE_ADDRESS = "http://prototype-ui.cbbg7lviorzp.us-east-1.neptune.amazonaws.com:8182";
    public static final String NAMES_INDEX = "all-names";
    public static final String SINS_INDEX = "all-events";

    //=-=-=-=-=-=-=-=-=-=-=-=-=-=-=
    // Elasticsearch Support
    // ----------------------------
    private final RestClientBuilder clientBuilder;

    public ESHelper(
        @Value("${elastic.scheme:http}") String scheme,
        @Value("${elastic.host:localhost}") String hostname,
        @Value("${elastic.port:9200}") int port) {
            clientBuilder = RestClient.builder(new HttpHost(hostname, port, scheme));
    }

    public Map<String, Object> getStatus() throws IOException {
        try (RestHighLevelClient client = new RestHighLevelClient(clientBuilder)) {
            GetResponse response = client.get(new GetRequest(STATUS_INDEX).id(STATUS_ID), RequestOptions.DEFAULT);
            if (response.isExists()) {
                return response.getSourceAsMap();
            }
        } catch (ElasticsearchStatusException e) {
            if (e.toString().contains("reason=no such index")) {
                return new HashMap<>();
            } else {
                throw e;
            }
        }
        // if index or document not found, return empty map
        return new HashMap<>();
    }

    public static String getIndexPrefix(Integer alias, String category) {
        return alias + "-" + category.toLowerCase();
    }

    public SearchRequest getSearchRequest(String graph, String category,
            String suffix, QueryBuilder builder, int dataLimit) throws IOException {
        Integer alias = DataUtils.getFromJSON(getStatus(), graph, ALIAS_FIELD);

        if (alias == null) {
            throw new IOException(String.format("Unable to find '%s' index for %s", category, graph));
        }

        return new SearchRequest(getIndexPrefix(alias, category) + suffix)
                .source(new SearchSourceBuilder().query(builder).size(dataLimit));
    }

    public SearchRequest getSearchRequest(RestHighLevelClient client, String graph, String category,
            QueryBuilder builder, int dataLimit) throws IOException {
        return getSearchRequest(graph, category, "", builder, dataLimit);
    }

    public <T> List<T> getClusters(String graph, String category, String type, String argument, int dataLimit,
                                          ObjectMapper objectMapper, Class<T> c) throws IOException {
        QueryBuilder builder;
        if (type == null && argument == null) {
            builder = QueryBuilders.matchAllQuery();
        } else {
            BoolQueryBuilder temp = QueryBuilders.boolQuery();
            if (argument != null) {
                temp.filter(QueryBuilders.matchQuery("names", argument).operator(Operator.AND));
            }
            if (type != null) {
                temp.filter(QueryBuilders.matchQuery("types", type).operator(Operator.AND));
            }
            builder = temp;
        }

        try (RestHighLevelClient client = new RestHighLevelClient(clientBuilder)) {
            SearchRequest request = getSearchRequest(client, graph, category, builder, dataLimit);
            SearchResponse response = client.search(request, RequestOptions.DEFAULT);
            return Stream.of(response.getHits().getHits()).map(hit -> {
                try {
                        return objectMapper.readValue(hit.getSourceAsString(), c);
                    } catch (Exception e) {
                        // don't add
                    }
                    return null;
            }).filter(Objects::nonNull).collect(Collectors.toList());
        }
    }

    public <T> List<T> getClustersByMultipleQueryInputs(String graph, String category, List<String> arguments, List<String> types, int dataLimit,
                                          ObjectMapper objectMapper, Class<T> c) throws IOException {
        QueryBuilder builder;

        if (types == null && arguments == null) {
            builder = QueryBuilders.matchAllQuery();
        } else {
            BoolQueryBuilder filterCondition = QueryBuilders.boolQuery();
            BiConsumer<String, List<String>> createFilter = (fieldName, list) -> {
                if (list.size() == 1) {
                    filterCondition.filter(QueryBuilders.matchQuery(fieldName, list.get(0)).operator(Operator.OR));
                } else {
                    BoolQueryBuilder valueCondition = QueryBuilders.boolQuery();
                    for (String entry : list) {
                        valueCondition.should(QueryBuilders.matchQuery(fieldName, entry).operator(Operator.OR));
                    }
                    filterCondition.filter(valueCondition);
                }
            };
            if (arguments != null) {
                createFilter.accept("names", arguments);
            }
            if (types != null) {
                createFilter.accept("types", types);
            }

            builder = filterCondition;
        }

        try (RestHighLevelClient client = new RestHighLevelClient(clientBuilder)) {
            SearchRequest request = getSearchRequest(client, graph, category, builder, dataLimit);
            SearchResponse response = client.search(request, RequestOptions.DEFAULT);
            return Stream.of(response.getHits().getHits()).map(hit -> {
                try {
                    return objectMapper.readValue(hit.getSourceAsString(), c);
                } catch (Exception e) {
                    // don't add
                }
                return null;
            }).filter(Objects::nonNull).collect(Collectors.toList());
        }
    }

    public List<String> autocomplete(String graph, String category, String toComplete,
            String indexSuffix, int dataLimit) throws IOException {
        QueryBuilder builder;
        if (toComplete == null || toComplete.isEmpty()) {
            builder = QueryBuilders.matchAllQuery();
        } else {
            builder = QueryBuilders.matchQuery(ID_FIELD + ".complete", toComplete).operator(Operator.AND);
        }
        SearchRequest request = getSearchRequest(graph, category, indexSuffix, builder, dataLimit);
        request.source().sort(new FieldSortBuilder(ID_FIELD + ".raw").order(SortOrder.ASC));
        try (RestHighLevelClient client = new RestHighLevelClient(clientBuilder)) {
            SearchResponse response = client.search(request, RequestOptions.DEFAULT);
            return Stream.of(response.getHits().getHits())
                .map(hit -> (String) hit.getSourceAsMap().get(ID_FIELD))
                .filter(Objects::nonNull)
                .collect(Collectors.toList());
        }
    }

    // TODO: instead of doing two calls, just make limit = 10k
    private List<SearchHit> getHits(String index, QueryBuilder builder, int limit) throws IOException {
        SearchRequest request = new SearchRequest(index).source(new SearchSourceBuilder().query(builder).size(limit));
        try (RestHighLevelClient client = new RestHighLevelClient(clientBuilder)) {
            SearchResponse response = client.search(request, RequestOptions.DEFAULT);
            return List.of(response.getHits().getHits());
        }
    }

    private List<TA3Event> parseHitsIntoEvents(Collection<SearchHit> hits, ObjectMapper mapper) {
        return hits.stream()
                .map(hit -> {
                    try {
                        return mapper.readValue(hit.getSourceAsString(), TA3Event.class);
                    } catch (IOException e) {
                        return null;
                    }
                })
                .filter(Objects::nonNull)
                .collect(Collectors.toList());
    }
    //TODO: Removed Hypothesis references. The code will most likely need to be refactored since we are using the claim frames structure now.
    public List<TA3Event> getEvents(String index, List<ClaimFrameProvenanceObjectValues> values, ObjectMapper mapper) throws IOException {
        int limit = 10000;
        if (values == null) {
            return parseHitsIntoEvents(getHits(index, QueryBuilders.matchAllQuery(), limit), mapper);
        } else {
            Set<SearchHit> hits = new HashSet<>();
            for (ClaimFrameProvenanceObjectValues value : values) {
                TermQueryBuilder keId = value.getKeId() == null ? null : QueryBuilders.termQuery("cluster.keyword", value.getKeId());
                if (keId == null) {
                    hits.addAll(getHits(index, keId, limit));
                } else {
                    BoolQueryBuilder argBuilder = QueryBuilders.boolQuery();
                    if (value.getKeId() != null) {
                        argBuilder.filter(keId);
                    }
                    for (String type : value.getTypes()) {
                        argBuilder.filter(QueryBuilders.termQuery("cluster_type.keyword", type));
                    }

                    hits.addAll(getHits(index, argBuilder, limit));
                }
            }
            return parseHitsIntoEvents(hits, mapper);
        }
    }

    // Any limit bigger than 10k requires paging: https://www.elastic.co/guide/en/elasticsearch/reference/current/paginate-search-results.html
    private SearchResponse getAutoCompleteResponse(String index, String toComplete, @NonNull List<String> eventTypes,
        @NonNull List<String> roles, int limit) throws IOException {

        QueryBuilder builder;
        boolean hasToComplete = !(toComplete == null || toComplete.isEmpty());
        if (!hasToComplete && eventTypes.isEmpty() && roles.isEmpty()) {
            builder = QueryBuilders.matchAllQuery();
        } else {
            BoolQueryBuilder boolBuilder = QueryBuilders.boolQuery();
            if (hasToComplete) {
                boolBuilder.filter(QueryBuilders.matchQuery(ID_FIELD + ".complete", toComplete).operator(Operator.AND));
            }
            for (String eventType : eventTypes) {
                boolBuilder.filter(QueryBuilders.matchQuery("eventTypes", eventType).operator(Operator.OR));
            }
            for (String role : roles) {
                boolBuilder.filter(QueryBuilders.matchQuery("roles", role).operator(Operator.OR));
            }
            builder = boolBuilder;
        }

        SearchRequest request = new SearchRequest(index).source(new SearchSourceBuilder().query(builder).size(limit));
        request.source().sort(new FieldSortBuilder(ID_FIELD + ".raw").order(SortOrder.ASC));
        try (RestHighLevelClient client = new RestHighLevelClient(clientBuilder)) {
            return client.search(request, RequestOptions.DEFAULT);
        }
    }

    public TotalHits getAutoCompleteCount(String index, String toComplete, @NonNull List<String> eventTypes,
        @NonNull List<String> roles) throws IOException {

        return getAutoCompleteResponse(index, toComplete, eventTypes, roles, 1).getHits().getTotalHits();
    }

    public List<InlineResponse2001> multiSelectAutoComplete(String index, String toComplete, @NonNull List<String> eventTypes,
        @NonNull List<String> roles, int limit, ObjectMapper mapper) throws IOException {

        return Stream.of(getAutoCompleteResponse(index, toComplete, eventTypes, roles, limit).getHits().getHits())
            .map(hit -> {
                try {
                     return mapper.readValue(hit.getSourceAsString(), InlineResponse2001.class);
                } catch (IOException e) {
                    return null;
                }
            })
            .filter(Objects::nonNull)
            .collect(Collectors.toList());
    }

    public Map<Boolean, Set<String>> removeGraph(String graph) throws IOException {
        Map<Boolean, Set<String>> tracker = new HashMap<>();
        Map<String, Object> statusMap = getStatus();
        Map<String, Object> value = DataUtils.getFromJSON(statusMap, graph);
        for (String category : CATEGORIES) {
            if (DataUtils.getFromJSON(value, category) != null) {
                String indexPrefix = getIndexPrefix(DataUtils.getFromJSON(value, ESHelper.ALIAS_FIELD), category);
                trackDeleteIndex(tracker, indexPrefix);
                for (String suffix : SUFFIXES) {
                    trackDeleteIndex(tracker, indexPrefix + suffix);
                }
            }
        }
        tracker.computeIfAbsent(removeStatus(graph) != null, key -> new HashSet<>()).add(graph);
        return tracker;
    }

    public UpdateResponse updateStatus(RDFNode graph, String category, int alias, Status status) throws IOException {
        // Wrapping graph entries allows for merging when using updateRequest
        XContentBuilder builder = XContentFactory.jsonBuilder()
                .startObject()
                .startObject(graph.toString()) // start graph
                .field(ALIAS_FIELD, alias)
                .startObject(category) // start category
                .field(STATUS_FIELD, status)
                .timeField(UPDATE_TIME_FIELD, Instant.now())
                .endObject() // end category
                .endObject() // end graph
                .endObject();

        try (RestHighLevelClient client = new RestHighLevelClient(clientBuilder)) {
            return client.update(new UpdateRequest(STATUS_INDEX, STATUS_ID).doc(builder).docAsUpsert(true), RequestOptions.DEFAULT);
        }
    }

    public UpdateResponse removeStatus(String graph) throws IOException {
        Script script = new Script(String.format("ctx._source.entrySet().removeIf(e -> e.getKey() === '%s')", graph));
        try (RestHighLevelClient client = new RestHighLevelClient(clientBuilder)) {
            return client.update(new UpdateRequest(ESHelper.STATUS_INDEX, ESHelper.STATUS_ID).script(script), RequestOptions.DEFAULT);
        } catch (ElasticsearchStatusException e) {
            if (e.getDetailedMessage().contains("type=document_missing_exception")) {
                // ignore: without status, nothing to remove
                return null;
            } else {
                throw e;
            }
        }
    }

    private Map<Boolean, Set<String>> trackDeleteIndex(Map<Boolean, Set<String>> tracker, String index) throws IOException {
        tracker.computeIfAbsent(deleteIndex(index), tmp -> new HashSet<>()).add(index);
        return tracker;
    }

    public Map<Boolean, Set<String>> deleteAllIndices() throws IOException {
        Map<Boolean, Set<String>> ret = new HashMap<>();
        Map<String, Object> statusMap = getStatus();
        for (String key : statusMap.keySet()) {
            Map<String, Object> value = DataUtils.getFromJSON(statusMap, key);
            for (String category : CATEGORIES) {
                if (DataUtils.getFromJSON(value, category) != null) {
                    String indexPrefix = getIndexPrefix(DataUtils.getFromJSON(value, ALIAS_FIELD), category);
                    trackDeleteIndex(ret, indexPrefix);
                    for (String suffix : SUFFIXES) {
                        trackDeleteIndex(ret, indexPrefix + suffix);
                    }
                }
            }
        }
        return trackDeleteIndex(ret, STATUS_INDEX);
    }

    public Map<Boolean, Set<String>> deleteIndices(String... indices) throws IOException {
        Map<Boolean, Set<String>> ret = new HashMap<>();
        for (String index : indices) {
            trackDeleteIndex(ret, index);
        }
        return ret;
    }

    private boolean deleteIndex(String index) throws IOException {
        try (RestHighLevelClient client = new RestHighLevelClient(clientBuilder)) {
            if (client.indices().exists(new GetIndexRequest(index), RequestOptions.DEFAULT)) {
                client.indices().delete(new DeleteIndexRequest(index), RequestOptions.DEFAULT);
                return true;
            } else {
                return false;
            }
        }
    }

    private static class StringHasBuilder implements HasBuilder {
        String value;
        private StringHasBuilder(String value) {
            this.value = value;
        }

        @Override
        public XContentBuilder getBuilder() throws IOException {
            return XContentFactory.jsonBuilder()
                .startObject()
                .field(ESHelper.ID_FIELD, value)
                .endObject();
        }

        @Override
        public String getId() {
            return value;
        }
    }

    public void writeStrings(BulkRequest request, String index, Set<String> strings, BiConsumer<String, String> handler) {
        List<StringHasBuilder> toAdd =  strings.stream().map(StringHasBuilder::new).collect(Collectors.toList());
        writeBuilders(request, index, toAdd, handler);
    }

    public <T extends HasBuilder> void writeBuilders(BulkRequest request, String index, Collection<T> hasBuilders, BiConsumer<String, String> handler) {
        hasBuilders.stream().forEach(hasBuilder -> {
            try {
                request.add(new IndexRequest(index).id(hasBuilder.getId()).source(hasBuilder.getBuilder()));
            } catch (IOException e) {
                handler.accept(index, hasBuilder.getId());
            }
        });
    }

    public void applySettingsToIndex(String index, XContentBuilder settingsBuilder, XContentBuilder mappingsBuilder) throws IOException {
        try (RestHighLevelClient client = new RestHighLevelClient(clientBuilder)) {
            CreateIndexRequest request = new CreateIndexRequest(index);
            if (settingsBuilder != null) {
                request.settings(settingsBuilder);
            }
            if (mappingsBuilder != null) {
                request.mapping(mappingsBuilder);
            }
            client.indices().create(request, RequestOptions.DEFAULT);
        }
    }

    public void applySettingsToIndices(String prefix, XContentBuilder settingsBuilder, XContentBuilder mappingsBuilder) throws IOException {
        for (String suffix : SUFFIXES) {
            applySettingsToIndex(prefix + suffix, settingsBuilder, mappingsBuilder);
        }
    }

    public BulkResponse sendBulk(BulkRequest request) throws IOException {
        try (RestHighLevelClient client = new RestHighLevelClient(clientBuilder)) {
            return client.bulk(request, RequestOptions.DEFAULT);
        }
    }

    public static interface HasBuilder {
        public XContentBuilder getBuilder() throws IOException ;
        public String getId();
    }
}
