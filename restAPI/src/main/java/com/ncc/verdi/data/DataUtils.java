package com.ncc.verdi.data;

import java.io.FileInputStream;

// import static org.mockito.ArgumentMatchers.notNull;

import java.io.IOException;
import java.io.InputStream;
import java.nio.charset.StandardCharsets;
import java.util.AbstractMap;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.Properties;
import java.util.function.Function;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import org.apache.commons.io.IOUtils;
import org.apache.jena.query.QuerySolution;
import org.apache.jena.rdf.model.RDFNode;
import org.json.JSONArray;
import org.json.JSONObject;
import org.springframework.web.client.ResourceAccessException;

import com.ncc.verdi.model.DocObject;
import com.ncc.verdi.model.Element;
import com.ncc.verdi.model.Entity;
import com.ncc.verdi.model.EntityElement;
import com.ncc.verdi.model.EntityMember;
import com.ncc.verdi.model.JustifiedNode;
import com.ncc.verdi.model.JustifiedNode.ObjectTypeEnum;
import com.ncc.verdi.model.LDCTime;
import com.ncc.verdi.model.Member;

public class DataUtils {
    private static final String FROM = "from";
    private static final String BEFORE = "BEFORE";
    private static final String AFTER = "AFTER";
    private static String DATA_SOURCE = "";

    public static <T> T getFromJSON(Map<String, Object> src, String... fieldPath) {
        String field = fieldPath[0];
        if (src == null) {
            return null;
        }
        Object o = src.get(field);
        if (fieldPath.length > 1) {
            @SuppressWarnings("unchecked")
            Map<String, Object> map = (Map<String, Object>)o;
            return getFromJSON(map, Arrays.copyOfRange(fieldPath, 1, fieldPath.length));
        } else {
            @SuppressWarnings("unchecked") T ret = (T) o;
            return ret;
        }
    }

    public static String getResourceStringFrom(String filename) throws ResourceAccessException {
        try (InputStream input = DataUtils.class.getClassLoader().getResourceAsStream(filename)) {
            return new String(input.readAllBytes(), "UTF8");
        } catch (IOException e) {
            throw new ResourceAccessException("Unable to read resource " + filename, e);
        }
    }

    public static String replace(String template, Map.Entry<String, String> entry) {
        return replace(template, entry.getKey(), entry.getValue());
    }

    public static String getDataSource() {
        if(DATA_SOURCE != "") {
            return DATA_SOURCE;
        } else {
            return setDataSource();
        }
    }

    public static String setDataSource() {
        Properties prop = new Properties();
        //TODO: use spring properties 
        try (InputStream input = new FileInputStream("./src/main/resources/application.properties")) {
            prop.load(input);
            DATA_SOURCE = prop.getProperty("BASE_URI");
        } catch (IOException ex) {
            ex.printStackTrace();
        }
        return DATA_SOURCE;
    }

    public static String replace(String template, String name, String replacement) {
        return template.replaceAll("\\{\\$" + name + "}", replacement);
    }

    public static Map.Entry<String, String> getFromEntry(String... graphs) {
        List<String> mappedGraphs = Stream.of(graphs)
            .filter(graph -> graph != null)
            .map(graph -> " from <" + graph + ">")
            .collect(Collectors.toList());
        return new AbstractMap.SimpleEntry<>(FROM, String.join(" ", mappedGraphs));
    }

    public static Map<String, String> getFromReplacements(String... graphs) {
        return new DataUtils().instanceGetFromReplacements(graphs);
    }

    public Map<String, String> instanceGetFromReplacements(String... graphs) {
        Map<String, String> replacements = new HashMap<>();
        Map.Entry<String, String> fromEntry = getFromEntry(graphs);
        replacements.put(fromEntry.getKey(), fromEntry.getValue());
        return replacements;
    }

    public static String replaceFrom(String template, String... graphs) {
        return replace(template, getFromEntry(graphs));
    }

    public static String replaceMultiple(String template, Map<String, String> replacements) {
        for (Map.Entry<String, String> entry : replacements.entrySet()) {
            template = replace(template, entry);
        }
        return template;
    }

    public static <T extends JustifiedNode> T setJustifiedNode(T node, QuerySolution qs) {
        return setJustifiedNode(node, qs, qs.get("node").toString());
    }

    public static <T extends JustifiedNode> T setJustifiedNode(T node, QuerySolution qs, String id) {
        RDFNode type = qs.get("type");
        node.id(id).addTypesItem(type != null ? type.toString() : "");

        if(qs.get("docs") != null) {
            node.id(id).docs(getDocList(qs, "docs"));
        }
        return node;
    }

    public static String getBlankIfNull(String str) {
        if (Objects.isNull(str)) {
            return "";
        } else {
            return str.toString();
        }
    }

    public static String getBlankIfNull(RDFNode rdfNode) {
        if (Objects.isNull(rdfNode)) {
            return "";
        } else {
            return rdfNode.toString();
        }
    }


    public static JustifiedNode getJustifiedNode(QuerySolution qs, JustifiedNode.ObjectTypeEnum objectType) {
        return setJustifiedNode(new JustifiedNode().objectType(objectType), qs);
    }

