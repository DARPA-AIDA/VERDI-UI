package com.ncc.verdi.model;

import java.util.Objects;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonCreator;
import com.ncc.verdi.model.ClaimFrameComponentObject;
import com.ncc.verdi.model.ClaimFrameProvenanceObject;
import com.ncc.verdi.model.ClaimFrameRelationObject;
import com.ncc.verdi.model.DocObject;
import com.ncc.verdi.model.LDCTime;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;
import org.openapitools.jackson.nullable.JsonNullable;
import javax.validation.Valid;
import javax.validation.constraints.*;

/**
 * ClaimFrameDetail
 */
@javax.annotation.Generated(value = "org.openapitools.codegen.languages.SpringCodegen", date = "2022-01-05T11:12:00.154-05:00[America/New_York]")

public class ClaimFrameDetail   {
  @JsonProperty("claimId")
  private String claimId;

  @JsonProperty("claimURI")
  private String claimURI;

  @JsonProperty("description")
  private String description;

  @JsonProperty("topic")
  private String topic;

  @JsonProperty("subtopic")
  private String subtopic;

  @JsonProperty("claimTemplate")
  private String claimTemplate;

  @JsonProperty("queryId")
  private String queryId;

  @JsonProperty("dates")
  @Valid
  private List<LDCTime> dates = null;

  @JsonProperty("importance")
  private BigDecimal importance;

  @JsonProperty("sourceDocument")
  private DocObject sourceDocument;

  @JsonProperty("system")
  private String system;

  @JsonProperty("epistemic")
  private String epistemic;

  @JsonProperty("sentiment")
  private String sentiment;

  @JsonProperty("components")
  @Valid
  private List<ClaimFrameComponentObject> components = null;

  @JsonProperty("relations")
  @Valid
  private List<ClaimFrameRelationObject> relations = null;

  @JsonProperty("provenances")
  @Valid
  private List<ClaimFrameProvenanceObject> provenances = null;

  public ClaimFrameDetail claimId(String claimId) {
    this.claimId = claimId;
    return this;
  }

  /**
   * Get claimId
   * @return claimId
  */
  @ApiModelProperty(value = "")


  public String getClaimId() {
    return claimId;
  }

  public void setClaimId(String claimId) {
    this.claimId = claimId;
  }

  public ClaimFrameDetail claimURI(String claimURI) {
    this.claimURI = claimURI;
    return this;
  }

  /**
   * Get claimURI
   * @return claimURI
  */
  @ApiModelProperty(value = "")


  public String getClaimURI() {
    return claimURI;
  }

  public void setClaimURI(String claimURI) {
    this.claimURI = claimURI;
  }

  public ClaimFrameDetail description(String description) {
    this.description = description;
    return this;
  }

  /**
   * Get description
   * @return description
  */
  @ApiModelProperty(value = "")


  public String getDescription() {
    return description;
  }

  public void setDescription(String description) {
    this.description = description;
  }

  public ClaimFrameDetail topic(String topic) {
    this.topic = topic;
    return this;
  }

  /**
   * Get topic
   * @return topic
  */
  @ApiModelProperty(value = "")


  public String getTopic() {
    return topic;
  }

  public void setTopic(String topic) {
    this.topic = topic;
  }

  public ClaimFrameDetail subtopic(String subtopic) {
    this.subtopic = subtopic;
    return this;
  }

  /**
   * Get subtopic
   * @return subtopic
  */
  @ApiModelProperty(value = "")


  public String getSubtopic() {
    return subtopic;
  }

  public void setSubtopic(String subtopic) {
    this.subtopic = subtopic;
  }

  public ClaimFrameDetail claimTemplate(String claimTemplate) {
    this.claimTemplate = claimTemplate;
    return this;
  }

  /**
   * Get claimTemplate
   * @return claimTemplate
  */
  @ApiModelProperty(value = "")


  public String getClaimTemplate() {
    return claimTemplate;
  }

  public void setClaimTemplate(String claimTemplate) {
    this.claimTemplate = claimTemplate;
  }

  public ClaimFrameDetail queryId(String queryId) {
    this.queryId = queryId;
    return this;
  }

