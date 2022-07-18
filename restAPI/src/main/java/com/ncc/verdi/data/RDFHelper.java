package com.ncc.verdi.data;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.function.Consumer;

import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.jena.query.QueryFactory;
import org.apache.jena.query.QuerySolution;
import org.apache.jena.rdf.model.RDFNode;
import org.apache.jena.rdfconnection.RDFConnection;
import org.apache.jena.rdfconnection.RDFConnectionRemote;
import org.apache.jena.rdfconnection.RDFConnectionRemoteBuilder;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

@Component
public class RDFHelper {
    private static final Map<String, RDFConnectionRemoteBuilder> rdfConnBuilders = new HashMap<>();
    private static final CloseableHttpClient client = HttpClients.createDefault();

    private final RDFConnectionRemoteBuilder builder;

    //TODO: Temp solution until we create a more generic request handler
    public String sparqlEndPoint;

    public RDFHelper(
        @Value("${triplestore.scheme:http}") String scheme,
        @Value("${triplestore.host:localhost}") String host,
        @Value("${triplestore.port:9999}") int port,
        @Value("${triplestore.query.namespace:sparql}") String endpoint) {
            String destination = scheme + "://" + host + ":" + String.valueOf(port);
            sparqlEndPoint = destination + "/" + endpoint;
            builder = rdfConnBuilders.computeIfAbsent(destination, key ->
                    RDFConnectionRemote.create()
                            .httpClient(client)
                            .destination(destination)
                            .queryEndpoint(endpoint)
            );
    }

    public List<RDFNode> getGraphs() {
        List<RDFNode> graphs = new ArrayList<>();
        executeQueryString("SELECT ?g WHERE {GRAPH ?g { }} order by ?g", qs -> graphs.add(qs.get("g")));
        return graphs;
    }

    public void executeQueryString(String query, Consumer<QuerySolution> solutionHandler) {
        try (RDFConnection conn = builder.build()) {
            conn.queryResultSet(QueryFactory.create(query), rs -> rs.forEachRemaining(solutionHandler));
        }
    }

    public void executeUpdateString(String query, Consumer<QuerySolution> solutionHandler) {
        try (RDFConnection conn = builder.build()) {
            conn.update(query);
            conn.commit();
        }
    }

    // Convenience method as this code exists in more than one place
    public List<QuerySolution> getExecutionResults(String query) {
        List<QuerySolution> ret = new ArrayList<>();
        executeQueryString(query, ret::add);
        return ret;
    }

}
