package com.ncc.verdi.api;

import java.io.BufferedInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.nio.file.Files;
import java.util.ArrayList;
import java.util.List;
import java.util.zip.ZipEntry;
import java.util.zip.ZipInputStream;

import javax.servlet.http.HttpServletRequest;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.ncc.verdi.data.RDFHelper;
import com.ncc.verdi.model.InlineResponse200;

import org.apache.commons.compress.archivers.tar.TarArchiveEntry;
import org.apache.commons.compress.archivers.tar.TarArchiveInputStream;
import org.apache.commons.compress.compressors.gzip.GzipCompressorInputStream;
import org.apache.commons.io.FileUtils;
import org.apache.http.NameValuePair;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.FileEntity;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.message.BasicNameValuePair;
import org.apache.jena.rdf.model.RDFNode;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;


@RestController
public class GraphApiController implements GraphApi {

    private static final Logger log = LoggerFactory.getLogger(GraphApiController.class);

    private final HttpServletRequest request;

    @Autowired
    private RDFHelper rdfHelper;

    @Autowired
    public GraphApiController(ObjectMapper objectMapper, HttpServletRequest request) {
        this.request = request;
    }

    @Override
    public ResponseEntity<InlineResponse200> graphDelete(String dropGraph) {
        for (MediaType mediaType : MediaType.parseMediaTypes(request.getHeader("Accept"))) {
            if (mediaType.isCompatibleWith(MediaType.valueOf("application/json"))) {
                return dropGraph(dropGraph);
            }
        }
        return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
    }

    @Override
    public ResponseEntity<InlineResponse200> graphRename(String graphURIOrig, String graphURInew) {
        for (MediaType mediaType : MediaType.parseMediaTypes(request.getHeader("Accept"))) {
            if (mediaType.isCompatibleWith(MediaType.valueOf("application/json"))) {
                return renameGraph(graphURIOrig, graphURInew);
            }
        }
        return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
    }

    @Override
    public ResponseEntity<InlineResponse200> graphUpload(String graphURI, MultipartFile fileName) {
        for (MediaType mediaType: MediaType.parseMediaTypes(request.getHeader("Content-Type"))) {
            if (mediaType.isCompatibleWith(MediaType.valueOf("multipart/form-data"))) {

                int EOF = -1;
                //TODO: Optimize buffer size
                int DEFAULT_BUFFER_SIZE = 1024 * 1024 * 100;
                try {
                    File file = new File(".", fileName.getOriginalFilename());
                    FileInputStream source = (FileInputStream)fileName.getInputStream();
                    FileOutputStream output = FileUtils.openOutputStream(file);
                    //long fileSize = fileName.getSize();
                    try {
                        byte[] buffer = new byte[DEFAULT_BUFFER_SIZE];
                        //long count = 0;
                        int n = 0;
                        while (EOF != (n = source.read(buffer))) {
                            output.write(buffer, 0, n);
                        }
                        output.close(); // don't swallow close Exception if copy completes normally
                        return postGraph(graphURI, file);
                    } catch (IOException e) {
                        log.error("Error in 'blazeGraphUpload' " + graphURI , e);
                    }

                } catch (IOException e) {
                    log.error("Unable to drop graph " + graphURI , e);
                }
            }
        }
        return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
    }