  /**
   * Get queryId
   * @return queryId
  */
  @ApiModelProperty(value = "")


  public String getQueryId() {
    return queryId;
  }

  public void setQueryId(String queryId) {
    this.queryId = queryId;
  }

  public ClaimFrameDetail dates(List<LDCTime> dates) {
    this.dates = dates;
    return this;
  }

  public ClaimFrameDetail addDatesItem(LDCTime datesItem) {
    if (this.dates == null) {
      this.dates = new ArrayList<>();
    }
    this.dates.add(datesItem);
    return this;
  }

  /**
   * Get dates
   * @return dates
  */
  @ApiModelProperty(value = "")

  @Valid

  public List<LDCTime> getDates() {
    return dates;
  }

  public void setDates(List<LDCTime> dates) {
    this.dates = dates;
  }

  public ClaimFrameDetail importance(BigDecimal importance) {
    this.importance = importance;
    return this;
  }

  /**
   * Get importance
   * @return importance
  */
  @ApiModelProperty(value = "")

  @Valid

  public BigDecimal getImportance() {
    return importance;
  }

  public void setImportance(BigDecimal importance) {
    this.importance = importance;
  }

  public ClaimFrameDetail sourceDocument(DocObject sourceDocument) {
    this.sourceDocument = sourceDocument;
    return this;
  }

  /**
   * Get sourceDocument
   * @return sourceDocument
  */
  @ApiModelProperty(value = "")

  @Valid

  public DocObject getSourceDocument() {
    return sourceDocument;
  }

  public void setSourceDocument(DocObject sourceDocument) {
    this.sourceDocument = sourceDocument;
  }

  public ClaimFrameDetail system(String system) {
    this.system = system;
    return this;
  }

  /**
   * Get system
   * @return system
  */
  @ApiModelProperty(value = "")


  public String getSystem() {
    return system;
  }

  public void setSystem(String system) {
    this.system = system;
  }

  public ClaimFrameDetail epistemic(String epistemic) {
    this.epistemic = epistemic;
    return this;
  }

  /**
   * Get epistemic
   * @return epistemic
  */
  @ApiModelProperty(value = "")


  public String getEpistemic() {
    return epistemic;
  }

  public void setEpistemic(String epistemic) {
    this.epistemic = epistemic;
  }

  public ClaimFrameDetail sentiment(String sentiment) {
    this.sentiment = sentiment;
    return this;
  }

  /**
   * Get sentiment
   * @return sentiment
  */
  @ApiModelProperty(value = "")


  public String getSentiment() {
    return sentiment;
  }

  public void setSentiment(String sentiment) {
    this.sentiment = sentiment;
  }

  public ClaimFrameDetail components(List<ClaimFrameComponentObject> components) {
    this.components = components;
    return this;
  }

  public ClaimFrameDetail addComponentsItem(ClaimFrameComponentObject componentsItem) {
    if (this.components == null) {
      this.components = new ArrayList<>();
    }
    this.components.add(componentsItem);
    return this;
  }

  /**
   * Get components
   * @return components
  */
  @ApiModelProperty(value = "")

  @Valid

  public List<ClaimFrameComponentObject> getComponents() {
    return components;
  }

  public void setComponents(List<ClaimFrameComponentObject> components) {
    this.components = components;
  }

  public ClaimFrameDetail relations(List<ClaimFrameRelationObject> relations) {
    this.relations = relations;
    return this;
  }

  public ClaimFrameDetail addRelationsItem(ClaimFrameRelationObject relationsItem) {
    if (this.relations == null) {
      this.relations = new ArrayList<>();
    }
    this.relations.add(relationsItem);
    return this;
  }

  /**
   * Get relations
   * @return relations
  */
  @ApiModelProperty(value = "")

  @Valid

  public List<ClaimFrameRelationObject> getRelations() {
    return relations;
  }

  public void setRelations(List<ClaimFrameRelationObject> relations) {
    this.relations = relations;
  }

  public ClaimFrameDetail provenances(List<ClaimFrameProvenanceObject> provenances) {
    this.provenances = provenances;
    return this;
  }

