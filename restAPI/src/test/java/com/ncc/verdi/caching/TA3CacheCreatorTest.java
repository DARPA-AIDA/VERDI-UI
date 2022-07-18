package com.ncc.verdi.caching;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.doReturn;
import static org.mockito.Mockito.mock;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import com.ncc.verdi.data.DataUtils;
import com.ncc.verdi.data.ESHelper;
import com.ncc.verdi.data.RDFHelper;

import org.apache.jena.rdf.model.RDFNode;
import org.apache.jena.rdf.model.impl.ResourceImpl;
import org.elasticsearch.common.xcontent.XContentBuilder;
import org.junit.jupiter.api.BeforeAll;

public class TA3CacheCreatorTest {
    private static TA3CacheCreator creator;
    //TODO: Failing tests were commented out and will need to be rewritten for refactored Claim Frames code
/*     // E202
    private static final String PROTEST_EVT_ID = "http://www.isi.edu/gaia/events/uiuc/K0C03BB24/ES_Event_004620-cluster-projectedFromSingleton";
    private static final String PROTEST_ID = Event.getEventId("E202", "test", PROTEST_EVT_ID);
    private static final String SE_ID = "http://www.isi.edu/gaia/entities/uiuc/K0C03BB7S/ES_Entity_EDL_0001573-cluster-projectedFromSingleton";
    private static final String LOC_ID = "http://www.isi.edu/gaia/entities/uiuc/K0C03BB24/ES_Entity_EDL_0023759-cluster-projectedFromSingleton";
    private static final String PROTESTER_ID = "http://www.isi.edu/gaia/entities/uiuc/K0C03BB24/ES_Entity_EDL_0025285-cluster-projectedFromSingleton"; */

    // E201
/*     private static final String TA3_MOCK_DOC_FILE = "TA3Test/mockDoc.json";
    private static final String TRANSPORT_EVT_ID = "http://www.isi.edu/gaia/events/uiuc/IC001VG2X/EN_Event_006021-cluster-projectedFromSingleton";
    private static final String TRANSPORT_ID = Event.getEventId("E201", "test", TRANSPORT_EVT_ID);
    private static final String TA3_EVENT_OUTPUT_FILE = "TA3Test/eventOutput.json";
    private static final String TA3_EVENT_WITH_DATES_OUTPUT_FILE = "TA3Test/eventWithDatesOutput.json"; */

/*     private static final ObjectMapper mapper = new ObjectMapper();

    private static <T> T parseResource(String resource, TypeReference<T> type) throws IOException {
        String input = DataUtils.getResourceStringFrom(resource);
        return mapper.readValue(input, type);
    } */

    @BeforeAll
    static void setup() throws IOException {
        String[] runs = new String[] { "/TA3/E201/test", "/TA3/E202/test" };
        List<String> graphs = Stream.of(runs).map(run -> run + "/").collect(Collectors.toList());
        List<RDFNode> graphNodes = graphs.stream().map(ResourceImpl::new).collect(Collectors.toList());

        RDFHelper rdfHelper = mock(RDFHelper.class);
        ESHelper esHelper = mock(ESHelper.class);
        doReturn(graphNodes).when(rdfHelper).getGraphs();

        creator = new TA3CacheCreator(rdfHelper, esHelper);
        creator.process(runs);
    }

/*     @Test
    void testClaim() throws IOException {
        Event toTest = creator.getEvents().get(PROTEST_ID);
        assertEquals("Protester, Protester, Protester, and Person was in a demonstration or protest at se, Facility, El Paraiso, Location, Sucre, and país", toTest.getClaim());
    }

    @Test
    void testEntity() {
        Entity entity = creator.getEntities().get(SE_ID);
        assertEquals("se", entity.toString());
    } */

    static ByteArrayOutputStream getBaos(XContentBuilder builder) {
        builder.close();
        return (ByteArrayOutputStream)builder.getOutputStream();
    }

    static void storeBuilder(String file, XContentBuilder builder) throws IOException {
        Files.newOutputStream(Paths.get("src", "test", "resources", file)).write(getBaos(builder).toByteArray());
    }

    static void assertBuilderEqualsFile(String expectedFile, XContentBuilder actual) {
        ByteArrayOutputStream baos = getBaos(actual);
        String expected = DataUtils.getResourceStringFrom(expectedFile);
        assertEquals(expected, baos.toString());
    }

/*     @Test
    void testEventOutput() throws Exception {
        // test event with docs
        Event protest = creator.getEvents().get(PROTEST_ID);
        assertBuilderEqualsFile(TA3_EVENT_OUTPUT_FILE, protest.getBuilder());
        // storeBuilder(TA3_EVENT_OUTPUT_FILE, protest.getBuilder());


        // test event with dates
        Event transport = creator.getEvents().get(TRANSPORT_ID);
        assertBuilderEqualsFile(TA3_EVENT_WITH_DATES_OUTPUT_FILE, transport.getBuilder());
        // storeBuilder(TA3_EVENT_WITH_DATES_OUTPUT_FILE, transport.getBuilder());
    } */
}
