package com.ncc.verdi.api;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.stream.Collectors;

import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.ncc.verdi.data.DataUtils;
import com.ncc.verdi.data.RDFHelper;
import com.ncc.verdi.model.Entity;
import com.ncc.verdi.model.EntityDetail;
import com.ncc.verdi.model.EntityElement;
import com.ncc.verdi.model.EntityMember;
import com.ncc.verdi.model.JustifiedNode;
import com.ncc.verdi.model.ReverseRole;

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
public class EntityApiController implements EntityApi {

    private static final Logger log = LoggerFactory.getLogger(EntityApiController.class);

    private final ObjectMapper objectMapper;

    private final HttpServletRequest request;

    @Autowired private RDFHelper rdfHelper;

    @org.springframework.beans.factory.annotation.Autowired
    public EntityApiController(ObjectMapper objectMapper, HttpServletRequest request) {
        this.objectMapper = objectMapper;
        this.request = request;
    }

    @Override
    public ResponseEntity<EntityDetail> entity(@NotNull @Valid String id, @Min(0) @Valid Integer dataLimit, @Valid String graph) {
        for (MediaType mediaType : MediaType.parseMediaTypes(request.getHeader("Accept"))) {
            if (mediaType.isCompatibleWith(MediaType.valueOf("application/json"))) {
                try {
                    return new ResponseEntity<>(getEntityDetail(rdfHelper, id, dataLimit, graph), HttpStatus.OK);
                } catch (Exception e) {
                    log.error("Unable to retrieve event details for: " + id, e);
                    return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
                }
            }
        }
        return new ResponseEntity<>(HttpStatus.NOT_IMPLEMENTED);
    }

    @Override
    public ResponseEntity<EntityElement> entityElement(@NotNull @Valid String id, @Valid String graph) {
        for (MediaType mediaType : MediaType.parseMediaTypes(request.getHeader("Accept"))) {
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

    public static EntityDetail getEntityDetail(RDFHelper rdfHelper, String id, int limit, String graph) throws ResourceAccessException {
        // Get members
        List<RDFNode> memberNodes = EventApiController.getMembers(rdfHelper, id, limit, graph);

        // Get justifications and roles for members
        List<EntityMember> members = memberNodes.stream().map(RDFNode::toString)
            .map(member -> getMember(rdfHelper, member))
            .filter(Objects::nonNull)
            .collect(Collectors.toList());

        // If prototype not in members, get prototype and prototype roles
        EventApiController.PrototypeCategoryObject prototypeCategoryObject = EventApiController.getPrototype(rdfHelper, id);
        EntityMember prototype = members.stream()
            .filter(member -> prototypeCategoryObject.prototype.equals(member.getId()))
            .findFirst()
            .orElse(getMember(rdfHelper, prototypeCategoryObject.prototype));
        prototype.setCategory(prototypeCategoryObject.category);
        return new EntityDetail()
            .handle(getHandle(rdfHelper, id))
            .cluster(id)
            .prototype(prototype)
            .members(members);
    }

    public static EntityMember getMember(RDFHelper rdfHelper, String id) {
        try {
            EntityMember entityMember = DataUtils.getJustifiedNodeObject(EntityMember.class);
            return setEntityNodeInfo(rdfHelper,  entityMember.connections(getReverseRoles(rdfHelper, id)), id);
        } catch (ResourceAccessException e) {
            return null;
        }
    }

    public static EntityElement getElement(RDFHelper rdfHelper, String id, String graph) {
        try {
            EntityElement ret = DataUtils.getJustifiedNodeObject(EntityElement.class);
            ret.connections(getElementReverseRoles(rdfHelper, id));
            setEntityNodeInfo(rdfHelper, ret, id);
            Map<String, String> replacements = DataUtils.getFromReplacements(graph);
            replacements.put("member", id);
            String query = DataUtils.replaceMultiple(DataUtils.getResourceStringFrom("sparql/get-entity-clusters.sparql"),
                replacements);
            rdfHelper.executeQueryString(query, qs -> {
                Literal handle = qs.getLiteral("handle");
                Entity entity = DataUtils.getJustifiedNodeObject(Entity.class);
                entity
                    .names(DataUtils.getList(qs, "names"))
                    .handle(handle == null ? null : handle.getString())
                    .id(qs.get("cluster").toString())
                    .types(DataUtils.getList(qs, "types"));
                ret.addClustersItem(entity);
            });
            return ret;
        } catch (ResourceAccessException e) {
            return null;
        }
    }

    public static <T extends Entity> T setEntityNodeInfo(RDFHelper rdfHelper, T node, String id) throws ResourceAccessException {
        String query = DataUtils.replace(DataUtils.getResourceStringFrom("sparql/get-type-docs-names.sparql"), "node", id);
        rdfHelper.executeQueryString(query, qs -> {
            node.names(DataUtils.getList(qs, "names"));
            DataUtils.setJustifiedNode(node, qs, id);
        });
        return node;
    }

    public static List<ReverseRole> getReverseRoles(RDFHelper rdfHelper, String id) throws ResourceAccessException {
        return getReverseRoles(rdfHelper, id, "sparql/entity-connections.sparql");
    }

    public static List<ReverseRole> getElementReverseRoles(RDFHelper rdfHelper, String id) throws ResourceAccessException {
        return getReverseRoles(rdfHelper, id, "sparql/entity-element-connections.sparql");
    }

    public static List<ReverseRole> getReverseRoles(RDFHelper rdfHelper, String id, String queryName) throws ResourceAccessException {
        String query = DataUtils.replace(DataUtils.getResourceStringFrom(queryName), "node", id);
        Map<RDFNode, ReverseRole> roles = new HashMap<>();
        rdfHelper.executeQueryString(query, qs -> {
            JustifiedNode node = DataUtils.getJustifiedNode(qs, JustifiedNode.ObjectTypeEnum.JUSTIFIEDNODE);
            roles.computeIfAbsent(qs.get("role"), key ->
                            new ReverseRole()
                                    .role(key.toString())
                                    .category(qs.get("category").toString()))
                    .addConnectionsItem(node);
        });
        return new ArrayList<>(roles.values());
    }

    public static String getHandle(RDFHelper rdfHelper, String id) throws ResourceAccessException {
        String query = DataUtils.replace(DataUtils.getResourceStringFrom("sparql/get-handle.sparql"), "cluster", id);
        rdfHelper.executeQueryString(query, qs -> qs.get("handle").toString());
        return null;
    }
}