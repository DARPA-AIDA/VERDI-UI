package com.ncc.verdi.api;

import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;
import javax.validation.constraints.NotNull;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.ncc.verdi.data.RDFHelper;
import com.ncc.verdi.model.Element;
import com.ncc.verdi.model.Event;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class RelationApiController implements RelationApi {

    private static final Logger log = LoggerFactory.getLogger(RelationsApiController.class);

    private final ObjectMapper objectMapper;

    private final HttpServletRequest request;

    @Autowired private RDFHelper rdfHelper;

    @Autowired
    public RelationApiController(ObjectMapper objectMapper, HttpServletRequest request) {
        this.objectMapper = objectMapper;
        this.request = request;
    }

    @Override
    public ResponseEntity<Event> relation(@NotNull @Valid String id, @Valid String graph) {
        for (MediaType mediaType: MediaType.parseMediaTypes(request.getHeader("Accept"))) {
            if (mediaType.isCompatibleWith(MediaType.valueOf("application/json"))) {
                try {
                    return new ResponseEntity<>(EventApiController.getEvent(rdfHelper, id, graph), HttpStatus.OK);
                } catch (Exception e) {
                    log.error("Unable to retrieve relation details for: " + id, e);
                    return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
                }
            }
        }
        return new ResponseEntity<>(HttpStatus.NOT_IMPLEMENTED);
    }

    @Override
    public ResponseEntity<Element> relationElement(@NotNull @Valid String id, @Valid String graph) {
        for (MediaType mediaType: MediaType.parseMediaTypes(request.getHeader("Accept"))) {
            if (mediaType.isCompatibleWith(MediaType.valueOf("application/json"))) {
                try {
                    return new ResponseEntity<>(EventApiController.getElement(rdfHelper, id, graph), HttpStatus.OK);
                } catch (Exception e) {
                    log.error("Unable to retrieve relation details for: " + id, e);
                    return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
                }
            }
        }
        return new ResponseEntity<>(HttpStatus.NOT_IMPLEMENTED);
    }
}