    @Override
    public ResponseEntity<List<InlineResponse200>> graphBatchUpload(String graphURI, MultipartFile compressedFile) {
        for (MediaType mediaType : MediaType.parseMediaTypes(request.getHeader("Content-Type"))) {
            if (mediaType.isCompatibleWith(MediaType.valueOf("multipart/form-data"))) {

                try {
                    String compressedFileName = compressedFile.getOriginalFilename();
                    List<InlineResponse200> resMsgs = new ArrayList<InlineResponse200>();
                    HttpStatus status = HttpStatus.INTERNAL_SERVER_ERROR;

                    FileInputStream compressedSource = (FileInputStream) compressedFile.getInputStream();
                    BufferedInputStream bis = new BufferedInputStream(compressedSource);
                    
                    byte[] buffer = new byte[1024];
                    
                    if (compressedFileName.endsWith(".zip")) {

                        ZipInputStream zipInput = new ZipInputStream(bis);
                        ZipEntry currentEntry;
            
                        while ((currentEntry = zipInput.getNextEntry()) != null) {
                            File newFile = new File(".", currentEntry.getName());
                            if (currentEntry.isDirectory()) {
                                if (!newFile.isDirectory() && !newFile.mkdirs()) {
                                    throw new IOException("Failed to create directory " + newFile);
                                }
                            } else {
                                // fix for Windows-created archives
                                File parent = newFile.getParentFile();
                                if (!parent.isDirectory() && !parent.mkdirs()) {
                                    throw new IOException("Failed to create directory " + parent);
                                }
                                
                                // write file content
                                FileOutputStream fos = new FileOutputStream(newFile);
                                int len;
                                while ((len = zipInput.read(buffer)) > 0) {
                                    fos.write(buffer, 0, len);
                                }
                                fos.close();
                            }

                            ResponseEntity<InlineResponse200> postMsg = postGraph(graphURI + "/" + newFile.getName().replace(" ", "_").replace("(", "").replace(")", "").replace(".ttl", ""), newFile);
                            
                            resMsgs.add(postMsg.getBody());
                            status = postMsg.getStatusCode();
                            //if status of uploaded file is not successful, break while loop
                            if (!status.is2xxSuccessful()) {
                                break;
                            }                            

                        }
                    } else if (compressedFileName.endsWith(".tar.gz") || compressedFileName.endsWith(".tgz")) {
                        
                        TarArchiveInputStream tarInput = new TarArchiveInputStream(
                            new GzipCompressorInputStream(compressedSource));
                        TarArchiveEntry currentEntry;

                        while ((currentEntry = tarInput.getNextTarEntry()) != null) {
                            File file = new File(".", currentEntry.getName());

                            // write file content
                            FileOutputStream fos = new FileOutputStream(file);
                            int len;
                            while ((len = tarInput.read(buffer)) > 0) {
                                fos.write(buffer, 0, len);
                            }
                            fos.close();            

                            ResponseEntity<InlineResponse200> postMsg = postGraph(graphURI + file.getName().replace(".ttl", ""), file);

                            resMsgs.add(postMsg.getBody());
                            status = postMsg.getStatusCode();
                            //if status of uploaded file is not successful, break while loop
                            if (!status.is2xxSuccessful()) {
                                break;
                            }
                        }
                    }
                    return new ResponseEntity<List<InlineResponse200>>(resMsgs, status);

                } catch (IOException e) {
                    log.error("Unable to upload entries for graph URI " + graphURI, e);
                }
            }
        }
        return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
    }

    @Override
    public ResponseEntity<List<InlineResponse200>> graphBatchDelete(String baseURI) {
        for (MediaType mediaType : MediaType.parseMediaTypes(request.getHeader("Accept"))) {
            if (mediaType.isCompatibleWith(MediaType.valueOf("application/json"))) {
                try {
                    List<InlineResponse200> resMsgs = new ArrayList<InlineResponse200>();
                    HttpStatus status = HttpStatus.INTERNAL_SERVER_ERROR;
                    List<String> dropGraphs = new ArrayList<>();
                    dropGraphs = List.of(getGraphsByBaseURI(rdfHelper, baseURI));

                    for (String graph : dropGraphs) {
                        ResponseEntity<InlineResponse200> dropIndexMsg = dropGraph(graph);
                        resMsgs.add(dropIndexMsg.getBody());
                        status = dropIndexMsg.getStatusCode();
                    }
                    return new ResponseEntity<List<InlineResponse200>>(resMsgs, status);
                }
                catch (Exception e) {
                    log.error("Unable to delete graphs for base URI " + baseURI, e);
                }
            }
        }
        return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
    }