    public static <T extends JustifiedNode> T getJustifiedNodeObject(Class<T> nodeClass) {
        JustifiedNode ret;
        if(nodeClass == Entity.class) {
            ret = new Entity().objectType(ObjectTypeEnum.ENTITY);
        } else if(nodeClass == EntityMember.class) {
            ret = new EntityMember().objectType(ObjectTypeEnum.ENTITYMEMBER);
        } else if(nodeClass == EntityElement.class) {
            ret = new EntityElement().objectType(ObjectTypeEnum.ENTITYELEMENT);
        } else if(nodeClass == Member.class) {
            ret = new Member().objectType(ObjectTypeEnum.MEMBER);
        } else if(nodeClass == Element.class) {
            ret = new Element().objectType(ObjectTypeEnum.ELEMENT);
        } else {
            return null;
        }
        return nodeClass.cast(ret);
    }

    public static DocObject getDocInfo (String docId) {
        return getDocDocsInfo(docId);
    }

    private static final InputStream docinfofile = DataUtils.class.getResourceAsStream("/ltf/docinfo.json");
    private static final JSONObject docinfojson;
    static {
        JSONObject tmp = null;
        try {
            tmp = new JSONObject(IOUtils.toString(Objects.requireNonNull(docinfofile), StandardCharsets.UTF_8));
        } catch (Exception ex) {
            ex.printStackTrace();
        }
        docinfojson = tmp;
    }

    private static final JSONArray jsonArray = (JSONArray) docinfojson.get("documents");
    private static final Map<String, DocObject> docHashmap;
    static {
        Map<String, DocObject> tmp = new HashMap<>();

        try {
            for (int i = 0; i <jsonArray.length(); i++) {
                JSONObject obj = jsonArray.getJSONObject(i);
                DocObject docObject = new DocObject();
                docObject.id(obj.getString("field_docid") != null ? obj.getString("field_docid") : "");
                docObject.contentDate(obj.getString("field_content_date") != null && obj.getString("field_content_date") != "n/a" ? obj.getString("field_content_date") : "");
                docObject.downloadDate(obj.getString("field_download_date") != null && obj.getString("field_download_date") != "n/a" ? obj.getString("field_download_date") : "");
                docObject.title(obj.getString("title") != null ? obj.getString("title") : "");
                tmp.put(docObject.getId(), docObject);
            }
        } catch (Exception ex) {
            ex.printStackTrace();
        }
        docHashmap = tmp;
    }

    public static DocObject getDocDocsInfo (String docId) {
        DocObject docDocsObject = new DocObject();
        if(docHashmap.get(docId) == null) {
            docDocsObject.setId(docId);
        } else {
            docDocsObject = docHashmap.get(docId);
        }
        return docDocsObject;
    }

    public static List<String> getList(QuerySolution qs, String varName) {
        RDFNode node = qs.get(varName);
        if (node == null || node.toString().isEmpty()) {
            return Collections.emptyList();
        } else {
            List<String> list = Arrays.asList(node.toString().split(","));
            list.sort(String.CASE_INSENSITIVE_ORDER);
            return list;
        }
    }

    public static List<DocObject> getDocList(QuerySolution qs, String varName) {
        RDFNode node = qs.get(varName);

        if (node == null || node.toString().isEmpty()) {
            return Collections.emptyList();
        } else {
            List<String> list = Arrays.asList(node.toString().split(","));
            List<DocObject> listDoc = new ArrayList<>();
            for (String docId : list) {
                listDoc.add(getDocInfo(docId));
            }
            list.sort(String.CASE_INSENSITIVE_ORDER);
            return listDoc;
        }
    }

    public static List<DocObject> getDocDocsList(QuerySolution qs, String varName) {
        RDFNode node = qs.get(varName);

        if (node == null || node.toString().isEmpty()) {
            return Collections.emptyList();
        } else {
            List<String> list = Arrays.asList(node.toString().split(","));
            List<DocObject> listDocDocs = new ArrayList<>();
            for (String docId : list) {
                listDocDocs.add(getDocDocsInfo(docId));
            }
            list.sort(String.CASE_INSENSITIVE_ORDER);
            return listDocDocs;
        }
    }

    public static String getFilterString(List<String> filters, Function<String, String> map) {
        List<String> filterStrings = filters.stream()
            .map(map)
            .collect(Collectors.toList());
        return String.join(" UNION ", filterStrings);
    }

    public static String getFilterStringFilterOr(List<String> filters, Function<String, String> map) {
        List<String> filterStrings = filters.stream()
            .map(map)
            .collect(Collectors.toList());

            //String filterString = "FILTER( \"%s\" ) . ";

        return "FILTER(" +  String.join(" || ", filterStrings) + " ) . ";

    }

    private static String getDate(String date) {
        String dateStr = date.split(":")[2];
        // TODO: add logic to resolve missing pieces?
        return dateStr;
    }

    public static LDCTime getFormattedDate(List<String> dates) {
        LDCTime time = new LDCTime();
        for (String date : dates) {
            if (date.contains("start")) {
                if (date.contains(BEFORE)) {
                    time.startBefore(getDate(date));
                } else if (date.contains(AFTER)) {
                    time.startAfter(getDate(date));
                }
            } else if (date.contains("end")) {
                if (date.contains(BEFORE)) {
                    time.endBefore(getDate(date));
                } else if (date.contains(AFTER)) {
                    time.endAfter(getDate(date));
                }
            }
            // ignore if none of the above
        }
        return time;
    }
}