  public ClaimFrameDetail addProvenancesItem(ClaimFrameProvenanceObject provenancesItem) {
    if (this.provenances == null) {
      this.provenances = new ArrayList<>();
    }
    this.provenances.add(provenancesItem);
    return this;
  }

  /**
   * Get provenances
   * @return provenances
  */
  @ApiModelProperty(value = "")

  @Valid

  public List<ClaimFrameProvenanceObject> getProvenances() {
    return provenances;
  }

  public void setProvenances(List<ClaimFrameProvenanceObject> provenances) {
    this.provenances = provenances;
  }


  @Override
  public boolean equals(java.lang.Object o) {
    if (this == o) {
      return true;
    }
    if (o == null || getClass() != o.getClass()) {
      return false;
    }
    ClaimFrameDetail claimFrameDetail = (ClaimFrameDetail) o;
    return Objects.equals(this.claimId, claimFrameDetail.claimId) &&
        Objects.equals(this.claimURI, claimFrameDetail.claimURI) &&
        Objects.equals(this.description, claimFrameDetail.description) &&
        Objects.equals(this.topic, claimFrameDetail.topic) &&
        Objects.equals(this.subtopic, claimFrameDetail.subtopic) &&
        Objects.equals(this.claimTemplate, claimFrameDetail.claimTemplate) &&
        Objects.equals(this.queryId, claimFrameDetail.queryId) &&
        Objects.equals(this.dates, claimFrameDetail.dates) &&
        Objects.equals(this.importance, claimFrameDetail.importance) &&
        Objects.equals(this.sourceDocument, claimFrameDetail.sourceDocument) &&
        Objects.equals(this.system, claimFrameDetail.system) &&
        Objects.equals(this.epistemic, claimFrameDetail.epistemic) &&
        Objects.equals(this.sentiment, claimFrameDetail.sentiment) &&
        Objects.equals(this.components, claimFrameDetail.components) &&
        Objects.equals(this.relations, claimFrameDetail.relations) &&
        Objects.equals(this.provenances, claimFrameDetail.provenances);
  }

  @Override
  public int hashCode() {
    return Objects.hash(claimId, claimURI, description, topic, subtopic, claimTemplate, queryId, dates, importance, sourceDocument, system, epistemic, sentiment, components, relations, provenances);
  }

  @Override
  public String toString() {
    StringBuilder sb = new StringBuilder();
    sb.append("class ClaimFrameDetail {\n");
    
    sb.append("    claimId: ").append(toIndentedString(claimId)).append("\n");
    sb.append("    claimURI: ").append(toIndentedString(claimURI)).append("\n");
    sb.append("    description: ").append(toIndentedString(description)).append("\n");
    sb.append("    topic: ").append(toIndentedString(topic)).append("\n");
    sb.append("    subtopic: ").append(toIndentedString(subtopic)).append("\n");
    sb.append("    claimTemplate: ").append(toIndentedString(claimTemplate)).append("\n");
    sb.append("    queryId: ").append(toIndentedString(queryId)).append("\n");
    sb.append("    dates: ").append(toIndentedString(dates)).append("\n");
    sb.append("    importance: ").append(toIndentedString(importance)).append("\n");
    sb.append("    sourceDocument: ").append(toIndentedString(sourceDocument)).append("\n");
    sb.append("    system: ").append(toIndentedString(system)).append("\n");
    sb.append("    epistemic: ").append(toIndentedString(epistemic)).append("\n");
    sb.append("    sentiment: ").append(toIndentedString(sentiment)).append("\n");
    sb.append("    components: ").append(toIndentedString(components)).append("\n");
    sb.append("    relations: ").append(toIndentedString(relations)).append("\n");
    sb.append("    provenances: ").append(toIndentedString(provenances)).append("\n");
    sb.append("}");
    return sb.toString();
  }

  /**
   * Convert the given object to string with each line indented by 4 spaces
   * (except the first line).
   */
  private String toIndentedString(java.lang.Object o) {
    if (o == null) {
      return "null";
    }
    return o.toString().replace("\n", "\n    ");
  }
}

