package com.ncc.verdi.api;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.stream.Collectors;

import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;
import javax.validation.constraints.NotNull;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.ncc.verdi.data.DataUtils;
import com.ncc.verdi.data.RDFHelper;
import com.ncc.verdi.model.Element;
import com.ncc.verdi.model.Entity;
import com.ncc.verdi.model.Event;
import com.ncc.verdi.model.JustifiedNode;
import com.ncc.verdi.model.JustifiedNode.ObjectTypeEnum;
import com.ncc.verdi.model.Member;
import com.ncc.verdi.model.Role;

import org.apache.jena.rdf.model.Literal;
import org.apache.jena.rdf.model.RDFNode;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.ResourceAccessException;

@RestController
public class EventApiController implements EventApi {

    private static final Logger log = LoggerFactory.getLogger(EventApiController.class);
    private final ObjectMapper objectMapper;
    private final HttpServletRequest request;

    public static class PrototypeCategoryObject
    {
        public String prototype, category;

        public PrototypeCategoryObject(String prototype, String category)
        {
            this.prototype = prototype;
            this.category = category;
        }
    }

    @Autowired private RDFHelper rdfHelper;

    @Autowired
    public EventApiController(ObjectMapper objectMapper, HttpServletRequest request) {
        this.objectMapper = objectMapper;
        this.request = request;
    }

    @Override
    public ResponseEntity<Event> event(@NotNull @Valid String id, @Valid String graph) {
        for (MediaType mediaType: MediaType.parseMediaTypes(request.getHeader("Accept"))) {
            if (mediaType.isCompatibleWith(MediaType.valueOf("application/json"))) {
                try {
                    return new ResponseEntity<>(getEvent(rdfHelper, id, graph), HttpStatus.OK);
                } catch (Exception e) {
                    log.error("Unable to retrieve event details for: " + id, e);
                    return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
                }
            }
        }
        return new ResponseEntity<>(HttpStatus.NOT_IMPLEMENTED);
    }

    @Override
    public ResponseEntity<Element> eventElement(@NotNull @Valid String id, @Valid String graph) {
        for (MediaType mediaType: MediaType.parseMediaTypes(request.getHeader("Accept"))) {
            if (mediaType.isCompatibleWith(MediaType.valueOf("application/json"))) {
                try {
                    return new ResponseEntity<>(getElement(rdfHelper, id, graph), HttpStatus.OK);
                } catch (Exception e) {
                    log.error("Unable to retrieve event details for: " + id, e);
                    return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
                }
            }
        }
        return new ResponseEntity<>(HttpStatus.NOT_IMPLEMENTED);
    }

    public static Event getEvent(RDFHelper rdfHelper, String id, String graph) throws ResourceAccessException {
        // Get members
        List<RDFNode> memberNodes = getMembers(rdfHelper, id, graph);

        // Get justifications and roles for members
        List<Member> members = memberNodes.stream()
            .map(RDFNode::toString)
            .map(memberId -> getMember(rdfHelper, memberId, graph))
            .filter(Objects::nonNull)
            .collect(Collectors.toList());

        // If prototype not in members, get prototype and prototype roles
        PrototypeCategoryObject prototypeObject = getPrototype(rdfHelper, id);
        Member prototype = members.stream()
            .filter(member -> prototypeObject.prototype.equals(member.getId()))
            .findFirst()
            .orElse(getMember(rdfHelper, prototypeObject.prototype, graph));
        return new Event().cluster(id).handle(getHandle(rdfHelper, id)).prototype(prototype).category(prototypeObject.category).members(members);
    }

    public static Member getMember(RDFHelper rdfHelper, String id, String graph) {
        try {
            Member member = DataUtils.getJustifiedNodeObject(Member.class);
            return setJustifiedNodeInfo(rdfHelper, member.arguments(RolesApiController.getRoles(rdfHelper, id, graph)), id);
        } catch (ResourceAccessException e) {
            return null;
        }
    }

