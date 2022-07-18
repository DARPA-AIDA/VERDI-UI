package com.ncc.verdi.caching;

import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

public class HasRelations {
    protected String id;
    protected String category;
    protected Map<String, Set<HasRelations>> relations = new HashMap<>();

    public HasRelations(String id, String category) {
        this.id = id;
        this.category = category;
    }

    public boolean addRelations(String role, Set<HasRelations> arguments) {
        return relations.computeIfAbsent(role, key -> new HashSet<>()).addAll(arguments);
    }

    @Override
    public int hashCode() {
        return id.hashCode();
    }

    @Override
    public boolean equals(Object obj) {
        return obj != null && (obj instanceof HasRelations) && ((HasRelations)obj).id.equals(id);
    }

    public String getId() {
        return id;
    }
}