    private ResponseEntity<InlineResponse200> postGraph(String graphURI, File fileName) {
        dropGraph(graphURI);
        HttpPost httppost = new HttpPost(rdfHelper.sparqlEndPoint + "?context-uri=" + graphURI);
        CloseableHttpClient httpclient = HttpClients.createDefault();
        httppost.addHeader("content-type", "text/turtle");
        FileEntity entity = new FileEntity(fileName);
        httppost.setEntity(entity);
        InlineResponse200 resMsg = new InlineResponse200();

        try {
            httpclient.execute(httppost);
            httpclient.close();
            Files.delete(fileName.toPath());
        } catch (IOException e) {
            log.error("Unable to import graph " + graphURI, e);
            resMsg.setMessage("Unable to import graph " + graphURI);
            return new ResponseEntity<InlineResponse200>(resMsg, HttpStatus.INTERNAL_SERVER_ERROR);
        }

        resMsg.setMessage("Sucessfully imported graph " + graphURI);
        return new ResponseEntity<InlineResponse200>(resMsg, HttpStatus.OK);
    }

    private ResponseEntity<InlineResponse200> dropGraph(String graphURI) {
        HttpPost httppost = new HttpPost(rdfHelper.sparqlEndPoint);
        CloseableHttpClient httpclient = HttpClients.createDefault();
        InlineResponse200 resMsg = new InlineResponse200();

        List<NameValuePair> params = new ArrayList<NameValuePair>();
        params.add(new BasicNameValuePair("update", "DROP GRAPH <" + graphURI + ">"));

        try {
            httppost.setEntity(new UrlEncodedFormEntity(params, "UTF-8"));
        } catch (UnsupportedEncodingException e) {
            log.error("Unable to drop graph " + graphURI, e);
            resMsg.setMessage("Unable to encode form enity during deletion of graph " + graphURI);
            return new ResponseEntity<InlineResponse200>(resMsg, HttpStatus.INTERNAL_SERVER_ERROR);
        }

        try {
            httpclient.execute(httppost);
            httpclient.close();
        } catch (IOException e) {
            log.error("Unable to drop graph " + graphURI, e);
            resMsg.setMessage("Unable to delete graph " + graphURI);
            return new ResponseEntity<InlineResponse200>(resMsg, HttpStatus.INTERNAL_SERVER_ERROR);
        }
        resMsg.setMessage("Sucessfully deleted graph " + graphURI);
        return new ResponseEntity<InlineResponse200>(resMsg, HttpStatus.OK);
    }

    private ResponseEntity<InlineResponse200> renameGraph(String graphURIOld, String graphURINew) {
        String renameStr = "MOVE <" + graphURIOld + "> TO <" + graphURINew + ">;";
        InlineResponse200 resMsg = new InlineResponse200();

        HttpPost httppost = new HttpPost(rdfHelper.sparqlEndPoint);
        CloseableHttpClient httpclient = HttpClients.createDefault();

        List<NameValuePair> params = new ArrayList<NameValuePair>();
        params.add(new BasicNameValuePair("update", renameStr));

        try {
            httppost.setEntity(new UrlEncodedFormEntity(params, "UTF-8"));
        } catch (UnsupportedEncodingException e) {
            log.error("Unable to rename graph " + graphURIOld, e);
            resMsg.setMessage("Unable to encode form enity during rename of graph " + graphURIOld);
            return new ResponseEntity<InlineResponse200>(resMsg, HttpStatus.INTERNAL_SERVER_ERROR);
        }

        try {
            httpclient.execute(httppost);
            httpclient.close();
        } catch (IOException e) {
            log.error("Unable to rename graph " + graphURIOld, e);
            resMsg.setMessage("Unable to rename graph " + graphURIOld);
            return new ResponseEntity<InlineResponse200>(resMsg, HttpStatus.INTERNAL_SERVER_ERROR);
        }

        resMsg.setMessage("Sucessfully renamed graph " + graphURIOld);
        return new ResponseEntity<InlineResponse200>(resMsg, HttpStatus.OK);    
    }

    public static String[] getGraphsByBaseURI(RDFHelper rdfHelper, String baseURI) {
        return rdfHelper.getGraphs().stream().map(RDFNode::toString).
                filter(g -> g.matches("^" + baseURI + "(\\/.*)*")).toArray(String[]::new);
    }
}

