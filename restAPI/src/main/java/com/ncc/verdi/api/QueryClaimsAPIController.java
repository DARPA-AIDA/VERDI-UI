package com.ncc.verdi.api;

import com.ncc.verdi.model.QueryClaim;
import com.ncc.verdi.model.QueryClaimList;
import com.ncc.verdi.model.QueryClaimListModObject;
import com.ncc.verdi.model.IdList;
import com.ncc.verdi.model.InlineResponse200;
import com.ncc.verdi.model.QueryClaimObject;
import com.ncc.verdi.model.QueryId;
import com.ncc.verdi.model.QueryListId;

import java.util.Map;
import java.util.HashMap;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.List;
import java.util.Base64;
import java.util.Date;
import java.security.MessageDigest;

import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;

import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.message.BasicNameValuePair;
import org.apache.jena.query.QuerySolution;
import org.apache.http.NameValuePair;
import org.apache.jena.rdf.model.RDFNode;
import org.apache.jena.update.UpdateRequest;

import com.ncc.verdi.data.DataUtils;
import com.ncc.verdi.data.KGTKHelper;
import com.ncc.verdi.data.RDFHelper;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class QueryClaimsAPIController implements QueryClaimsApi {
    private static final Logger log = LoggerFactory.getLogger(QueryClaimsAPIController.class);
    private final HttpServletRequest request;

    @Autowired
    private RDFHelper rdfHelper;

    @Autowired
    public QueryClaimsAPIController(HttpServletRequest request) {
        this.request = request;
    }

    @Override
    public ResponseEntity<QueryClaimList> queryClaim(String queryId) {
        for (MediaType mediaType : MediaType.parseMediaTypes(request.getHeader("Accept"))) {
            if (mediaType.isCompatibleWith(MediaType.valueOf("application/json"))) {
                try {
                    Map<String, String> replacements = new HashMap<>();
                    replacements.put("queryId", queryId);

                    String query = DataUtils.replaceMultiple(
                            DataUtils.getResourceStringFrom("sparql/query-claims-get.sparql"), replacements);

                    QueryClaimList ret = new QueryClaimList();
                    rdfHelper.executeQueryString(query, qs -> {
                        QueryClaim tempQuery = new QueryClaim()
                                .queryId(qs.get("queryId").toString())
                                .topic(qs.get("topic").toString())
                                .subtopic(qs.get("subtopic").toString())
                                .claimTemplate(qs.get("claimTemplate").toString());
                        ret.addQueriesItem(tempQuery);
                    });

                    return new ResponseEntity<>(ret, HttpStatus.OK);
                } catch (Exception e) {
                    log.error("Error: ", e);
                    return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
                }
            }
        }
        return new ResponseEntity<>(HttpStatus.NOT_IMPLEMENTED);
    }

    @Override
    public ResponseEntity<InlineResponse200> claimQueryDelete(QueryId queryId) {
        for (MediaType mediaType : MediaType.parseMediaTypes(request.getHeader("Accept"))) {
            if (mediaType.isCompatibleWith(MediaType.valueOf("application/json"))) {
                try {
                    InlineResponse200 ret = new InlineResponse200();

                    // Insert new entry
                    String tqId = queryId.getId();

                    Map<String, String> replacements = new HashMap<>();
                    replacements.put("queryId", tqId);

                    String query = DataUtils.replaceMultiple(
                            DataUtils.getResourceStringFrom("sparql/query-claims-delete.sparql"), replacements);

                    HttpPost httppost = new HttpPost(rdfHelper.sparqlEndPoint);
                    CloseableHttpClient httpclient = HttpClients.createDefault();
                    InlineResponse200 resMsg = new InlineResponse200();

                    List<NameValuePair> params = new ArrayList<NameValuePair>();
                    params.add(new BasicNameValuePair("update", query));

                    try {
                        httppost.setEntity(new UrlEncodedFormEntity(params, "UTF-8"));
                        httpclient.execute(httppost);
                        httpclient.close();
                        ret.setMessage("Query " + queryId.getId() + " deleted");
                    } catch (Exception e) {
                        log.error("Failed to delete query claim: " + queryId.getId(), e);
                        resMsg.setMessage("Failed to create query claim: " + queryId.getId());
                        return new ResponseEntity<InlineResponse200>(resMsg, HttpStatus.INTERNAL_SERVER_ERROR);
                    }

                    return new ResponseEntity<>(ret, HttpStatus.OK);
                } catch (Exception e) {
                    log.error("Error: ", e);
                    return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
                }
            }
        }
        return new ResponseEntity<>(HttpStatus.NOT_IMPLEMENTED);
    }

    @Override
    public ResponseEntity<InlineResponse200> claimQueryCreate(QueryClaimObject queryClaimObject) {
        for (MediaType mediaType : MediaType.parseMediaTypes(request.getHeader("Accept"))) {
            if (mediaType.isCompatibleWith(MediaType.valueOf("application/json"))) {
                try {
                    InlineResponse200 ret = new InlineResponse200();

                    String queryId = queryClaimObject.getSubtopic() + new Date().getTime();
                    queryId = Base64.getUrlEncoder().withoutPadding()
                            .encodeToString(queryId.getBytes(StandardCharsets.UTF_8));
                    queryId = queryId.concat(":" + new Date().getTime());

                    // Insert new entry
                    Map<String, String> replacements = new HashMap<>();
                    replacements.put("queryId", queryId);
                    replacements.put("topic", queryClaimObject.getTopic());
                    replacements.put("subtopic", queryClaimObject.getSubtopic());
                    replacements.put("claimTemplate", queryClaimObject.getClaimTemplate());

                    String query = DataUtils.replaceMultiple(
                            DataUtils.getResourceStringFrom("sparql/query-claims-create.sparql"), replacements);

                    HttpPost httppost = new HttpPost(rdfHelper.sparqlEndPoint);
                    CloseableHttpClient httpclient = HttpClients.createDefault();
                    InlineResponse200 resMsg = new InlineResponse200();

                    List<NameValuePair> params = new ArrayList<NameValuePair>();
                    params.add(new BasicNameValuePair("update", query));

                    try {
                        httppost.setEntity(new UrlEncodedFormEntity(params, "UTF-8"));
                        httpclient.execute(httppost);
                        httpclient.close();
                    } catch (Exception e) {
                        log.error("Failed to create query claim: ", e);
                        resMsg.setMessage("Failed to create query claim.");
                        return new ResponseEntity<InlineResponse200>(resMsg, HttpStatus.INTERNAL_SERVER_ERROR);
                    }

                    ret.setMessage(queryId);
                    return new ResponseEntity<>(ret, HttpStatus.OK);
                } catch (Exception e) {
                    log.error("Error: ", e);
                    return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
                }
            }
        }
        return new ResponseEntity<>(HttpStatus.NOT_IMPLEMENTED);
    }

    @Override
    public ResponseEntity<InlineResponse200> claimQueryUpdate(QueryClaim queryClaim) {
        for (MediaType mediaType : MediaType.parseMediaTypes(request.getHeader("Accept"))) {
            if (mediaType.isCompatibleWith(MediaType.valueOf("application/json"))) {
                try {
                    InlineResponse200 ret = new InlineResponse200();

                    String queryId = queryClaim.getQueryId();

                    // Insert new entry
                    Map<String, String> replacements = new HashMap<>();
                    replacements.put("queryId", queryId);
                    replacements.put("topic", queryClaim.getTopic());
                    replacements.put("subtopic", queryClaim.getSubtopic());
                    replacements.put("claimTemplate", queryClaim.getClaimTemplate());

                    String query = DataUtils.replaceMultiple(
                            DataUtils.getResourceStringFrom("sparql/query-claims-update.sparql"), replacements);

                    HttpPost httppost = new HttpPost(rdfHelper.sparqlEndPoint);
                    CloseableHttpClient httpclient = HttpClients.createDefault();
                    InlineResponse200 resMsg = new InlineResponse200();

                    List<NameValuePair> params = new ArrayList<NameValuePair>();
                    params.add(new BasicNameValuePair("update", query));

                    try {
                        httppost.setEntity(new UrlEncodedFormEntity(params, "UTF-8"));
                        httpclient.execute(httppost);
                        httpclient.close();
                    } catch (Exception e) {
                        log.error("Failed to update query claim: ", e);
                        resMsg.setMessage("Failed to update query claim.");
                        return new ResponseEntity<InlineResponse200>(resMsg, HttpStatus.INTERNAL_SERVER_ERROR);
                    }

                    ret.setMessage(queryId);
                    return new ResponseEntity<>(ret, HttpStatus.OK);
                } catch (Exception e) {
                    log.error("Error: ", e);
                    return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
                }
            }
        }
        return new ResponseEntity<>(HttpStatus.NOT_IMPLEMENTED);
    }

    public ResponseEntity<InlineResponse200> claimQueryListCreate(IdList idList) {
        for (MediaType mediaType : MediaType.parseMediaTypes(request.getHeader("Accept"))) {
            if (mediaType.isCompatibleWith(MediaType.valueOf("application/json"))) {
                try {
                    InlineResponse200 ret = new InlineResponse200();

                    String queryListId = new Date().getTime() + "";
                    Map<String, String> replacements = new HashMap<>();
                    String query = new String();

                    if(idList.getIds().size() == 0){
                        replacements.put("queryListId", queryListId);

                        query = DataUtils.replaceMultiple(
                                DataUtils.getResourceStringFrom("sparql/query-claims-list-create.sparql"), replacements);
                    } else {
                        replacements.put("queryListId", queryListId);

                        query = DataUtils.replaceMultiple(
                                DataUtils.getResourceStringFrom("sparql/query-claims-list-create.sparql"),
                                replacements);

                        for(int i = 0; i < idList.getIds().size(); i++){
                            String queryId = idList.getIds().get(i);
                            Map<String, String> insertReplacements = new HashMap<>();

                            insertReplacements.put("queryListId", queryListId);
                            insertReplacements.put("queryId", queryId);

                            String insertClause = DataUtils.replaceMultiple(
                                    DataUtils.getResourceStringFrom("sparql/query-claims-list-add-entry.sparql"),
                                    insertReplacements);

                            query = query.concat(insertClause);
                        }
                    }

                    HttpPost httppost = new HttpPost(rdfHelper.sparqlEndPoint);
                    CloseableHttpClient httpclient = HttpClients.createDefault();
                    InlineResponse200 resMsg = new InlineResponse200();

                    List<NameValuePair> params = new ArrayList<NameValuePair>();
                    params.add(new BasicNameValuePair("update", query));

                    try {
                        httppost.setEntity(new UrlEncodedFormEntity(params, "UTF-8"));
                        httpclient.execute(httppost);
                        httpclient.close();
                    } catch (Exception e) {
                        log.error("Failed to create query claim: ", e);
                        resMsg.setMessage("Failed to create query claim.");
                        return new ResponseEntity<InlineResponse200>(resMsg, HttpStatus.INTERNAL_SERVER_ERROR);
                    }

                    ret.setMessage(queryListId);
                    return new ResponseEntity<>(ret, HttpStatus.OK);
                } catch (Exception e) {
                    log.error("Error: ", e);
                    return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
                }
            }
        }
        return new ResponseEntity<>(HttpStatus.NOT_IMPLEMENTED);
    }

    public ResponseEntity<IdList> queryClaimListFetch(String queryListId) {
        for (MediaType mediaType : MediaType.parseMediaTypes(request.getHeader("Accept"))) {
            if (mediaType.isCompatibleWith(MediaType.valueOf("application/json"))) {
                try {
                    IdList ret = new IdList();
                    String query = new String();

                    if(queryListId == "") {
                        query = DataUtils.getResourceStringFrom("sparql/get-claim-query-lists.sparql");
                    } else {
                        Map<String, String> replacements = new HashMap<>();
                        replacements.put("queryListId", queryListId);

                        query = DataUtils.replaceMultiple(
                                DataUtils.getResourceStringFrom("sparql/get-claim-query-list.sparql"),
                                replacements);
                    }

                    InlineResponse200 resMsg = new InlineResponse200();

                    List<NameValuePair> params = new ArrayList<NameValuePair>();
                    params.add(new BasicNameValuePair("update", query));

                    rdfHelper.executeQueryString(query, qs -> {
                        ret.addIdsItem(qs.get("assocaitedClaimQueryId").toString());
                    });

                    return new ResponseEntity<>(ret, HttpStatus.OK);
                } catch (Exception e) {
                    log.error("Error: ", e);
                    return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
                }
            }
        }
        return new ResponseEntity<>(HttpStatus.NOT_IMPLEMENTED);
    }

    public ResponseEntity<InlineResponse200> claimQueryListAdd(QueryClaimListModObject queryClaimListModObject) {
        for (MediaType mediaType : MediaType.parseMediaTypes(request.getHeader("Accept"))) {
            if (mediaType.isCompatibleWith(MediaType.valueOf("application/json"))) {
                try {
                    InlineResponse200 ret = new InlineResponse200();

                    String queryListId = queryClaimListModObject.getQueryClaimListId();
                    String query = "";

                    if (queryClaimListModObject.getQueryClaims().size() == 0) {
                        ret.setMessage("No ids in list");
                        return new ResponseEntity<>(ret, HttpStatus.OK);
                    } else {
                        for (int i = 0; i < queryClaimListModObject.getQueryClaims().size(); i++) {
                            String queryId = queryClaimListModObject.getQueryClaims().get(i);
                            Map<String, String> insertReplacements = new HashMap<>();

                            insertReplacements.put("queryListId", queryListId);
                            insertReplacements.put("queryId", queryId);

                            String insertClause = DataUtils.replaceMultiple(
                                    DataUtils.getResourceStringFrom("sparql/query-claims-list-add-entry.sparql"),
                                    insertReplacements);

                            query = query.concat(insertClause);
                        }
                    }

                    HttpPost httppost = new HttpPost(rdfHelper.sparqlEndPoint);
                    CloseableHttpClient httpclient = HttpClients.createDefault();
                    InlineResponse200 resMsg = new InlineResponse200();

                    List<NameValuePair> params = new ArrayList<NameValuePair>();
                    params.add(new BasicNameValuePair("update", query));

                    try {
                        httppost.setEntity(new UrlEncodedFormEntity(params, "UTF-8"));
                        httpclient.execute(httppost);
                        httpclient.close();
                    } catch (Exception e) {
                        log.error("Failed to create query claim: ", e);
                        resMsg.setMessage("Failed to create query claim.");
                        return new ResponseEntity<InlineResponse200>(resMsg, HttpStatus.INTERNAL_SERVER_ERROR);
                    }

                    ret.setMessage(queryListId);
                    return new ResponseEntity<>(ret, HttpStatus.OK);
                } catch (Exception e) {
                    log.error("Error: ", e);
                    return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
                }
            }
        }
        return new ResponseEntity<>(HttpStatus.NOT_IMPLEMENTED);
    }

    public ResponseEntity<InlineResponse200> claimQueryListDelete(QueryListId queryListId) {
        for (MediaType mediaType : MediaType.parseMediaTypes(request.getHeader("Accept"))) {
            if (mediaType.isCompatibleWith(MediaType.valueOf("application/json"))) {
                try {
                    InlineResponse200 ret = new InlineResponse200();
                    Map<String, String> replacements = new HashMap<>();

                    replacements.put("queryListId", queryListId.getId().toString());

                    String query = DataUtils.replaceMultiple(
                            DataUtils.getResourceStringFrom("sparql/query-claims-list-delete.sparql"),
                            replacements);

                    HttpPost httppost = new HttpPost(rdfHelper.sparqlEndPoint);
                    CloseableHttpClient httpclient = HttpClients.createDefault();
                    InlineResponse200 resMsg = new InlineResponse200();

                    List<NameValuePair> params = new ArrayList<NameValuePair>();
                    params.add(new BasicNameValuePair("update", query));

                    try {
                        httppost.setEntity(new UrlEncodedFormEntity(params, "UTF-8"));
                        httpclient.execute(httppost);
                        httpclient.close();
                    } catch (Exception e) {
                        log.error("Failed to delete query claim list: ", e);
                        resMsg.setMessage("Failed to delete query claim list.");
                        return new ResponseEntity<InlineResponse200>(resMsg, HttpStatus.INTERNAL_SERVER_ERROR);
                    }

                    ret.setMessage("Query claim list");
                    return new ResponseEntity<>(ret, HttpStatus.OK);
                } catch (Exception e) {
                    log.error("Error: ", e);
                    return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
                }
            }
        }
        return new ResponseEntity<>(HttpStatus.NOT_IMPLEMENTED);
    }

    public ResponseEntity<InlineResponse200> claimQueryListRemove(QueryClaimListModObject queryClaimListModObject) {
        for (MediaType mediaType : MediaType.parseMediaTypes(request.getHeader("Accept"))) {
            if (mediaType.isCompatibleWith(MediaType.valueOf("application/json"))) {
                try {
                    InlineResponse200 ret = new InlineResponse200();

                    String queryListId = queryClaimListModObject.getQueryClaimListId();
                    String query = "";

                    if (queryClaimListModObject.getQueryClaims().size() == 0) {
                        ret.setMessage("No ids in list");
                        return new ResponseEntity<>(ret, HttpStatus.OK);
                    } else {
                        for (int i = 0; i < queryClaimListModObject.getQueryClaims().size(); i++) {
                            String queryId = queryClaimListModObject.getQueryClaims().get(i);
                            Map<String, String> deleteReplacements = new HashMap<>();

                            deleteReplacements.put("queryListId", queryListId);
                            deleteReplacements.put("queryId", queryId);

                            String deleteClause = DataUtils.replaceMultiple(
                                    DataUtils.getResourceStringFrom("sparql/query-claims-list-remove-entry.sparql"),
                                    deleteReplacements);

                            query = query.concat(deleteClause);
                        }
                    }

                    HttpPost httppost = new HttpPost(rdfHelper.sparqlEndPoint);
                    CloseableHttpClient httpclient = HttpClients.createDefault();
                    InlineResponse200 resMsg = new InlineResponse200();

                    List<NameValuePair> params = new ArrayList<NameValuePair>();
                    params.add(new BasicNameValuePair("update", query));

                    try {
                        httppost.setEntity(new UrlEncodedFormEntity(params, "UTF-8"));
                        httpclient.execute(httppost);
                        httpclient.close();
                    } catch (Exception e) {
                        log.error("Failed to delete entries: ", e);
                        resMsg.setMessage("Failed to delete entries.");
                        return new ResponseEntity<InlineResponse200>(resMsg, HttpStatus.INTERNAL_SERVER_ERROR);
                    }

                    ret.setMessage(queryListId);
                    return new ResponseEntity<>(ret, HttpStatus.OK);
                } catch (Exception e) {
                    log.error("Error: ", e);
                    return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
                }
            }
        }
        return new ResponseEntity<>(HttpStatus.NOT_IMPLEMENTED);
    }
}
