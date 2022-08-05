package com.ncc.verdi.model;

import java.util.Objects;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonCreator;
import com.ncc.verdi.model.ClaimFrameComponentObject;
import com.ncc.verdi.model.ClaimFrameRelationObject;
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
 * ClaimFrame
 */
@javax.annotation.Generated(value = "org.openapitools.codegen.languages.SpringCodegen", date = "2022-08-05T15:31:00.866110500-04:00[America/New_York]")

public class ClaimFrame   {
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

  @JsonProperty("components")
  @Valid
  private List<ClaimFrameComponentObject> components = null;

  @JsonProperty("claimer")
  private String claimer;

  @JsonProperty("epistemic")
  private String epistemic;

  @JsonProperty("sourceDoc")
  private String sourceDoc;

  @JsonProperty("sourceDocTitle")
  private String sourceDocTitle;

  @JsonProperty("xVariable")
  private String xVariable;

  @JsonProperty("xVariableCompId")
  private String xVariableCompId;

  @JsonProperty("claimerKE")
  private String claimerKE;

  @JsonProperty("queryClaimId")
  private String queryClaimId;

  @JsonProperty("ranking")
  private String ranking;

  @JsonProperty("claimRelations")
  private String claimRelations;

  @JsonProperty("dates")
  @Valid
  private List<LDCTime> dates = null;

  @JsonProperty("importance")
  private BigDecimal importance;

  @JsonProperty("locationName")
  private String locationName;

  @JsonProperty("relations")
  @Valid
  private List<ClaimFrameRelationObject> relations = null;

  @JsonProperty("claimerAffiliation")
  private String claimerAffiliation;

