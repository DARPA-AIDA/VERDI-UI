package com.ncc.verdi.caching;

import java.io.IOException;
import java.io.InputStream;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Properties;
import java.util.Set;
import java.util.SortedSet;
import java.util.TreeSet;
import java.util.logging.Level;
import java.util.logging.Logger;
import java.util.stream.Collectors;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.ncc.verdi.data.ESHelper;
import com.ncc.verdi.data.ESHelper.HasBuilder;
import com.ncc.verdi.data.RDFHelper;
import com.ncc.verdi.data.DataUtils;
import com.ncc.verdi.model.ClaimFrameProvenanceObjectValues;
import com.ncc.verdi.model.Doc;
import com.ncc.verdi.model.DocObject;
import com.ncc.verdi.model.TA3Event;

import org.apache.jena.rdf.model.RDFNode;
import org.elasticsearch.action.bulk.BulkRequest;
import org.elasticsearch.common.xcontent.XContentBuilder;
import org.elasticsearch.common.xcontent.XContentFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.web.client.ResourceAccessException;

@Component
public class TA3CacheCreator {
    //=-=-=-=-=-=-=-=-=-=-=-=-=-=-=
    // Constants
    // ----------------------------
    //TODO: TA3 Caching code will need to be refactored since we are no longer getting event information from sins and hypotheses
    private static final Logger LOGGER = Logger.getLogger(TA3CacheCreator.class.getName());

    private Map<String, Event> events = new HashMap<>();
    private Map<String, Entity> entities = new HashMap<>();

    private RDFHelper rdfHelper;
    private ESHelper esHelper;

    public static class NamesDataSet implements HasBuilder {
        String name;
        SortedSet<String> entities = new TreeSet<>();
        SortedSet<String> roles = new TreeSet<>();
        SortedSet<String> eventTypes = new TreeSet<>();

        public NamesDataSet(String name) {
            this.name = name;
        }

        public void addEntity(Entity entity) {
            eventTypes.addAll(entity.getEventTypes());
            roles.addAll(entity.getRoles());
            /* entities.add(entity.getId()); */
        }

        public XContentBuilder getBuilder() throws IOException {
            return XContentFactory.jsonBuilder()
                .startObject()
                .field(ESHelper.ID_FIELD, name)
                .array("entities", entities.toArray(String[]::new))
                .array("eventTypes", eventTypes.toArray(String[]::new))
                .array("roles", roles.toArray(String[]::new))
                .endObject();
        }

        @Override
        public String getId() {
            return name;
        }
    }

    @Autowired
    public TA3CacheCreator(RDFHelper rdfHelper, ESHelper esHelper) {
        this.rdfHelper = rdfHelper;
        this.esHelper = esHelper;
    }

    private static String getRoot(String graph) {
        return graph.substring(0, graph.lastIndexOf('/'));
    }

    // expected format for ta3Run=RunGraphRoot
    // ex root:  https://www.nextcentury.com/TA3/E201/BBN-20210222/COLORADO-20210106/BBN-20201223
    // ex graph: https://www.nextcentury.com/TA3/E201/BBN-20210222/COLORADO-20210106/BBN-20201223/AIDA_M36_TA3_E201.F1.HN000
    public void process(String... ta3Runs) throws IOException {

        try {
            this.deleteAllIndices();
        } catch (IOException e) {
            LOGGER.log(Level.SEVERE, "Unable to delete TA3 Indicies");
        }


        Set<String> runs = Set.of(ta3Runs);
        Set<String> graphs = rdfHelper.getGraphs().stream()
            .map(RDFNode::toString)
            .filter(graph -> runs.contains(getRoot(graph)))
            .collect(Collectors.toSet());

        log(Level.INFO, "Found %d graphs", graphs.size());
        int count = 1;
        for (String graph : graphs) {
            String root = getRoot(graph);
            List<ClaimFrameProvenanceObjectValues> values = new ArrayList<>();
            log(Level.INFO, "Processing graph %d / %d", count++, graphs.size());
            // sin and run
            root = root.replaceAll("^.*/TA3/", "");
            String[] splits = root.split("/", 2);
            String claim = splits[0];
            String run = splits[1];


            // add events
            for (ClaimFrameProvenanceObjectValues value : values) {
                String category = value.getCategory();
                if (category.contains("#Event")) {
                    String id = Event.getEventId(claim, run, value.getKeId());
                    Event event = events.computeIfAbsent(id, key -> {
                        try {
                            return new Event(claim, run, value.getKeId(), entities);
                        } catch (IOException e) {
                            LOGGER.log(Level.SEVERE, "Unable to process graph: " + graph, e);
                        }
                        return null;
                    });
                    List<Doc> docs = getDocs(rdfHelper, value.getKeId(), event.getRoles(), List.of(graph));
                    for (Doc doc : docs) {
                        Entity arg = entities.get(doc.getQueryObject());
                        if (arg != null) {
                            for (DocObject dObj : doc.getDocs()) {
                                event.addDoc(dObj, arg);
                            }
                        }
                    }
                } else if (category.contains("#Relation")) {
                    values.add(value);
                } else {
                    log(Level.WARNING, "Non-event/relation member: %s %s %s %s", claim, run, category);
                }
            }
        }
        BulkRequest request = new BulkRequest();
/*         String index = ESHelper.SINS_INDEX;
        esHelper.writeBuilders(request, index, events.values(), (passedIndex, id) ->
            log(Level.SEVERE, "Unable to add event '%s' to index '%s'", id, passedIndex));
        try {
            esHelper.applySettingsToIndex(index, null, eventMapping);
        } catch (IOException e) {
            LOGGER.log(Level.SEVERE, "Unable to apply mapping to index " + index, e);
        }

        Map<String, NamesDataSet> names = new HashMap<>();
        for (Entity entity : entities.values()) {
            for (String name : entity.getNames()) {
                names.computeIfAbsent(name, NamesDataSet::new).addEntity(entity);
            }
        }
        log(Level.INFO, "Found %d names", names.size());

        index = ESHelper.NAMES_INDEX;
        esHelper.writeBuilders(request, index, names.values(), (passedIndex, id) ->
            log(Level.SEVERE, "Unable to ingest name '%s' into index '%s'", id, passedIndex));
        try {
            esHelper.applySettingsToIndex(index, nameSettings, nameMapping);
        } catch (IOException e) {
            LOGGER.log(Level.SEVERE, "Unable to apply settings to index " + index, e);
        } */

        LOGGER.log(Level.INFO, "Sending bulk request");
        try {
            esHelper.sendBulk(request);
            LOGGER.log(Level.INFO, "Completed bulk request");
        } catch (IOException e) {
            LOGGER.log(Level.SEVERE, "Failure while sending bulk request", e);
        }
    }

