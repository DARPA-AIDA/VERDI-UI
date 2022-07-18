package com.ncc.verdi.model;

import java.util.Objects;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonCreator;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import org.openapitools.jackson.nullable.JsonNullable;
import javax.validation.Valid;
import javax.validation.constraints.*;

/**
 * ClaimFrameRelationObjectValues
 */
@javax.annotation.Generated(value = "org.openapitools.codegen.languages.SpringCodegen", date = "2022-01-05T11:12:00.154-05:00[America/New_York]")

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
        Objects.equals(this.claimTemplate, claimFrameRelationObjectValues.claimTemplate);
  }

  @Override
  public int hashCode() {
    return Objects.hash(claimId, claimURI, description, topic, subtopic, claimTemplate);
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