    public static Element getElement(RDFHelper rdfHelper, String id, String graph) {
        try {
            Element ret = DataUtils.getJustifiedNodeObject(Element.class);
            setJustifiedNodeInfo(rdfHelper, ret.arguments(getElementRoles(rdfHelper, id, graph)), id);
            String query = DataUtils.replace(DataUtils.getResourceStringFrom("sparql/get-clusters.sparql"),
                "member", id);
            rdfHelper.executeQueryString(query, qs -> {
                Literal handle = qs.getLiteral("handle");
                ret.addClustersItem(new JustifiedNode()
                    .objectType(ObjectTypeEnum.JUSTIFIEDNODE)
                    .id(qs.get("cluster").toString())
                    .handle(handle == null ? null : handle.getString())
                    .types(DataUtils.getList(qs, "types")));
            });
            return ret;
        } catch (ResourceAccessException e) {
            return null;
        }
    }

    public static List<Role> getElementRoles(RDFHelper rdfHelper, String id, String graph) {
        Map<RDFNode, Role> roles = new HashMap<>();

        Map<String, String> replacements = DataUtils.getFromReplacements(graph);
        replacements.put("node", id);

        String query = DataUtils.replaceMultiple(DataUtils.getResourceStringFrom("sparql/element-arguments.sparql"),
            replacements);

        rdfHelper.executeQueryString(query, qs -> {
            Role role = roles.computeIfAbsent(qs.get("role"), key -> new Role().role(key.toString()));
            Literal handle = qs.getLiteral("handle");
            RDFNode node = qs.get("node");
            Entity entity = DataUtils.setJustifiedNode(DataUtils.getJustifiedNodeObject(Entity.class), qs);
            entity
                .category(qs.get("category").toString())
                .names(DataUtils.getList(qs, "names"))
                .clusterIds(DataUtils.getList(qs, "clusters"))
                .handle(handle == null ? node.toString() : handle.getString());
            if (entity.getNames().isEmpty()) {
                entity.names(List.of(node.toString()));
            }
            role.addFillersItem(entity);
        });
        return new ArrayList<>(roles.values());
    }

    public static <T extends JustifiedNode> T setJustifiedNodeInfo(RDFHelper rdfHelper, T node, String id) throws ResourceAccessException {
        String query = DataUtils.replace(DataUtils.getResourceStringFrom("sparql/get-type-docs.sparql"),
            "node", id);
        rdfHelper.executeQueryString(query, qs -> DataUtils.setJustifiedNode(node, qs, id));
        return node;
    }

    public static PrototypeCategoryObject getPrototype(RDFHelper rdfHelper, String id) throws ResourceAccessException {
        PrototypeCategoryObject prototypeObject = new PrototypeCategoryObject("", "");
        String query = DataUtils.replace(DataUtils.getResourceStringFrom("sparql/get-prototype.sparql"),
            "cluster", id);
        List<String> prototypes = new ArrayList<>();
        rdfHelper.executeQueryString(query, qs -> {
            prototypes.add(qs.get("prototype").toString());
            prototypeObject.prototype = prototypes.isEmpty() ? "" : prototypes.get(0);
            prototypeObject.category = qs.get("category").toString();
            
        });
        return prototypeObject;
    }

    public static String getHandle(RDFHelper rdfHelper, String id) throws ResourceAccessException {
        String query = DataUtils.replace(DataUtils.getResourceStringFrom("sparql/get-handle.sparql"), "cluster", id);
        List<String> handles = new ArrayList<>();
        rdfHelper.executeQueryString(query, qs -> handles.add(qs.get("handle").toString()));
        return handles.isEmpty() ? null : handles.get(0);
    }

    public static List<RDFNode> getMembers(RDFHelper rdfHelper, String id, String graph) throws ResourceAccessException {
        return getMembers(rdfHelper, id, 0, graph);
    }
    public static List<RDFNode> getMembers(RDFHelper rdfHelper, String id, int limit, String graph) throws ResourceAccessException {
        Map<String, String> replacements = DataUtils.getFromReplacements(graph);
        replacements.put("cluster", id);
        String query = DataUtils.replaceMultiple(DataUtils.getResourceStringFrom("sparql/get-members.sparql"),
            replacements);
        String limitReplacement = limit > 0 ? "limit " + limit : "";
        query = DataUtils.replace(query, "limit", limitReplacement);

        List<RDFNode> memberNodes = new ArrayList<>();
        rdfHelper.executeQueryString(query, qs -> memberNodes.add(qs.get("member")));
        return memberNodes;
    }
}