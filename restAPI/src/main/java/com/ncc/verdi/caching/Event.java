package com.ncc.verdi.caching;

import java.io.IOException;
import java.text.DateFormatSymbols;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

import com.ncc.verdi.model.ClaimFrameProvenanceObjectValues;
import com.ncc.verdi.model.DocObject;
import com.ncc.verdi.model.LDCTime;

import org.elasticsearch.common.xcontent.XContentBuilder;
import org.elasticsearch.common.xcontent.XContentFactory;

/*
* Event ids are non-unique across hyptheses, even though their content might differ.
*   Hypothesis ids are non-unique across sins, even though their content might differ.
*   Claim ids span across all runs. The following must be combined to uniquely
*   identify an event: run, claim, and id.
*/
public class Event{
    //TODO: cached event code will need to be refactored since we are no longer getting event information from sins and hypotheses
    //private static final String TYPE_SPLIT = "\\.";

    private static String URI_DELIMITER = "#";
    static String removeUriPrefix(String uri) {
        return uri.contains(URI_DELIMITER) ? uri.split(URI_DELIMITER)[1] : uri;
    }
    private static String ROLE_DELIMITER = "_";
    static String removeRolePrefix(String uri) {
        return removeUriPrefix(uri).split(ROLE_DELIMITER, 2)[1];
    }

    public static String getEventId(String claim, String run, String id) {
        return claim + run  + id;
    }

    // Instance variables
    private String claim;
    private String run;
    private String fullType;
    private Map<String, Set<Entity>> roles = new HashMap<>();
    private String english;
    private ClaimFrameProvenanceObjectValues values;
    private Set<LDCTime> times = new HashSet<>();
    private Map<Entity, Set<DocObject>> docs = new HashMap<>();

    public Event(String claim, String run, String id, Map<String, Entity> entities) throws IOException {
        super();

        this.claim = claim;
        this.run = run;

        fullType = removeUriPrefix(values.getTypes().get(0));
/*         String[] types = fullType.split(TYPE_SPLIT);
        String type = types[0];
        String subtype = types.length > 1 ? types[1] : null;
        String subsubtype = types.length > 2 ? types[2] : null; */

        Map<String, Set<Entity>> rolesCopy = new HashMap<>();
        for (String key : roles.keySet()) {
            rolesCopy.put(removeRolePrefix(key), roles.get(key));
        }
    }

    public String getClaim() {
        return english;
    }

    public List<String> getRoles() {
        return List.copyOf(roles.keySet());
    }

    public void addDoc(DocObject doc, Entity entity) {
        docs.computeIfAbsent(entity, key -> new HashSet<>()).add(doc);
    }

    private static class Time {
        String year;
        int month;
        String date;
        private Time(String time) {
            String[] parts = time.split("--");
            year = parts[0];
            if (parts[1].contains("-")) {
                month = 0;
                date = parts[1].substring(1);
            } else {
                month = Integer.parseInt(parts[1]) - 1;
                date = parts[2].substring(1);
            }
        }

        private static String combine(String first, String second) {
            return first + " \u2013 " + second;
        }

        String getDurationString(Time other) {
            if (other == null) {
                return toString();
            } else if (!year.equals(other.year)) {
                return combine(toString(), other.toString());
            } else if (month != other.month) {
                return year + " " + combine(getMonthDate(), other.getMonthDate());
            } else if (!date.equals(other.date)) {
                return combine(toString(), other.date);
            } else {
                return toString();
            }
        }

        @Override
        public String toString() {
            return year + " " + getMonthDate();
        }

        private String getMonthDate() {
            return DateFormatSymbols.getInstance().getShortMonths()[month] + " " + date;
        }
    }
    private static String getDateString(LDCTime time) {
        String start = time.getStartAfter() != null ? time.getStartAfter() : time.getStartBefore();
        String end = time.getEndBefore() != null ? time.getEndBefore() : time.getEndAfter();
        return new Time(start).getDurationString(new Time(end));
    }

    public XContentBuilder getBuilder() throws IOException {
        XContentBuilder builder = XContentFactory.jsonBuilder()
            .startObject()
            .field("cluster", values.getKeId())
            .field("cluster_category", "event")
            .field("cluster_type", fullType)
/*             .field("id", "do we need this?") // do we need this?
            .field("prototype_uri", member.getPrototype()) */
            .field("statement", getClaim());

        // add dates
        builder.startArray("date");
        for (LDCTime time : times) {
            builder.value(getDateString(time));
        }
        builder.endArray()
            .field("category", "do we need this?") // do we need this? dupe cluster_category?
            .field("claim", claim)
            .field("run", run);

        // add entities
        builder.startArray("entities");
        for (Map.Entry<String, Set<Entity>> role : roles.entrySet()) {
            for (Entity entity : role.getValue()) {
                entity.addToBuilder(builder, role.getKey());
            }
        }
        builder.endArray();

        // add docs
        builder.startArray("docs");
        for (Map.Entry<Entity, Set<DocObject>> entry : docs.entrySet()) {
            String handle = entry.getKey().getHandle();
            for (DocObject doc : entry.getValue()) {
                builder.startObject()
                    .field("document_id", doc.getId())
                    .field("document_title", doc.getTitle())
                    .field("document_contentDate", doc.getContentDate())
                    .field("document_downloadDate", doc.getDownloadDate())
                    .field("headline", handle)
                    .endObject();
            }
        }
        builder.endArray();

        return builder.endObject();
    }
}