    public Map<String, Event> getEvents() {
        return events;
    }

    public Map<String, Entity> getEntities() {
        return entities;
    }

/*     private static final XContentBuilder eventMapping = getEventMapping();
    private static XContentBuilder getEventMapping() {
        try {
            return XContentFactory.jsonBuilder()
                .startObject()
                .startObject("properties")
                .startObject("entities")
                .field("type", "nested")
                .endObject()
                .endObject()
                .endObject();
        } catch (IOException e) {
            LOGGER.log(Level.SEVERE, "Unable to create Event mapping", e);
            return null;
        }
    } */
/* 
    private static final XContentBuilder nameSettings = getNamesSettings();
    private static XContentBuilder getNamesSettings() {
        try {
            return XContentFactory.jsonBuilder()
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
        } catch (IOException e) {
            LOGGER.log(Level.SEVERE, "Unable to create Name settings", e);
            return null;
        }
    } */
/* 
    private static final XContentBuilder nameMapping = getNameMapping();
    private static XContentBuilder getNameMapping() {
        try {
            return XContentFactory.jsonBuilder()
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
        } catch (IOException e) {
            LOGGER.log(Level.SEVERE, "Unable to create Name mapping", e);
            return null;
        }
    } */

    private static void log(Level level, String template, Object... params) {
        LOGGER.log(level, String.format(template, params));
    }

    public void deleteAllIndices() throws IOException {
        Map<Boolean, Set<String>> tracker = esHelper.deleteIndices( ESHelper.NAMES_INDEX, ESHelper.SINS_INDEX );
        for (String index : tracker.getOrDefault(true, Collections.emptySet())) {
            log(Level.INFO, "Deleted index %s", index);
        }
        for (String index : tracker.getOrDefault(false, Collections.emptySet())) {
            log(Level.WARNING, "Unable to delete index %s", index);
        }
    }

    public static void main(String[] args) throws ResourceAccessException, IOException {
        Properties props = new Properties();
        try (InputStream input = TA3CacheCreator.class.getClassLoader().getResourceAsStream("application.properties")) {
            props.load(input);
        }
        ESHelper esHelper = new ESHelper(
            props.getProperty("elastic.scheme", "http"),
            props.getProperty("elastic.host", "localhost"),
            Integer.parseInt(props.getProperty("elastic.port", "9200"))
        );
/*         RDFHelper rdfHelper = new RDFHelper(
            props.getProperty("triplestore.scheme", "http"),
            props.getProperty("triplestore.host", "localhost"),
            Integer.parseInt(props.getProperty("triplestore.port", "9999")),
            props.getProperty("triplestore.query.namespace", "blazegraph/sparql")
        ); */

        //Example added to get around errors
        List<ClaimFrameProvenanceObjectValues> values = List.of(
            new ClaimFrameProvenanceObjectValues().types(List.of("Conflict.Attack", "Life.Die.DeathCausedByViolentEvents"))
                .category("Event")
        );
        List<TA3Event> events = esHelper.getEvents("all-events", values, new ObjectMapper());
        System.out.println(events.size());
        Files.write(Paths.get("test.txt"),
            events.stream().map(Object::toString).collect(Collectors.toList()),
            StandardCharsets.UTF_8);
    }

    public static List<Doc> getDocs(RDFHelper rdfHelper, String id, List<String> roles, List<String> graphs) throws ResourceAccessException {
        Map<String, Doc> docs = new HashMap<>();
        List<DocObject> docArray = new ArrayList<>();
        Map<String, String> replacements = DataUtils.getFromReplacements(graphs.toArray(String[]::new));

        replacements.put("cluster", id);
        replacements.put("predicate", "http://www.w3.org/1999/02/22-rdf-syntax-ns#type");
        String template = DataUtils.getResourceStringFrom("sparql/get-sin-docs.sparql");
        String typeQuery = DataUtils.replaceMultiple(template, replacements);

        //collect event or relation docs
        rdfHelper.executeQueryString(typeQuery, qs -> {
            docs.computeIfAbsent(id, key -> new Doc())
            .queryObject(id) //event or relation id
            .docs(docArray);
        });

        //collect entity docs
        for (String role : roles) {
            replacements.put("predicate", role);
            String roleQuery = DataUtils.replaceMultiple(template, replacements);

            rdfHelper.executeQueryString(roleQuery, qs -> {
                docs.computeIfAbsent(qs.get("object").toString(), key -> new Doc().queryObject(key))
                    .queryObject(qs.get("object").toString()) //entity id
                    .docs(DataUtils.getDocDocsList(qs, "docs"));
            });
        }
        return new ArrayList<>(docs.values());
    }


}
