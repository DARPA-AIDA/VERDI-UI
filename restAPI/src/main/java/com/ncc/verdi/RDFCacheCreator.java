package com.ncc.verdi;

import java.io.IOException;
import java.io.InputStream;
import java.time.Duration;
import java.time.Instant;
import java.util.Collections;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.Properties;
import java.util.Set;
import java.util.function.BiConsumer;
import java.util.function.Function;
import java.util.logging.Level;
import java.util.logging.Logger;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import com.ncc.verdi.data.DataUtils;
import com.ncc.verdi.data.ESHelper;
import com.ncc.verdi.data.ESHelper.HasBuilder;
import com.ncc.verdi.data.RDFHelper;

import org.apache.jena.query.QuerySolution;
import org.apache.jena.rdf.model.RDFNode;
import org.apache.jena.rdf.model.impl.ResourceImpl;
import org.elasticsearch.action.bulk.BulkRequest;
import org.elasticsearch.common.xcontent.XContentBuilder;
import org.elasticsearch.common.xcontent.XContentFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.web.client.ResourceAccessException;

/**
 * Class used to populate sort/autocomplete data in elasticsearch:
 *   event/relation clusterIds, prototypes, and types
 *   argument names and types
 */
@Component
public class RDFCacheCreator {
    //=-=-=-=-=-=-=-=-=-=-=-=-=-=-=
    // Constants
    // ----------------------------
    private static final Logger LOGGER = Logger.getLogger(RDFCacheCreator.class.getName());

    /**
     * Data class to contain information about a cluster
     */
    public static class ClusterInfo implements HasBuilder {
        private final RDFNode cluster, prototype;
        private final Set<RDFNode> types = new HashSet<>();
        private final Set<String> argumentNames = new HashSet<>();
        private final Set<String> argumentTypes = new HashSet<>();
        private int argumentCount = 0;

        ClusterInfo(RDFNode cluster, RDFNode prototype) {
            this.cluster = cluster;
            this.prototype = prototype;
        }
        ClusterInfo addType(RDFNode type) {
            if (type != null) {
                types.add(type);
            }
            return this;
        }
        RDFNode getPrototype() {
            return prototype;
        }
        Stream<RDFNode> getTypes() {
            return types.stream();
        }
        Stream<String> getArgumentNames() {
            return argumentNames.stream();
        }
        Stream<String> getArgumentTypes() {
            return argumentTypes.stream();
        }
        int getArgumentCount() {
            return argumentCount;
        }
        void addArgumentNames(QuerySolution qs) {
            argumentNames.addAll(DataUtils.getList(qs, "names"));
        }
        void addArgumentTypes(QuerySolution qs) {
            argumentTypes.addAll(DataUtils.getList(qs, "types"));
        }
        void addArgumentInformation(QuerySolution qs) {
            addArgumentNames(qs);
            addArgumentTypes(qs);
            argumentCount++;
        }
        public XContentBuilder getBuilder() throws IOException {
            return XContentFactory.jsonBuilder()
                .startObject()
                .field("cluster", cluster.toString())
                .field("prototype", prototype.toString())
                .array("types", getTypes().map(RDFNode::toString).toArray(String[]::new))
                .array("names", getArgumentNames().toArray(String[]::new))
                .field("argumentCount", getArgumentCount())
                .endObject();
        }
        @Override
        public String getId() {
            return cluster.toString();
        }
    }

    public static class DataSet {
        private final RDFNode graph;
        private final String category;
        private final Map<RDFNode, ClusterInfo> clusters = new HashMap<>();

        public DataSet(RDFNode graph, String category) {
            this.graph = graph;
            this.category = category;
        }

        void addClusterInformation(QuerySolution qs) {
            RDFNode cluster = qs.get("cluster");
            RDFNode prototype = qs.get("prototype");
            RDFNode type = qs.get("type");

            clusters.computeIfAbsent(cluster, key -> new ClusterInfo(key, prototype))
                    .addType(type);
        }

        private Set<String> getSet(Function<ClusterInfo, Stream<? extends Object>> supplier) {
            return clusters.values().stream().flatMap(supplier).map(Object::toString).collect(Collectors.toSet());
        }

        Set<String> getArgumentNames() {
            return getSet(ClusterInfo::getArgumentNames);
        }
        Set<String> getArgumentTypes() {
            return getSet(ClusterInfo::getArgumentTypes);
        }
        Set<String> getTypes() {
            return getSet(ClusterInfo::getTypes);
        }

        Map<RDFNode, ClusterInfo> getClusters() {
            return clusters;
        }

