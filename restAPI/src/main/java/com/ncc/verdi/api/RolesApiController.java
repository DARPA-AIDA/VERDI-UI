package com.ncc.verdi.api;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;
import javax.validation.constraints.NotNull;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.ncc.verdi.data.DataUtils;
import com.ncc.verdi.data.RDFHelper;
import com.ncc.verdi.model.Entity;
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
public class RolesApiController implements RolesApi {

    private static final Logger log = LoggerFactory.getLogger(RolesApiController.class);

    private final ObjectMapper objectMapper;

    private final HttpServletRequest request;

    @Autowired private RDFHelper rdfHelper;

    @Autowired
    public RolesApiController(ObjectMapper objectMapper, HttpServletRequest request) {
        this.objectMapper = objectMapper;
        this.request = request;
    }

    @Override
    public ResponseEntity<List<Role>> roles(@NotNull @Valid String id, @Valid String graph) {
        for (MediaType mediaType: MediaType.parseMediaTypes(request.getHeader("Accept"))) {
            if (mediaType.isCompatibleWith(MediaType.valueOf("application/json"))) {
                try {
                    return new ResponseEntity<>(getRoles(rdfHelper, id, graph), HttpStatus.OK);
                } catch (Exception e) {
                    log.error("Unable to get roles for: " + id, e);
                    return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
                }
            }
        }
        return new ResponseEntity<>(HttpStatus.NOT_IMPLEMENTED);
    }

    public static List<Role> getRoles(RDFHelper rdfHelper, @NotNull String id, String graph) throws ResourceAccessException {
        Map<RDFNode, Role> roles = new HashMap<>();
        Map<String, String> replacements = DataUtils.getFromReplacements(graph);
        replacements.put("node", id);
        String query = DataUtils.replaceMultiple(DataUtils.getResourceStringFrom("sparql/member-arguments.sparql"),
                replacements);

        rdfHelper.executeQueryString(query, qs -> {
            Role role = roles.computeIfAbsent(qs.get("role"), key -> new Role().role(key.toString()));
            Entity entityObject = DataUtils.getJustifiedNodeObject(Entity.class);
            Entity entity = DataUtils.setJustifiedNode(entityObject.names(DataUtils.getList(qs, "names")), qs);
            Literal handle = qs.getLiteral("handle");

            entity
                .category(qs.get("category").toString())
                .handle(handle == null ? null : handle.getString());
            role.addFillersItem(entity);
        });
        return new ArrayList<>(roles.values());
    }
}
