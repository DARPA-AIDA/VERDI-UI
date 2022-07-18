package com.ncc.verdi.api;

import java.io.IOException;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.ncc.verdi.data.ESHelper;
import com.ncc.verdi.model.CachedEvent;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class RelationsApiController implements RelationsApi {

    private static final Logger log = LoggerFactory.getLogger(RelationsApiController.class);

    private final ObjectMapper objectMapper;

    private final HttpServletRequest request;

    @Autowired private ESHelper esHelper;

    @Autowired
    public RelationsApiController(ObjectMapper objectMapper, HttpServletRequest request) {
        this.objectMapper = objectMapper;
        this.request = request;
    }

    @Override
    public ResponseEntity<List<CachedEvent>> relations(@NotNull @Valid String graph, @Valid List<String> arguments,
            @Valid List<String> types, @Min(0) @Valid Integer dataLimit) {
        for (MediaType mediaType: MediaType.parseMediaTypes(request.getHeader("Accept"))) {
            if (mediaType.isCompatibleWith(MediaType.valueOf("application/json"))) {
                try {
                    List<CachedEvent> ret = esHelper.getClustersByMultipleQueryInputs(graph, "Relation", arguments, types, dataLimit,
                            objectMapper, CachedEvent.class);
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
    public ResponseEntity<List<String>> relationTypes(@NotNull @Valid String graph, @Valid String type,
            @Min(0) @Valid Integer dataLimit) {
        for (MediaType mediaType: MediaType.parseMediaTypes(request.getHeader("Accept"))) {
            if (mediaType.isCompatibleWith(MediaType.valueOf("application/json"))) {
                try {
                    List<String> ret = esHelper.autocomplete(graph, "Relation", type, ESHelper.TYPES_SUFFIX, dataLimit);
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
    public ResponseEntity<List<String>> relationNames(@NotNull @Valid String graph, @Valid String type,
            @Min(0) @Valid Integer dataLimit) {
        for (MediaType mediaType: MediaType.parseMediaTypes(request.getHeader("Accept"))) {
            if (mediaType.isCompatibleWith(MediaType.valueOf("application/json"))) {
                try {
                    List<String> ret = esHelper.autocomplete(graph, "Relation", type, ESHelper.NAMES_SUFFIX,
                            dataLimit);
                    return new ResponseEntity<>(ret, HttpStatus.OK);
                } catch (IOException e) {
                    log.error("Couldn't serialize response for content type application/json", e);
                    return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
                }
            }
        }
        return new ResponseEntity<>(HttpStatus.NOT_IMPLEMENTED);
    }
}
