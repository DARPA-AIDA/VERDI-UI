package com.ncc.verdi.api;

//TODO: Update this with generic response scheme component
import com.ncc.verdi.model.InlineResponse200;
import com.ncc.verdi.model.CacheObject;

import com.ncc.verdi.caching.TA3CacheCreator;
import com.ncc.verdi.RDFCacheCreator;

import javax.servlet.http.HttpServletRequest;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.ncc.verdi.data.ESHelper;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RestController;

import java.io.IOException;

import org.springframework.beans.factory.annotation.Autowired;

import org.springframework.context.ApplicationContext;

@RestController
public class CacheApiController implements CacheApi {

    private static final Logger log = LoggerFactory.getLogger(CacheApiController.class);

    private final HttpServletRequest request;

    @Autowired
    ApplicationContext appContext;

    @Autowired
    public CacheApiController(ObjectMapper objectMapper, HttpServletRequest request) {
        this.request = request;
    }

    @Override
    public ResponseEntity<InlineResponse200> cacheDelete(String graphURI) {
        InlineResponse200 resMsg = new InlineResponse200();

        for (MediaType mediaType: MediaType.parseMediaTypes(request.getHeader("Accept"))) {
            if (mediaType.isCompatibleWith(MediaType.valueOf("application/json"))) {
                try {
                    if (graphURI == null) {
                        appContext.getBean(RDFCacheCreator.class).deleteAllIndices();
                        resMsg.setMessage("Sucessfully cleared all indices");
                        return new ResponseEntity<InlineResponse200>(resMsg, HttpStatus.OK);

                    } else {
                        appContext.getBean(ESHelper.class).removeGraph(graphURI);
                        resMsg.setMessage("Sucessfully cleared index for " + graphURI);
                        return new ResponseEntity<InlineResponse200>(resMsg, HttpStatus.OK);

                    }
                } catch (IOException e) {
                    log.error("Unable to clear cache", e);
                    resMsg.setMessage("Unable to clear cache");
                    return new ResponseEntity<InlineResponse200>(resMsg, HttpStatus.INTERNAL_SERVER_ERROR);
                }
            }
        }
        resMsg.setMessage("Unable to clear cache");
        return new ResponseEntity<InlineResponse200>(resMsg, HttpStatus.INTERNAL_SERVER_ERROR);
    }


    @Override
    public ResponseEntity<InlineResponse200> cacheCreate(CacheObject cacheObject) {
        InlineResponse200 resMsg = new InlineResponse200();

        for (MediaType mediaType: MediaType.parseMediaTypes(request.getHeader("Accept"))) {
            if (mediaType.isCompatibleWith(MediaType.valueOf("application/json"))) {
                if (cacheObject.getTaskArea().equals("TA2")) {
                    try {
                        RDFCacheCreator cache = appContext.getBean(RDFCacheCreator.class);
                        cache.run(cacheObject.getGraphRootURI().stream().toArray(String[]::new));
                        //TODO: message from cache.run
                        resMsg.setMessage("Sucessfully indexed TA2: " + cacheObject.getGraphRootURI().toString());
                        return new ResponseEntity<InlineResponse200>(resMsg, HttpStatus.OK);
                    } catch (IOException e) {
                        log.error("Failed to index TA2: " + cacheObject.getGraphRootURI().toString());
                        resMsg.setMessage("Failed to index TA2: " + cacheObject.getGraphRootURI().toString());
                        return new ResponseEntity<InlineResponse200>(resMsg, HttpStatus.INTERNAL_SERVER_ERROR);
                    }
                }

                if (cacheObject.getTaskArea().equals("TA3")) {
                    try {
                        appContext.getBean(TA3CacheCreator.class).process(cacheObject.getGraphRootURI().stream().toArray(String[]::new));
                    }
                    catch (IOException e) {
                        log.error("Couldn't serialize response for content type application/json", e);
                        return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
                    }
                    //TODO: message from process
                    resMsg.setMessage("Sucessfully indexed TA3: " + cacheObject.getGraphRootURI().toString());
                    return new ResponseEntity<InlineResponse200>(resMsg, HttpStatus.OK);
                }


            }
        }
        resMsg.setMessage("Failed to index: " + cacheObject.getGraphRootURI().stream().toArray(String[]::new).toString());
        return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
    }

}

