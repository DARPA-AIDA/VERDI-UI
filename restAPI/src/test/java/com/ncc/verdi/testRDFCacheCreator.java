package  com.ncc.verdi;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.doAnswer;
import static org.mockito.Mockito.mock;

import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.function.Consumer;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

import com.ncc.verdi.RDFCacheCreator.ClusterInfo;
import com.ncc.verdi.data.DataUtils;
import com.ncc.verdi.data.ESHelper;
import com.ncc.verdi.data.RDFHelper;

import org.apache.jena.query.QuerySolution;
import org.apache.jena.query.QuerySolutionMap;
import org.apache.jena.rdf.model.impl.ResourceImpl;
import org.elasticsearch.common.bytes.BytesReference;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.mockito.invocation.InvocationOnMock;
import org.mockito.stubbing.Answer;
import org.springframework.web.client.ResourceAccessException;

public class testRDFCacheCreator {
    private static final String graph = "testGraph";
    private static final String ereType = "Event";
    private static final String clusterName = "testCluster";

    private static final Path clusterFile = Paths.get("src", "main", "resources", "sparql", "cache-clusters.sparql");
    private static final Path argFile = Paths.get("src", "main", "resources", "sparql", "prototype-arguments.sparql");

    private static class TestAnswer implements Answer<Void> {
        public int count = 0;
        private final List<QuerySolution> clusterSolutions;
        private final List<QuerySolution> argSolutions;

        TestAnswer(List<QuerySolution> clusterSolutions, List<QuerySolution> argSolutions) {
            this.clusterSolutions = clusterSolutions;
            this.argSolutions = argSolutions;
        }

        public Void answer(InvocationOnMock invocation) throws Throwable {
            Object[] arguments = invocation.getArguments();
            String query = (String) arguments[0];
            Map<String, String> replacements = DataUtils.getFromReplacements(graph);
            Map<String, String> clusterReplacements = replacements;
            Map<String, String> argReplacements = replacements;

            InputStream clusterInput = Files.newInputStream(clusterFile);
            clusterReplacements.put("ereType", ereType);
            String clusterQuery = DataUtils.replaceMultiple(new String(clusterInput.readAllBytes(), "UTF8"), replacements);

            InputStream argInput = Files.newInputStream(argFile);
            argReplacements.put("cluster", clusterName);
            String argQuery = DataUtils.replaceMultiple(new String(argInput.readAllBytes(), "UTF8"), replacements);
            
            @SuppressWarnings("unchecked") Consumer<QuerySolution> consumer = (Consumer<QuerySolution>) arguments[1];

            if (clusterQuery.equals(query)) {
                clusterSolutions.forEach(consumer);
                count++;
            } else if (argQuery.equals(query)) {
                argSolutions.forEach(consumer);
                count++;
            }

            return null;
        }
    }
    private static final List<QuerySolution> clusterSolutions = new ArrayList<>();
    private static final List<QuerySolution> argSolutions = new ArrayList<>();
    private static final TestAnswer answer = new TestAnswer(clusterSolutions, argSolutions);

    private static final String prototypeName = "testPrototype";
    private static final String testArgName = "name1";
    private static final String testArgType = "type3";

    private static RDFCacheCreator.DataSet dataset;

    @BeforeAll
    static void setup() {
        RDFHelper rdfHelper = mock(RDFHelper.class);
        ESHelper esHelper = mock(ESHelper.class);
        RDFCacheCreator creator = new RDFCacheCreator(rdfHelper, esHelper);

        doAnswer(answer).when(rdfHelper).executeQueryString(anyString(), any());

        // create mock solutions
        QuerySolutionMap clusterSoln = new QuerySolutionMap();
        clusterSoln.add("cluster", new ResourceImpl(clusterName));
        clusterSoln.add("prototype", new ResourceImpl(prototypeName));
        clusterSoln.add("type", new ResourceImpl("testType"));
        clusterSolutions.add(clusterSoln);

        QuerySolutionMap argSoln = new QuerySolutionMap();
        argSoln.add("names", new ResourceImpl(testArgName + ",name2,name3"));
        argSoln.add("types", new ResourceImpl("type1,type2," + testArgType));
        argSolutions.add(argSoln);

        dataset = creator.getDataSet(new ResourceImpl(graph), ereType);
    }

    @Test
    void testGetDataSet() throws ResourceAccessException {
        // verify that mock was accessed twice
        assertEquals(2, answer.count);

        // test returned dataset
        assertEquals(1, dataset.getClusters().size());

        ClusterInfo info = dataset.getClusters().get(new ResourceImpl(clusterName));
        assertEquals(prototypeName, info.getPrototype().toString());
        assertEquals(1, info.getArgumentCount());
        assertTrue(info.getArgumentNames().anyMatch(name -> testArgName.equals(name)));
        assertTrue(info.getArgumentTypes().anyMatch(name -> testArgType.equals(name)));
    }

    @Test
    void testSaveToES() throws IOException {
        ClusterInfo info = dataset.getClusters().get(new ResourceImpl(clusterName));
        String output = new String(BytesReference.toBytes(BytesReference.bytes(info.getBuilder())));
        assertEquals("{\"cluster\":\"testCluster\",\"prototype\":\"testPrototype\",\"types\":[\"testType\"],\"names\":[\"name3\",\"name2\",\"name1\"],\"argumentCount\":1}", output);
    }
}