        RDFNode getGraph() {
            return graph;
        }

        String getCategory() {
            return category;
        }
    }

    //=-=-=-=-=-=-=-=-=-=-=-=-=-=-=
    // Instance
    // ----------------------------
    private final ESHelper esHelper;
    private final RDFHelper rdfHelper;

    @Autowired
    public RDFCacheCreator(RDFHelper rdfHelper, ESHelper esHelper) {
        this.rdfHelper = rdfHelper;
        this.esHelper = esHelper;
    }

    public void populateGraphs(List<RDFNode> import_graphs) throws ResourceAccessException, IOException {
        Map<String, Object> statusMap = esHelper.getStatus();

        for (RDFNode graph : import_graphs) {
            for (String category : ESHelper.CATEGORIES) {
                String status = DataUtils.getFromJSON(statusMap, graph.toString(), category, ESHelper.STATUS_FIELD);
                if (status == null || ESHelper.Status.NEEDS_UPDATE.isStatus(status) || ESHelper.Status.IN_PROGRESS.isStatus(status)) {
                    if (status != null) {
                        logTracker(esHelper.removeGraph(graph.toString()));
                    }
                    saveToElasticSearch(getDataSet(graph, category));
                } else {
                    log(Level.INFO, "Status = %s; Skipping %s for %s", status, category, graph);
                }
            }
        }
    }

    /**
     * Populate all data required for sort/autocomplete
     * @return {@link DataSet} containing clusters and arguments
     */
    public DataSet getDataSet(RDFNode graph, String category) throws ResourceAccessException {
        String clusterTemplate, argumentTemplate;
        clusterTemplate = DataUtils.getResourceStringFrom("sparql/cache-clusters.sparql");
        argumentTemplate = DataUtils.getResourceStringFrom("sparql/prototype-arguments.sparql");

        log(Level.INFO, "Getting %ss for %s...", category, graph);
        DataSet dataSet = new DataSet(graph, category);
        Instant start = Instant.now();
        String queryString = replaceType(clusterTemplate, graph, category);
        rdfHelper.executeQueryString(queryString, dataSet::addClusterInformation);
        int size = dataSet.clusters.size();
        log(Level.INFO, "  Found: %d (%s)", size, Duration.between(start, Instant.now()));

        log(Level.INFO, "Getting %s arguments for %s...", category, graph);
        start = Instant.now();
        int i = 0;
        for (ClusterInfo cluster: dataSet.clusters.values()) {
            queryString = replaceCluster(argumentTemplate, graph, cluster.cluster);
            rdfHelper.executeQueryString(queryString, cluster::addArgumentInformation);
            if (++i % 500 == 0) {
                log(Level.INFO, "  Processed: %d (%s)", i, Duration.between(start, Instant.now()));
            }
        }
        log(Level.INFO, String.format("  %d names %d types (%s)",
                dataSet.getArgumentNames().size(),
                dataSet.getArgumentTypes().size(),
                Duration.between(start, Instant.now())));

        return dataSet;
    }

