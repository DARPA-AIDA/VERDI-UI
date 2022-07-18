package com.ncc.verdi.api;

import java.util.List;
import java.util.stream.Collectors;

import javax.servlet.http.HttpServletRequest;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.ncc.verdi.data.RDFHelper;

import org.apache.jena.rdf.model.RDFNode;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class GraphsApiController implements GraphsApi {

    private final HttpServletRequest request;

    @Autowired private RDFHelper rdfHelper;

    @Autowired
    public GraphsApiController(ObjectMapper objectMapper, HttpServletRequest request) {
        this.request = request;
    }

    @Override
    public ResponseEntity<List<String>> graph() {
        for (MediaType mediaType: MediaType.parseMediaTypes(request.getHeader("Accept"))) {
            if (mediaType.isCompatibleWith(MediaType.valueOf("application/json"))) {
                List<RDFNode> graphs = rdfHelper.getGraphs();
                return new ResponseEntity<>(graphs.stream().map(RDFNode::toString).collect(Collectors.toList()),
                        HttpStatus.OK);
            }
        }
        return new ResponseEntity<>(HttpStatus.NOT_IMPLEMENTED);
    }
}
