package com.ncc.verdi.api;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Objects;

import javax.servlet.http.HttpServletRequest;

import org.apache.http.NameValuePair;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.message.BasicNameValuePair;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RestController;

import com.ncc.verdi.data.DataUtils;
import com.ncc.verdi.data.RDFHelper;
import com.ncc.verdi.model.IdList;
import com.ncc.verdi.model.InlineResponse200;
import com.ncc.verdi.model.QueryClaimListModObject;
import com.ncc.verdi.model.QueryClaimListRecord;
import com.ncc.verdi.model.QueryClaimRenamingObj;
import com.ncc.verdi.model.QueryListId;

@RestController
public class QueryClaimsListAPIController implements QueryClaimsListApi {
    private static final Logger log = LoggerFactory.getLogger(QueryClaimsAPIController.class);
    private final HttpServletRequest request;

    @Autowired
    private RDFHelper rdfHelper;

    @Autowired
    public QueryClaimsListAPIController(HttpServletRequest request) {
        this.request = request;
    }

    @Override
    public ResponseEntity<InlineResponse200> claimQueryListCreate(IdList idList) {
        for (MediaType mediaType : MediaType.parseMediaTypes(request.getHeader("Accept"))) {
            if (mediaType.isCompatibleWith(MediaType.valueOf("application/json"))) {
                try {
                    InlineResponse200 ret = new InlineResponse200();

                    String name = idList.getName();
                    String queryListId = new Date().getTime() + "";
                    Map<String, String> replacements = new HashMap<>();
                    String query = new String();

                    if (idList.getIds().size() == 0) {
                        replacements.put("queryListId", queryListId);
                        replacements.put("queryListName", name);

                        query = DataUtils.replaceMultiple(
                                DataUtils.getResourceStringFrom("sparql/query-claims-list-create.sparql"),
                                replacements);

                    } else {
                        replacements.put("queryListId", queryListId);
                        replacements.put("queryListName", name);

                        query = DataUtils.replaceMultiple(
                                DataUtils.getResourceStringFrom("sparql/query-claims-list-create.sparql"),
                                replacements);

                        for (int i = 0; i < idList.getIds().size(); i++) {
                            String queryId = idList.getIds().get(i);
                            Map<String, String> insertReplacements = new HashMap<>();

                            insertReplacements.put("queryListId", queryListId);
                            insertReplacements.put("queryId", queryId);
                            insertReplacements.put("queryListName", name);

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

    @Override
    public ResponseEntity<List<QueryClaimListRecord>> getAllClaimQueryListCreate() {
        for (MediaType mediaType : MediaType.parseMediaTypes(request.getHeader("Accept"))) {
            if (mediaType.isCompatibleWith(MediaType.valueOf("application/json"))) {
                try {
                    ArrayList<QueryClaimListRecord> ret = new ArrayList<>();
                    String query = DataUtils.getResourceStringFrom("sparql/get-claim-query-lists.sparql");

                    rdfHelper.executeQueryString(query, qs -> {
                        QueryClaimListRecord tempQCLR = new QueryClaimListRecord();
                        tempQCLR.setId(qs.get("queryListId").toString());
                        tempQCLR.setName(DataUtils.getBlankIfNull(qs.get("queryListName")));
                        tempQCLR.setAssocaitedClaimQueryId(
                                DataUtils.getBlankIfNull(qs.get("assocaitedClaimQueryId")));

                        ret.add(tempQCLR);
                    });

                    return new ResponseEntity<List<QueryClaimListRecord>>(ret, HttpStatus.OK);
                } catch (Exception e) {
                    log.error("Error: ", e);
                    return new ResponseEntity<List<QueryClaimListRecord>>(HttpStatus.INTERNAL_SERVER_ERROR);
                }
            }
        }
        return new ResponseEntity<>(HttpStatus.NOT_IMPLEMENTED);
    }

    @Override
    public ResponseEntity<IdList> queryClaimListFetch(String queryListId) {
        for (MediaType mediaType : MediaType.parseMediaTypes(request.getHeader("Accept"))) {
            if (mediaType.isCompatibleWith(MediaType.valueOf("application/json"))) {
                try {
                    IdList ret = new IdList();
                    String query = new String();

                    if (queryListId == "") {
                        query = DataUtils.getResourceStringFrom("sparql/get-claim-query-lists.sparql");
                    } else {
                        Map<String, String> replacements = new HashMap<>();
                        replacements.put("queryListId", queryListId);

                        query = DataUtils.replaceMultiple(
                                DataUtils.getResourceStringFrom("sparql/get-claim-query-list.sparql"),
                                replacements);
                    }

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

    @Override
    public ResponseEntity<InlineResponse200> claimQueryListAdd(QueryClaimListModObject queryClaimListModObject) {
        for (MediaType mediaType : MediaType.parseMediaTypes(request.getHeader("Accept"))) {
            if (mediaType.isCompatibleWith(MediaType.valueOf("application/json"))) {
                try {
                    InlineResponse200 ret = new InlineResponse200();

                    String queryListId = queryClaimListModObject.getQueryClaimListId();
                    String query = "";
                    String name = queryClaimListModObject.getName();

                    if (queryClaimListModObject.getQueryClaims().size() == 0) {
                        ret.setMessage("No ids in list");
                        return new ResponseEntity<>(ret, HttpStatus.OK);
                    } else {
                        for (int i = 0; i < queryClaimListModObject.getQueryClaims().size(); i++) {
                            String queryId = queryClaimListModObject.getQueryClaims().get(i);
                            Map<String, String> insertReplacements = new HashMap<>();

                            insertReplacements.put("queryListId", queryListId);
                            insertReplacements.put("queryId", queryId);
                            insertReplacements.put("queryListName", name);

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

    @Override
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

    @Override
    public ResponseEntity<InlineResponse200> claimQueryListRemove(QueryClaimListModObject queryClaimListModObject) {
        for (MediaType mediaType : MediaType.parseMediaTypes(request.getHeader("Accept"))) {
            if (mediaType.isCompatibleWith(MediaType.valueOf("application/json"))) {
                try {
                    InlineResponse200 ret = new InlineResponse200();

                    String queryListId = queryClaimListModObject.getQueryClaimListId();
                    String query = "";
                    String name = queryClaimListModObject.getName();

                    if (queryClaimListModObject.getQueryClaims().size() == 0) {
                        ret.setMessage("No ids in list");
                        return new ResponseEntity<>(ret, HttpStatus.OK);
                    } else {
                        for (int i = 0; i < queryClaimListModObject.getQueryClaims().size(); i++) {
                            String queryId = queryClaimListModObject.getQueryClaims().get(i);
                            Map<String, String> deleteReplacements = new HashMap<>();

                            deleteReplacements.put("queryListId", queryListId);
                            deleteReplacements.put("queryId", queryId);
                            deleteReplacements.put("queryListName", name);

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

    @Override
    public ResponseEntity<InlineResponse200> claimQueryListRename(QueryClaimRenamingObj queryClaimRenamingObj) {
        for (MediaType mediaType : MediaType.parseMediaTypes(request.getHeader("Accept"))) {
            if (mediaType.isCompatibleWith(MediaType.valueOf("application/json"))) {
                try {
                    String queryListId = queryClaimRenamingObj.getQueryId();
                    String newListName = queryClaimRenamingObj.getName();

                    String searchQuery = new String();
                    Map<String, String> searchReplacements = new HashMap<>();
                    searchReplacements.put("queryListId", queryListId);

                    searchQuery = DataUtils.replaceMultiple(
                            DataUtils.getResourceStringFrom("sparql/get-claim-query-list.sparql"),
                            searchReplacements);

                    IdList idList = new IdList();

                    rdfHelper.executeQueryString(searchQuery, qs -> {
                        idList.addIdsItem(qs.get("assocaitedClaimQueryId").toString());

                        if (Objects.isNull(qs.get("queryListName"))){
                            idList.setName("");
                        } else {
                            idList.setName(qs.get("queryListName").toString());
                        }
                    });

                    HashMap<String, String> idMap = new HashMap<>();

                    for(int i = 0; i < idList.getIds().size(); i++){
                        String id = idList.getIds().get(i);

                        idMap.put(id, id);
                    }

                    ArrayList<String> compressedIdList = new ArrayList<>();

                    for (String name : idMap.keySet()) {
                        compressedIdList.add(name);
                    }

                    String masterQuery = "";

                    Map<String, String> deleteReplacements = new HashMap<>();
                    deleteReplacements.put("queryListId", queryListId);

                    String masterDeleteQuery = DataUtils.replaceMultiple(
                            DataUtils.getResourceStringFrom("sparql/query-claims-list-delete.sparql"),
                            deleteReplacements);

                    String masterInsertQuery = "";

                    for(int i = 0; i < compressedIdList.size(); i++){
                        String tempId = compressedIdList.get(i);

                        Map<String, String> insertReplacements = new HashMap<>();
                        insertReplacements.put("queryListId", queryListId);
                        insertReplacements.put("queryId", tempId);
                        insertReplacements.put("queryListName", newListName);

                        String tempInsertStatement = DataUtils.replaceMultiple(
                                DataUtils.getResourceStringFrom("sparql/query-claims-list-add-entry.sparql"),
                                insertReplacements);

                        masterInsertQuery = masterInsertQuery.concat(tempInsertStatement);
                    }

                    masterQuery = masterQuery.concat(masterDeleteQuery);
                    masterQuery = masterQuery.concat(masterInsertQuery);

                    HttpPost httppost = new HttpPost(rdfHelper.sparqlEndPoint);
                    CloseableHttpClient httpclient = HttpClients.createDefault();
                    InlineResponse200 resMsg = new InlineResponse200();

                    List<NameValuePair> params = new ArrayList<NameValuePair>();
                    params.add(new BasicNameValuePair("update", masterQuery));

                    try {
                        httppost.setEntity(new UrlEncodedFormEntity(params, "UTF-8"));
                        httpclient.execute(httppost);
                        httpclient.close();
                    } catch (Exception e) {
                        log.error("Failed to rename list: ", e);
                        resMsg.setMessage("Failed to rename list.");
                        return new ResponseEntity<InlineResponse200>(resMsg, HttpStatus.INTERNAL_SERVER_ERROR);
                    }

                    resMsg.setMessage(queryListId + " query claim list name changed to " + newListName);
                    return new ResponseEntity<>(resMsg, HttpStatus.OK);
                } catch (Exception e) {
                    log.error("Error: ", e);
                    return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
                }
            }
        }
        return new ResponseEntity<>(HttpStatus.NOT_IMPLEMENTED);
    }

}