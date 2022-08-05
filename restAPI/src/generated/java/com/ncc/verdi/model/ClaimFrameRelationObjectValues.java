package com.ncc.verdi.model;

import java.util.Objects;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonCreator;
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
 * ClaimFrameRelationObjectValues
 */
@javax.annotation.Generated(value = "org.openapitools.codegen.languages.SpringCodegen", date = "2022-08-05T15:31:00.866110500-04:00[America/New_York]")

public class ClaimFrameRelationObjectValues   {
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

  @JsonProperty("xVariable")
  private String xVariable;

  @JsonProperty("claimLocation")
  private String claimLocation;

  @JsonProperty("importance")
  private BigDecimal importance;

  @JsonProperty("dates")
  @Valid
  private List<LDCTime> dates = null;

  public ClaimFrameRelationObjectValues claimId(String claimId) {
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

  public ClaimFrameRelationObjectValues claimURI(String claimURI) {
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

  public ClaimFrameRelationObjectValues description(String description) {
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

  public ClaimFrameRelationObjectValues topic(String topic) {
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

  public ClaimFrameRelationObjectValues subtopic(String subtopic) {
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

  public ClaimFrameRelationObjectValues claimTemplate(String claimTemplate) {
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

  public ClaimFrameRelationObjectValues xVariable(String xVariable) {
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

  public ClaimFrameRelationObjectValues claimLocation(String claimLocation) {
    this.claimLocation = claimLocation;
    return this;
  }

  /**
   * Get claimLocation
   * @return claimLocation
  */
  @ApiModelProperty(value = "")


  public String getClaimLocation() {
    return claimLocation;
  }

  public void setClaimLocation(String claimLocation) {
    this.claimLocation = claimLocation;
  }

  public ClaimFrameRelationObjectValues importance(BigDecimal importance) {
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

  public ClaimFrameRelationObjectValues dates(List<LDCTime> dates) {
    this.dates = dates;
    return this;
  }

  public ClaimFrameRelationObjectValues addDatesItem(LDCTime datesItem) {
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


  @Override
  public boolean equals(java.lang.Object o) {
    if (this == o) {
      return true;
    }
    if (o == null || getClass() != o.getClass()) {
      return false;
    }
    ClaimFrameRelationObjectValues claimFrameRelationObjectValues = (ClaimFrameRelationObjectValues) o;
    return Objects.equals(this.claimId, claimFrameRelationObjectValues.claimId) &&
        Objects.equals(this.claimURI, claimFrameRelationObjectValues.claimURI) &&
        Objects.equals(this.description, claimFrameRelationObjectValues.description) &&
        Objects.equals(this.topic, claimFrameRelationObjectValues.topic) &&
        Objects.equals(this.subtopic, claimFrameRelationObjectValues.subtopic) &&
        Objects.equals(this.claimTemplate, claimFrameRelationObjectValues.claimTemplate) &&
        Objects.equals(this.xVariable, claimFrameRelationObjectValues.xVariable) &&
        Objects.equals(this.claimLocation, claimFrameRelationObjectValues.claimLocation) &&
        Objects.equals(this.importance, claimFrameRelationObjectValues.importance) &&
        Objects.equals(this.dates, claimFrameRelationObjectValues.dates);
  }

  @Override
  public int hashCode() {
    return Objects.hash(claimId, claimURI, description, topic, subtopic, claimTemplate, xVariable, claimLocation, importance, dates);
  }

  @Override
  public String toString() {
    StringBuilder sb = new StringBuilder();
    sb.append("class ClaimFrameRelationObjectValues {\n");
    
    sb.append("    claimId: ").append(toIndentedString(claimId)).append("\n");
    sb.append("    claimURI: ").append(toIndentedString(claimURI)).append("\n");
    sb.append("    description: ").append(toIndentedString(description)).append("\n");
    sb.append("    topic: ").append(toIndentedString(topic)).append("\n");
    sb.append("    subtopic: ").append(toIndentedString(subtopic)).append("\n");
    sb.append("    claimTemplate: ").append(toIndentedString(claimTemplate)).append("\n");
    sb.append("    xVariable: ").append(toIndentedString(xVariable)).append("\n");
    sb.append("    claimLocation: ").append(toIndentedString(claimLocation)).append("\n");
    sb.append("    importance: ").append(toIndentedString(importance)).append("\n");
    sb.append("    dates: ").append(toIndentedString(dates)).append("\n");
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

