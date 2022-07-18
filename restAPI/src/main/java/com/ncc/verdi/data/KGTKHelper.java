package com.ncc.verdi.data;

import java.io.IOException;
import java.io.InputStream;
import java.net.URISyntaxException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Properties;

import javax.validation.constraints.NotNull;

import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;
import org.apache.http.client.utils.URIBuilder;
import org.springframework.beans.factory.annotation.Value;
import com.ncc.verdi.model.NodeDetail;
import com.ncc.verdi.RDFCacheCreator;

@Component
public class KGTKHelper {

     //=-=-=-=-=-=-=-=-=-=-=-=-=-=-=
    // Knowledge Graph ToolKit API Support
    // ----------------------------
    private final RestTemplate restTemplate = new RestTemplate();
    private final URIBuilder builder = new URIBuilder();
    private final Properties props = new Properties();

    public KGTKHelper(
        @Value("https") String scheme, 
        @Value("kgtk.isi.edu") String hostname, 
        @Value("/api") String namespace ) throws IOException{

        try (InputStream input = RDFCacheCreator.class.getClassLoader().getResourceAsStream("application.properties")) {
            props.load(input);
        }

        scheme = props.getProperty("kgtk.scheme", scheme);
        hostname = props.getProperty("kgtk.host", hostname);
        namespace = props.getProperty("kgtk.namespace", namespace);

        builder
            .setScheme(scheme)
            .setHost(hostname)
            .setPath(namespace)
            .addParameter("extra_info", "true")
            .addParameter("language", "en")        //return new ResponseEntity<>(HttpStatus.UNPROCESSABLE_ENTITY);
            .addParameter("type", "exactmatch")
            .addParameter("q", "");
    }

    public NodeDetail qNodeLookup(@NotNull String qnodeId) {
        NodeDetail nodeDetail = new NodeDetail();
        try {
            builder.setParameter("q", qnodeId);
            String url = builder.build().toString();
            List<NodeDetail> nodeList = new ArrayList<>();
            nodeList = Arrays.asList(restTemplate.getForObject(url, NodeDetail[].class));

            //Look for an exact match of the qNodeId since labels and aliases containing the qNodeId are also returned.
            if(nodeList.size() > 1) {
                for (NodeDetail node : nodeList) {
                    String detailQNode = node.getQnode();
                    if(detailQNode.equals(qnodeId)) {
                        nodeDetail = node;
                    }
                }
            } else {
                nodeDetail = nodeList.get(0);
            }

            //reset q parameter after qnode is found
            builder.setParameter("q", "");
        } catch (URISyntaxException e) {
            e.printStackTrace();
        }
        return nodeDetail;
    }
}