    public void saveToElasticSearch(DataSet dataset) throws IOException {
        String graph = dataset.graph.toString();
        String category = dataset.category;
        Map<String, Object> status = esHelper.getStatus();

        Integer alias = DataUtils.getFromJSON(status, graph, ESHelper.ALIAS_FIELD);

        // Get 1 or max + 1
        if (alias == null) {
            alias = status.keySet()
                .stream()
                .map(key -> (Integer) DataUtils.getFromJSON(status, key,ESHelper. ALIAS_FIELD)).filter(Objects::nonNull)
                .max(Integer::compare)
                .orElse(0) + 1;
        }
        String prefix = ESHelper.getIndexPrefix(alias, category);

        BulkRequest request = new BulkRequest();

        // Add event/relation clusters to the clusters index (add category and graph)
        esHelper.writeBuilders(request, prefix, dataset.clusters.values(), (index, id) ->
            log(Level.SEVERE, "Unable to write cluster '%s' to index '%s'", id, index));

        BiConsumer<String, String> handler = (index, string) ->
            log(Level.SEVERE, "Unable to write string '%s' to index '%s'", string, index);

        // Add event/relation types to the types index (add category and graph)
        esHelper.writeStrings(request, prefix + ESHelper.TYPES_SUFFIX, dataset.getTypes(), handler);

        // Add event/relation argumentNames to argument-names index (add category and graph)
        esHelper.writeStrings(request, prefix + ESHelper.NAMES_SUFFIX, dataset.getArgumentNames(), handler);

        // Add event/relation argumentTypes to argument-types index (add category and graph)
        esHelper.writeStrings(request, prefix + ESHelper.ARG_TYPES_SUFFIX, dataset.getArgumentTypes(), handler);

        try {
            // Capture status before creating indices so know to remove them later
            esHelper.updateStatus(dataset.graph, category, alias, ESHelper.Status.IN_PROGRESS);
        } catch (IOException e) {
            log(Level.SEVERE, e.getMessage());
        }

        // Create indices w/ mappings to support bulk request
        XContentBuilder settingsBuilder = XContentFactory.jsonBuilder()
            .startObject()
            .startObject("analysis")
            .startObject("analyzer")
            .startObject("autocomplete")
            .field("tokenizer", "autocomplete")
            .array("filter", "lowercase")
            .endObject()
            .startObject("autocomplete_search")
            .field("tokenizer", "lowercase")
            .endObject()
            .endObject()
            .startObject("tokenizer")
            .startObject("autocomplete")
            .field("type", "edge_ngram")
            .field("min_gram", 2)
            .field("max_gram", 25)
            .array("token_chars", "letter")
            .endObject()
            .endObject()
            .endObject()
            .endObject();
        XContentBuilder mappingsBuilder = XContentFactory.jsonBuilder()
            .startObject()
            .startObject("properties")
            .startObject(ESHelper.ID_FIELD)
            .field("type", "text")
            .startObject("fields")
            .startObject("complete")
            .field("type", "text")
            .field("analyzer", "autocomplete")
            .field("search_analyzer", "autocomplete_search")
            .endObject()
            .startObject("raw")
            .field("type", "keyword")
            .endObject()
            .endObject()
            .endObject()
            .endObject()
            .endObject();

        esHelper.applySettingsToIndices(prefix, settingsBuilder, mappingsBuilder);

        log(Level.INFO, "Sending bulk %ss for %s", category, graph);
        esHelper.sendBulk(request);
        esHelper.updateStatus(dataset.graph, category, alias, ESHelper.Status.COMPLETE);
        log(Level.INFO, "Completed %ss for %s", category, graph);
    }

    private static void log(Level level, String template, Object... params) {
        LOGGER.log(level, String.format(template, params));
    }

    private String replaceType(String template, RDFNode graph, String ereType) {
        Map<String, String> replacements = DataUtils.getFromReplacements(graph.toString());
        replacements.put("ereType", ereType);
        return DataUtils.replaceMultiple(template, replacements);
    }

    private String replaceCluster(String template, RDFNode graph, RDFNode cluster) {
        Map<String, String> replacements = DataUtils.getFromReplacements(graph.toString());
        replacements.put("cluster", cluster.toString());
        return DataUtils.replaceMultiple(template, replacements);
    }

    private void logTracker(Map<Boolean, Set<String>> tracker) {
        for (String index : tracker.getOrDefault(true, Collections.emptySet())) {
            log(Level.INFO, "Deleted index %s", index);
        }
        for (String index : tracker.getOrDefault(false, Collections.emptySet())) {
            log(Level.WARNING, "Unable to delete index %s", index);
        }
    }

    public void deleteAllIndices() throws IOException {
        logTracker(esHelper.deleteAllIndices());
    }

    public void run(String... args) throws IOException {
        if (args.length == 0) {
            populateGraphs(rdfHelper.getGraphs());
        } else {
            if (args[0].compareToIgnoreCase("listgraphs") >= 0) {
                System.out.println(rdfHelper.getGraphs());
            } else {
                // TODO: use --force or something to indicate that graphs should be overwritten
                populateGraphs(Stream.of(args).map(ResourceImpl::new).collect(Collectors.toList()));
            }
        }
    }

    public static void main(String[] args) throws ResourceAccessException, IOException {
        Properties props = new Properties();
        try (InputStream input = RDFCacheCreator.class.getClassLoader().getResourceAsStream("application.properties")) {
            props.load(input);
        }
        ESHelper esHelper = new ESHelper(
            props.getProperty("elastic.scheme", "http"),
            props.getProperty("elastic.host", "localhost"),
            Integer.parseInt(props.getProperty("elastic.port", "9200"))
        );
        RDFHelper rdfHelper = new RDFHelper(
            props.getProperty("triplestore.scheme", "http"),
            props.getProperty("triplestore.host", "localhost"),
            Integer.parseInt(props.getProperty("triplestore.port", "9999")),
            props.getProperty("triplestore.query.namespace", "blazegraph/sparql")
        );
        new RDFCacheCreator(rdfHelper, esHelper).run(args);
    }
}
