package com.ncc.verdi.api;

import java.io.IOException;
import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.stream.Stream;

import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;


import java.util.HashMap;
//import com.github.andrewoma.dexx.collection.HashMap;
import com.ncc.verdi.data.DataUtils;
import com.ncc.verdi.data.KGTKHelper;
import com.ncc.verdi.data.RDFHelper;
import com.ncc.verdi.model.ClaimFrame;
import com.ncc.verdi.model.ClaimFrameComponentObject;
import com.ncc.verdi.model.ClaimFrameComponentObjectValues;
import com.ncc.verdi.model.ClaimFrameDetail;
import com.ncc.verdi.model.ClaimFrameFilterObject;
import com.ncc.verdi.model.ClaimFrameProvenanceObject;
import com.ncc.verdi.model.ClaimFrameProvenanceObjectValues;
import com.ncc.verdi.model.ClaimFrameRelationObject;
import com.ncc.verdi.model.ClaimFrameRelationObjectValues;
import com.ncc.verdi.model.ClaimFrameTopic;
import com.ncc.verdi.model.DocObject;
import com.ncc.verdi.model.Event;
import com.ncc.verdi.model.LDCTime;
import com.ncc.verdi.model.NodeDetail;

import org.apache.jena.query.QuerySolution;
import org.apache.jena.rdf.model.RDFNode;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class ClaimframeApiController implements ClaimframeApi {
    private static final Logger log = LoggerFactory.getLogger(ClaimframeApiController.class);
    private static final String ta3URIRegex = DataUtils.getDataSource() + "TA3/.*";
    private final HttpServletRequest request;
    private enum ClaimObjectTypes {COMPONENT, RELATION, PROVENANCE};
    private enum ClaimObjectProperties {
        LOCATION(ClaimObjectTypes.COMPONENT, "claimLocation"),
        XVARIABLE(ClaimObjectTypes.COMPONENT, "xVariable"),
        CLAIMER(ClaimObjectTypes.COMPONENT, "claimer"),
        CLAIMERAFFILIATION(ClaimObjectTypes.COMPONENT, "claimerAffiliation"),
        MEDIUM(ClaimObjectTypes.COMPONENT, "claimMedium"),
        IDENTICAL(ClaimObjectTypes.RELATION, "identicalClaims"),
        REFUTING(ClaimObjectTypes.RELATION, "refutingClaims"),
        RELATED(ClaimObjectTypes.RELATION, "relatedClaims"),
        SUPPORTING(ClaimObjectTypes.RELATION, "supportingClaims"),
        ASSOCIATEDKES(ClaimObjectTypes.PROVENANCE, "associatedKEs"),
        SEMANTICS(ClaimObjectTypes.PROVENANCE, "claimSemantics");

        private ClaimObjectTypes type;
        private String value;

        private ClaimObjectProperties(ClaimObjectTypes type, String value) {
            this.type = type;
            this.value = value;
        }


        public ClaimObjectTypes getType() {
            return type;
        }

        public String getValue() {
            return value;
        }

        public static List<String> getValuesByType(ClaimObjectTypes type) {
            List<String> values = new ArrayList<>();
            for (ClaimObjectProperties o : ClaimObjectProperties.values()) {
                if(o.type.equals(type)) {
                    values.add(o.value);
                }
            }
            return values;
        }
    };

    @Autowired private RDFHelper rdfHelper;
    @Autowired private KGTKHelper kgtkHelper;

    @Autowired
    public ClaimframeApiController(HttpServletRequest request) {
        this.request = request;
    }

    @Override
    public ResponseEntity<List<ClaimFrame>> claimFrames(@Valid ClaimFrameFilterObject claimFrameFilterObject) {
        for (MediaType mediaType: MediaType.parseMediaTypes(request.getHeader("Accept"))) {
            if (mediaType.isCompatibleWith(MediaType.valueOf("application/json"))) {
                try {
                    List<String> queryGraphs = new ArrayList<>();
                    queryGraphs = List.of(getAllTA3ClaimFrameGraphs(rdfHelper));

                    List<ClaimFrame> ret = getClaimFrames(rdfHelper, queryGraphs, claimFrameFilterObject.getBaseGraphs(), claimFrameFilterObject.getTopics(), claimFrameFilterObject.getSubtopics(), claimFrameFilterObject.getQueryClaimIds());

                    return new ResponseEntity<>(ret, HttpStatus.OK);

                } catch (IOException e) {
                    log.error("Couldn't serialize response for content type application/json", e);
                    return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
                }
            }
        }
        return new ResponseEntity<>(HttpStatus.NOT_IMPLEMENTED);
    }

    @Override
    public ResponseEntity<ClaimFrameDetail> claimFrameDetails(String claimId) {
        for (MediaType mediaType: MediaType.parseMediaTypes(request.getHeader("Accept"))) {
            if (mediaType.isCompatibleWith(MediaType.valueOf("application/json"))) {
                try {
                    List<String> queryGraphs = getClaimFrameGraphsAsLists(rdfHelper, claimId);
                    ClaimFrameDetail ret = getClaimFrameDetails(rdfHelper, queryGraphs, claimId);
                    return new ResponseEntity<>(ret, HttpStatus.OK);

                } catch (IOException e) {
                    log.error("Couldn't serialize response for content type application/json", e);
                    return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
                }
            }
        }
        return new ResponseEntity<>(HttpStatus.NOT_IMPLEMENTED);
    }

    @Override
    public ResponseEntity<NodeDetail> qNodeDetails(String qnodeId) {
        for (MediaType mediaType: MediaType.parseMediaTypes(request.getHeader("Accept"))) {
            if (mediaType.isCompatibleWith(MediaType.valueOf("application/json"))) {
                try {
                    NodeDetail ret = kgtkHelper.qNodeLookup(qnodeId);
                    return new ResponseEntity<>(ret, HttpStatus.OK);

                } catch (Exception e) {
                    log.error("Couldn't serialize response for content type application/json", e);
                    return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
                }
            }
        }
        return new ResponseEntity<>(HttpStatus.NOT_IMPLEMENTED);
    }

    public List<ClaimFrame> getClaimFrames(RDFHelper rdfHelper, List<String> graphs, List<String> baseGraphs, List<String> topics, List<String> subTopics, List<String> queryClaimIds) throws IOException {
        Map<String, String> replacements = new HashMap<>();
        List<ClaimFrame> claimFrames = new ArrayList<>();

        String topicString = "{ ?claimURI aida:topic \"%s\". }";
        replacements.put("topicFilter", topics == null ?  "" :
            DataUtils.getFilterString(topics, str -> String.format(topicString, str)));

        String subTopicString = "{ ?claimURI aida:subtopic \"%s\". }";
        replacements.put("subTopicFilter", subTopics == null ?  "" :
            DataUtils.getFilterString(subTopics, str -> String.format(subTopicString, str)));

        String baseGraphsString = "contains(str(?graph), \"%s\") ";
        replacements.put("baseGraphFilter", baseGraphs == null ?  "" : 
            DataUtils.getFilterStringFilterOr(baseGraphs, str -> String.format(baseGraphsString, str)));    
            //DataUtils.getFilterString(baseGraphs, str -> String.format(baseGraphsString, str)));            

        String queryClaimIdString = "{ ?claimURI aida:queryClaimId \"%s\". }";
        replacements.put("queryClaimIdFilter", queryClaimIds == null ?  "" : 
            DataUtils.getFilterString(queryClaimIds, str -> String.format(queryClaimIdString, str)));    

        String query = DataUtils.replaceMultiple(DataUtils.getResourceStringFrom("sparql/claim-frames.sparql"), replacements);

        System.out.println(query);

        rdfHelper.executeQueryString(query, qs -> {
            DocObject document = DataUtils.getDocInfo(qs.get("sourceDoc").toString());
            String claimURI = qs.get("claimURI").toString();
            ClaimFrame claim = new ClaimFrame()

                    .claimId(qs.get("claimId").toString())
                    .queryClaimId((qs.get("queryClaimId") == null) ? "" : qs.get("queryClaimId").toString())
                    .ranking((qs.get("ranking") == null) ? "" : qs.get("ranking").toString())
                    .claimRelations((qs.get("claimRelations") == null) ? "" : qs.get("claimRelations").toString())
                    .claimURI(claimURI)
                    .claimTemplate(qs.get("claimTemplate").toString())
                    .topic(qs.get("topic").toString())
                    .subtopic(qs.get("subtopic").toString())
                    .queryId((qs.get("queryId") == null) ? "" : qs.get("queryId").toString())
                    .epistemic((qs.get("epistemic") == null) ? "" : qs.get("epistemic").toString())
                    .sourceDoc((qs.get("sourceDoc") == null) ? "" : qs.get("sourceDoc").toString())
                    .sourceDocTitle((document.getTitle() == null) ? "" : document.getTitle())
                    .description(qs.get("description").toString())
                    .claimer((qs.get("claimer") == null) ? "" : qs.get("claimer").toString())
                    .claimerKE((qs.get("claimerKE") == null) ? "" : qs.get("claimerKE").toString())
                    .locationName((qs.get("locationName") == null) ? "" : qs.get("locationName").toString())
                    .xVariable((qs.get("xVariable") == null) ? "" : qs.get("xVariable").toString())
                    .xVariableCompId((qs.get("xVariableCompIds") == null) ? "" : qs.get("xVariableCompIds").toString())
                    .claimerAffiliation((qs.get("claimerAffiliation") == null) ? "" : qs.get("claimerAffiliation").toString())
                    .importance(new BigDecimal(qs.get("importance") == null ? 0 : qs.get("importance").asLiteral().getDouble()).setScale(2,
                            RoundingMode.CEILING));

            claim.setDates(createLDCTimes(qs));

            //TODO: Why is identicalClaims called out here?
            if(qs.get("identicalClaims") != null){
                String list = (String) qs.get("identicalClaims").asNode().getLiteralValue();
                //  node -> label -> value

                if(list.length() > 0){
                    for (String dupClaims : list.split(",")) {
                        if (dupClaims.trim().length() == 0) {
                            break;
                        }
                        ClaimFrameRelationObject relationObject = new ClaimFrameRelationObject();
                        relationObject.setPropertyName("identicalClaims");
                        ClaimFrameRelationObjectValues component = new ClaimFrameRelationObjectValues();
                        component.setClaimId(dupClaims);
                        relationObject.addValuesItem(component);
                        claim.addRelationsItem(relationObject);
                    }
                }
            }

            claimFrames.add(claim);
        });

        return claimFrames;
    }

    public ClaimFrameDetail getClaimFrameDetails(RDFHelper rdfHelper, List<String> graphs, String claimId) throws IOException {
        Map<String, String> replacements = DataUtils.getFromReplacements(graphs.toArray(String[]::new));
        ClaimFrameDetail claimFrameDetails = new ClaimFrameDetail();
        replacements.put("claimId", claimId);

        String query = DataUtils.replaceMultiple(DataUtils.getResourceStringFrom("sparql/claim-frame-details.sparql"), replacements);

        // System.out.println(query);

        rdfHelper.executeQueryString(query, qs -> {
            DocObject document = DataUtils.getDocInfo(qs.get("sourceDocument").toString());
            String claimURI = qs.get("claimURI").toString();

            claimFrameDetails
                .claimId(claimId)
                .claimURI(claimURI)
                .claimTemplate(qs.get("claimTemplate").toString())
                .topic(qs.get("topic").toString())
                .subtopic(qs.get("subtopic").toString())
                .queryId((qs.get("queryId") == null) ? "" : qs.get("queryId").toString())
                .description(qs.get("description").toString())
                .sourceDocument(document)
                .system(qs.get("system").toString())
                .epistemic(qs.get("epistemic").toString())
                .sentiment(qs.get("sentiment").toString())
                .importance(new BigDecimal(qs.get("importance").asLiteral().getDouble()).setScale(2, RoundingMode.CEILING))
                .setDates(createLDCTimes(qs));

            for(ClaimObjectTypes type : ClaimObjectTypes.values()) {
                List<String> properties = ClaimObjectProperties.getValuesByType(type);
                for (String property: properties) {
                    if(type.equals(ClaimObjectTypes.COMPONENT)) {
                        claimFrameDetails.addComponentsItem(
                            getClaimComponents(rdfHelper, claimURI, graphs, DataUtils.getList(qs, property), property)
                        );
                    // } else if (type.equals(ClaimObjectTypes.RELATION)) {
                    //     claimFrameDetails.addRelationsItem(
                    //         getClaimRelations(rdfHelper, DataUtils.getList(qs, property), property)
                    //     );
                    } else if (type.equals(ClaimObjectTypes.PROVENANCE)) {
                        claimFrameDetails.addProvenancesItem(
                            getClaimProvenances(rdfHelper, DataUtils.getList(qs, property), property)
                        );
                    }
                }
            }
        });
        return claimFrameDetails;
    }


    private static ClaimFrameComponentObject getClaimComponents(RDFHelper rdfHelper, String claimURI, List<String> graphs, List<String> claimComponentURIs, String propertyName) {
        Map<String, String> replacements = DataUtils.getFromReplacements(graphs.toArray(String[]::new));
        ClaimFrameComponentObject componentObject = new ClaimFrameComponentObject();
        componentObject.setPropertyName(propertyName);
        replacements.put("claimURI", claimURI);

        for (String componentURI : claimComponentURIs) {
            ClaimFrameComponentObjectValues  component = new ClaimFrameComponentObjectValues();
            component.setComponentURI(componentURI);
            replacements.put("componentURI", componentURI);
            String keQuery = DataUtils.replaceMultiple(DataUtils.getResourceStringFrom("sparql/get-claim-component.sparql"), replacements);

            // System.out.println(keQuery);

            rdfHelper.executeQueryString(keQuery, qs -> {
                component.setComponentId(qs.get("componentIdentity").toString());
                component.setComponentName(qs.get("componentName").toString());
                component.setComponentKE((qs.get("componentKE") == null) ? "" : qs.get("componentKE").toString());
                component.setComponentTypes(DataUtils.getList(qs, "componentTypes"));
                component.setComponentProvenance((qs.get("componentProvenance") == null) ? "" : qs.get("componentProvenance").toString());
                component.setSystem((qs.get("system") == null) ? "" : qs.get("system").toString());
            });
            componentObject.addValuesItem(component);
        }
        return componentObject;
    }

    //TODO: We should just have a generic getComponent Function
    private static ClaimFrameRelationObject getClaimRelations(RDFHelper rdfHelper, List<String> claimRelationIds, String propertyName) {
        ClaimFrameRelationObject relationObject = new ClaimFrameRelationObject();
        relationObject.setPropertyName(propertyName);

        for (String claimId : claimRelationIds) {
            List<String> queryGraphs = getClaimFrameGraphsAsLists(rdfHelper, claimId);
            Map<String, String> replacements = DataUtils.getFromReplacements(queryGraphs.toArray(String[]::new));

            ClaimFrameRelationObjectValues component = new ClaimFrameRelationObjectValues();
            component.setClaimId(claimId);
            replacements.put("claimId", claimId);
            String keQuery = DataUtils.replaceMultiple(DataUtils.getResourceStringFrom("sparql/get-claim-relation.sparql"), replacements);




            rdfHelper.executeQueryString(keQuery, qs -> {
                component.setClaimURI(qs.get("claimURI").toString());
                component.setTopic(qs.get("topic").toString());
                component.setSubtopic(qs.get("subtopic").toString());
                component.setClaimTemplate(qs.get("claimTemplate").toString());
                component.setDescription(qs.get("description").toString());
                component.setxVariable((qs.get("xVariable") == null) ? "" : qs.get("xVariable").toString());
                component.setClaimLocation((qs.get("claimLocation") == null) ? "" : qs.get("claimLocation").toString());
                component.setImportance(new BigDecimal(qs.get("importance").asLiteral().getDouble()).setScale(2, RoundingMode.CEILING));
                component.setDates(createLDCTimes(qs));
            });
            
            relationObject.addValuesItem(component);
        }
        return relationObject;
    }

    private static ClaimFrameProvenanceObject getClaimProvenances(RDFHelper rdfHelper, List<String> provenanceKEs, String propertyName) {
        ClaimFrameProvenanceObject provenanceObject = new ClaimFrameProvenanceObject();
        provenanceObject.setPropertyName(propertyName);

        for (String provenanceKE : provenanceKEs) {
            ClaimFrameProvenanceObjectValues component = new ClaimFrameProvenanceObjectValues();
            component.setKeId(provenanceKE);
            Event ke = EventApiController.getEvent(rdfHelper, provenanceKE, "");
            if (ke != null) {
                component.setCategory(ke.getCategory());
                component.setTypes(ke.getPrototype().getTypes());
            }
            provenanceObject.addValuesItem(component);
        }
        return provenanceObject;
    }

    private static List<LDCTime> createLDCTimes(QuerySolution qs){
        List<LDCTime> dates = new ArrayList<LDCTime>();

        String startAfterYear   = DataUtils
                .getBlankIfNull(qs.get("startAfterYear"))
                .replace("^^http://www.w3.org/2001/XMLSchema#gYear", "");
        String startAfterMonth  = DataUtils
                .getBlankIfNull(qs.get("startAfterMonth"))
                .replace("^^http://www.w3.org/2001/XMLSchema#gMonth", "");
        String startAfterDay    = DataUtils
                .getBlankIfNull(qs.get("startAfterDay"))
                .replace("^^http://www.w3.org/2001/XMLSchema#gDay", "");

        String startBeforeYear  = DataUtils
                .getBlankIfNull(qs.get("startBeforeYear"))
                .replace("^^http://www.w3.org/2001/XMLSchema#gYear", "");
        String startBeforeMonth = DataUtils
                .getBlankIfNull(qs.get("startBeforeMonth"))
                .replace("^^http://www.w3.org/2001/XMLSchema#gMonth", "");
        String startBeforeDay   = DataUtils
                .getBlankIfNull(qs.get("startBeforeDay"))
                .replace("^^http://www.w3.org/2001/XMLSchema#gDay", "");

        String endAfterYear     = DataUtils
                .getBlankIfNull(qs.get("endAfterYear"))
                .replace("^^http://www.w3.org/2001/XMLSchema#gYear", "");
        String endAfterMonth    = DataUtils
                .getBlankIfNull(qs.get("endAfterMonth"))
                .replace("^^http://www.w3.org/2001/XMLSchema#gMonth", "");
        String endAfterDay      = DataUtils
                .getBlankIfNull(qs.get("endAfterDay"))
                .replace("^^http://www.w3.org/2001/XMLSchema#gDay", "");

        String endBeforeYear    = DataUtils
                .getBlankIfNull(qs.get("endBeforeYear"))
                .replace("^^http://www.w3.org/2001/XMLSchema#gYear", "");
        String endBeforeMonth   = DataUtils
                .getBlankIfNull(qs.get("endBeforeMonth"))
                .replace("^^http://www.w3.org/2001/XMLSchema#gMonth", "");
        String endBeforeDay     = DataUtils.getBlankIfNull(qs.get("endBeforeDay"))
                .replace("^^http://www.w3.org/2001/XMLSchema#gDay", "");

        LDCTime time = new LDCTime();
        time.setStartAfter(startAfterYear + startAfterMonth + startAfterDay);
        time.setStartBefore(startBeforeYear + startBeforeMonth + startBeforeDay);
        time.setEndAfter(endAfterYear + endAfterMonth + endAfterDay);
        time.setEndBefore(endBeforeYear + endBeforeMonth + endBeforeDay);

        dates.add(time);
        return dates;
    }

    private static void populateDates(RDFHelper rdfHelper, ClaimFrame claim, List<String> graphs) {
        claim.dates(getFormattedDates(rdfHelper, graphs, claim.getClaimURI()));
    }

    private static void populateDates(RDFHelper rdfHelper, ClaimFrameDetail claimDetails, List<String> graphs) {
        claimDetails.dates(getFormattedDates(rdfHelper, graphs, claimDetails.getClaimURI()));
    }

    private static void populateDates(RDFHelper rdfHelper, ClaimFrameRelationObjectValues claimRelation, List<String> graphs) {
        claimRelation.dates(getFormattedDates(rdfHelper, graphs, claimRelation.getClaimURI()));
    }

    private static List<LDCTime> getFormattedDates(RDFHelper rdfHelper, List<String> graphs, String claimURI) {
        Map<String, String> replacements = DataUtils.getFromReplacements(graphs.toArray(String[]::new));
        replacements.put("subject", claimURI);
        replacements.put("timeProperty", "aida:claimDateTime");
        String dateQuery = DataUtils.replaceMultiple(DataUtils.getResourceStringFrom("sparql/get-times.sparql"), replacements);
        List<LDCTime> dateItems = new ArrayList<>();
        rdfHelper.executeQueryString(dateQuery, qs -> {
            List<String> dates = DataUtils.getList(qs, "dates");
            if (!dates.isEmpty()) {
                LDCTime time = DataUtils.getFormattedDate(dates);
                dateItems.add(time);
            }
        });
        return dateItems;
    }

    @Override
    public ResponseEntity<List<ClaimFrameTopic>> claimFrameTopicalList(){
        List<ClaimFrameTopic> listings = new ArrayList<ClaimFrameTopic>();
        String listingQuery = DataUtils.getResourceStringFrom("sparql/claim-frame-topical-listings.sparql");

        rdfHelper.executeQueryString(listingQuery, qs -> {
            ClaimFrameTopic claimFrameProperties = new ClaimFrameTopic();

            claimFrameProperties.setBaseGraph(qs.get("baseGraph").toString());
            claimFrameProperties.setTopic(qs.get("topic").toString());
            claimFrameProperties.setSubtopic(qs.get("subtopic").toString());
            claimFrameProperties.setTemplate(qs.get("claimTemplate").toString());
            claimFrameProperties.setQueryClaimId(qs.get("queryClaimId").toString());
            claimFrameProperties.setDescription(qs.get("description").toString());

            listings.add(claimFrameProperties);
        });

        return new ResponseEntity<>(listings, HttpStatus.OK);
    }

    public static String[] getAllTA3ClaimFrameGraphs(RDFHelper rdfHelper) {
        return rdfHelper.getGraphs().stream().map(RDFNode::toString).
                filter(g -> g.matches(ta3URIRegex)).toArray(String[]::new);
    }

    public static Optional<String> getTA3ClaimFrameGraphByClaimId(RDFHelper rdfHelper, String id) {
        Stream<String> graphs = rdfHelper.getGraphs().stream().map(RDFNode::toString).
                filter(g -> g.matches(ta3URIRegex + id));
        return graphs.findFirst();
    }

    public static List<String> getClaimFrameGraphsAsLists(RDFHelper rdfHelper, String claimId) {
        List<String> graphs = new ArrayList<>();
        Optional<String> graph = getTA3ClaimFrameGraphByClaimId(rdfHelper, claimId);

        if (graph.isPresent()) {
            graphs = List.of(graph.get());
        } else {
            //TODO: we should review this???
            graphs = List.of(getAllTA3ClaimFrameGraphs(rdfHelper));
        }
        return graphs;
    }
}