  public ClaimFrame claimId(String claimId) {
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

  public ClaimFrame claimURI(String claimURI) {
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

  public ClaimFrame description(String description) {
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

  public ClaimFrame topic(String topic) {
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

  public ClaimFrame subtopic(String subtopic) {
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

  public ClaimFrame claimTemplate(String claimTemplate) {
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

  public ClaimFrame queryId(String queryId) {
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

  public ClaimFrame components(List<ClaimFrameComponentObject> components) {
    this.components = components;
    return this;
  }

  public ClaimFrame addComponentsItem(ClaimFrameComponentObject componentsItem) {
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

  public ClaimFrame claimer(String claimer) {
    this.claimer = claimer;
    return this;
  }

  /**
   * Get claimer
   * @return claimer
  */
  @ApiModelProperty(value = "")


  public String getClaimer() {
    return claimer;
  }

  public void setClaimer(String claimer) {
    this.claimer = claimer;
  }

  public ClaimFrame epistemic(String epistemic) {
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

  public ClaimFrame sourceDoc(String sourceDoc) {
    this.sourceDoc = sourceDoc;
    return this;
  }

  /**
   * Get sourceDoc
   * @return sourceDoc
  */
  @ApiModelProperty(value = "")


  public String getSourceDoc() {
    return sourceDoc;
  }

  public void setSourceDoc(String sourceDoc) {
    this.sourceDoc = sourceDoc;
  }

  public ClaimFrame sourceDocTitle(String sourceDocTitle) {
    this.sourceDocTitle = sourceDocTitle;
    return this;
  }

  /**
   * Get sourceDocTitle
   * @return sourceDocTitle
  */
  @ApiModelProperty(value = "")


  public String getSourceDocTitle() {
    return sourceDocTitle;
  }

  public void setSourceDocTitle(String sourceDocTitle) {
    this.sourceDocTitle = sourceDocTitle;
  }

  public ClaimFrame xVariable(String xVariable) {
    this.xVariable = xVariable;
    return this;
  }

  /**
   * Get xVariable
   * @return xVariable
  */
  @ApiModelProperty(value = "")


  public String getxVariable() {
    return xVariable;
  }

  public void setxVariable(String xVariable) {
    this.xVariable = xVariable;
  }

  public ClaimFrame xVariableCompId(String xVariableCompId) {
    this.xVariableCompId = xVariableCompId;
    return this;
  }

  /**
   * Get xVariableCompId
   * @return xVariableCompId
  */
  @ApiModelProperty(value = "")


  public String getxVariableCompId() {
    return xVariableCompId;
  }

  public void setxVariableCompId(String xVariableCompId) {
    this.xVariableCompId = xVariableCompId;
  }

  public ClaimFrame claimerKE(String claimerKE) {
    this.claimerKE = claimerKE;
    return this;
  }

  /**
   * Get claimerKE
   * @return claimerKE
  */
  @ApiModelProperty(value = "")


  public String getClaimerKE() {
    return claimerKE;
  }

  public void setClaimerKE(String claimerKE) {
    this.claimerKE = claimerKE;
  }

  public ClaimFrame queryClaimId(String queryClaimId) {
    this.queryClaimId = queryClaimId;
    return this;
  }

  /**
   * Get queryClaimId
   * @return queryClaimId
  */
  @ApiModelProperty(value = "")


  public String getQueryClaimId() {
    return queryClaimId;
  }

  public void setQueryClaimId(String queryClaimId) {
    this.queryClaimId = queryClaimId;
  }

  public ClaimFrame ranking(String ranking) {
    this.ranking = ranking;
    return this;
  }

  /**
   * Get ranking
   * @return ranking
  */
  @ApiModelProperty(value = "")


  public String getRanking() {
    return ranking;
  }

  public void setRanking(String ranking) {
    this.ranking = ranking;
  }

  public ClaimFrame claimRelations(String claimRelations) {
    this.claimRelations = claimRelations;
    return this;
  }

  /**
   * Get claimRelations
   * @return claimRelations
  */
  @ApiModelProperty(value = "")


  public String getClaimRelations() {
    return claimRelations;
  }

  public void setClaimRelations(String claimRelations) {
    this.claimRelations = claimRelations;
  }

  public ClaimFrame dates(List<LDCTime> dates) {
    this.dates = dates;
    return this;
  }

  public ClaimFrame addDatesItem(LDCTime datesItem) {
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

  public ClaimFrame importance(BigDecimal importance) {
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

  public ClaimFrame locationName(String locationName) {
    this.locationName = locationName;
    return this;
  }

  /**
   * Get locationName
   * @return locationName
  */
  @ApiModelProperty(value = "")


  public String getLocationName() {
    return locationName;
  }

  public void setLocationName(String locationName) {
    this.locationName = locationName;
  }

  public ClaimFrame relations(List<ClaimFrameRelationObject> relations) {
    this.relations = relations;
    return this;
  }

  public ClaimFrame addRelationsItem(ClaimFrameRelationObject relationsItem) {
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

  public ClaimFrame claimerAffiliation(String claimerAffiliation) {
    this.claimerAffiliation = claimerAffiliation;
    return this;
  }

  /**
   * Get claimerAffiliation
   * @return claimerAffiliation
  */
  @ApiModelProperty(value = "")


  public String getClaimerAffiliation() {
    return claimerAffiliation;
  }

  public void setClaimerAffiliation(String claimerAffiliation) {
    this.claimerAffiliation = claimerAffiliation;
  }


  @Override
  public boolean equals(java.lang.Object o) {
    if (this == o) {
      return true;
    }
    if (o == null || getClass() != o.getClass()) {
      return false;
    }
    ClaimFrame claimFrame = (ClaimFrame) o;
    return Objects.equals(this.claimId, claimFrame.claimId) &&
        Objects.equals(this.claimURI, claimFrame.claimURI) &&
        Objects.equals(this.description, claimFrame.description) &&
        Objects.equals(this.topic, claimFrame.topic) &&
        Objects.equals(this.subtopic, claimFrame.subtopic) &&
        Objects.equals(this.claimTemplate, claimFrame.claimTemplate) &&
        Objects.equals(this.queryId, claimFrame.queryId) &&
        Objects.equals(this.components, claimFrame.components) &&
        Objects.equals(this.claimer, claimFrame.claimer) &&
        Objects.equals(this.epistemic, claimFrame.epistemic) &&
        Objects.equals(this.sourceDoc, claimFrame.sourceDoc) &&
        Objects.equals(this.sourceDocTitle, claimFrame.sourceDocTitle) &&
        Objects.equals(this.xVariable, claimFrame.xVariable) &&
        Objects.equals(this.xVariableCompId, claimFrame.xVariableCompId) &&
        Objects.equals(this.claimerKE, claimFrame.claimerKE) &&
        Objects.equals(this.queryClaimId, claimFrame.queryClaimId) &&
        Objects.equals(this.ranking, claimFrame.ranking) &&
        Objects.equals(this.claimRelations, claimFrame.claimRelations) &&
        Objects.equals(this.dates, claimFrame.dates) &&
        Objects.equals(this.importance, claimFrame.importance) &&
        Objects.equals(this.locationName, claimFrame.locationName) &&
        Objects.equals(this.relations, claimFrame.relations) &&
        Objects.equals(this.claimerAffiliation, claimFrame.claimerAffiliation);
  }

  @Override
  public int hashCode() {
    return Objects.hash(claimId, claimURI, description, topic, subtopic, claimTemplate, queryId, components, claimer, epistemic, sourceDoc, sourceDocTitle, xVariable, xVariableCompId, claimerKE, queryClaimId, ranking, claimRelations, dates, importance, locationName, relations, claimerAffiliation);
  }

  @Override
  public String toString() {
    StringBuilder sb = new StringBuilder();
    sb.append("class ClaimFrame {\n");
    
    sb.append("    claimId: ").append(toIndentedString(claimId)).append("\n");
    sb.append("    claimURI: ").append(toIndentedString(claimURI)).append("\n");
    sb.append("    description: ").append(toIndentedString(description)).append("\n");
    sb.append("    topic: ").append(toIndentedString(topic)).append("\n");
    sb.append("    subtopic: ").append(toIndentedString(subtopic)).append("\n");
    sb.append("    claimTemplate: ").append(toIndentedString(claimTemplate)).append("\n");
    sb.append("    queryId: ").append(toIndentedString(queryId)).append("\n");
    sb.append("    components: ").append(toIndentedString(components)).append("\n");
    sb.append("    claimer: ").append(toIndentedString(claimer)).append("\n");
    sb.append("    epistemic: ").append(toIndentedString(epistemic)).append("\n");
    sb.append("    sourceDoc: ").append(toIndentedString(sourceDoc)).append("\n");
    sb.append("    sourceDocTitle: ").append(toIndentedString(sourceDocTitle)).append("\n");
    sb.append("    xVariable: ").append(toIndentedString(xVariable)).append("\n");
    sb.append("    xVariableCompId: ").append(toIndentedString(xVariableCompId)).append("\n");
    sb.append("    claimerKE: ").append(toIndentedString(claimerKE)).append("\n");
    sb.append("    queryClaimId: ").append(toIndentedString(queryClaimId)).append("\n");
    sb.append("    ranking: ").append(toIndentedString(ranking)).append("\n");
    sb.append("    claimRelations: ").append(toIndentedString(claimRelations)).append("\n");
    sb.append("    dates: ").append(toIndentedString(dates)).append("\n");
    sb.append("    importance: ").append(toIndentedString(importance)).append("\n");
    sb.append("    locationName: ").append(toIndentedString(locationName)).append("\n");
    sb.append("    relations: ").append(toIndentedString(relations)).append("\n");
    sb.append("    claimerAffiliation: ").append(toIndentedString(claimerAffiliation)).append("\n");
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

