package com.ncc.verdi.caching;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.HashMap;
import java.util.HashSet;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Queue;
import java.util.Set;


import org.elasticsearch.common.xcontent.XContentBuilder;

public class Entity {
    private static final String TYPE_DELIMETER = ".";
    private static final String TYPE_DELIMETER_REGEX = "\\" + TYPE_DELIMETER;
    private static final String UPPER_REGEX = "(?=[A-Z])";

    private String handle;
    private Set<String> names = new HashSet<>();
    private Set<String> types = new HashSet<>();
    private String resolvedType;
    private Set<String> runs = new HashSet<>();
    private Set<String> roles = new HashSet<>();
    private Set<String> eventTypes = new HashSet<>();

    public Entity(String run) {
        super();
        //TODO: cached entity code will need to be refactored since we are no longer getting argument information from hypotheses
/*         super(argument.getId(), argument.getCategory());

        types.addAll(argument.getTypes());
        resolvedType = getResolvedType(types);

        handle = DataUtils.decodeLTFRef(argument.getHandle());
        if (handle == null || handle.isEmpty()) {
            handle = getHumanReadableType(resolvedType);
        } else {
            names.add(handle);
        }
        names.addAll(argument.getNames()); */
        runs.add(run);

        //this.argument = argument;
    }

    @Override
    public String toString() {
        return handle;
    }

    private static class Node {
        private String part;
        private int count = 0;
        private Map<String, Node> children = new HashMap<>();
        private Node(String part) {
            this.part = part;
        }
        private void add(Queue<String> parts) {
            count++;
            if (parts.isEmpty()) {
                return;
            }
            children.computeIfAbsent(parts.remove(), Node::new).add(parts);
        }
        private Node getNext() {
            List<Node> sorted = new ArrayList<>(children.values());
            sorted.sort(Comparator.comparing(Node::getCount));
            return sorted.get(0);
        }
        private int getCount() {
            return count;
        }
    }

    public static String getResolvedType(Set<String> types) {
        switch (types.size()) {
            case 0:
                return "None";
            case 1:
                return Event.removeUriPrefix(types.iterator().next());
            default:
                Node tree = new Node("root");
                for (String type : types) {
                    // remove prefix and split by delimter
                    tree.add(new LinkedList<>(List.of(Event.removeUriPrefix(type).split(TYPE_DELIMETER_REGEX))));
                }

                // not possible for last part to be the same
                Node first = tree.getNext();
                Node second = first.getNext();
                return first.part + (first.count == second.count ? "." + second.part : "");
        }
    }

    // TODO: this is dirty. Should have resource for mapping, but don't have time
    public static String getHumanReadableType(String type) {
        String noPrefix = Event.removeUriPrefix(type);
        int index = noPrefix.contains(TYPE_DELIMETER) ? noPrefix.lastIndexOf(TYPE_DELIMETER) + 1 : 0;
        String specificType = noPrefix.substring(index);
        switch(specificType) {
            case "GPE":
                return "Geopolitical Entity";
            case "FAC":
                return "Facility";
            case "LOC":
                return "Location";
            case "ORG":
                return "Organization";
            case "PER":
                return "Person";
            case "VAL":
                return "Value";
            case "VEH":
                return "Vehicle";
            case "WEA":
                return "Weapon";
            case "StartGPE":
                return "Form A Government";
            default:
                return String.join(" ", specificType.split(UPPER_REGEX));
        }
    }

    public void addToBuilder(XContentBuilder builder, String role) throws IOException {
        String[] toString = new String[0];
        builder.startObject()
            .field("headline", handle)
            .array("names", names.toArray(toString))
            .array("role_uri", new String[]{ role })
            .array("role", Event.removeRolePrefix(role))
/*             .field("id", id)
            .field("prototype_uri", argument.getPrototype()) */
            .array("types", types.stream().map(Event::removeUriPrefix).toArray(String[]::new))
            .field("resolved_type", resolvedType)
            .endObject();
    }

    public String getHandle() {
        return handle;
    }

    public Set<String> getNames() {
        return names;
    }

    public Set<String> getRoles() {
        return roles;
    }

    public Set<String> getEventTypes() {
        return eventTypes;
    }

    public void addRole(String eventType, String role) {
        eventTypes.add(eventType);
        roles.add(role);
    }
}
