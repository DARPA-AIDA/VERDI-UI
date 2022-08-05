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
 * ClaimFrameTopic
 */
@javax.annotation.Generated(value = "org.openapitools.codegen.languages.SpringCodegen", date = "2022-08-05T15:31:00.866110500-04:00[America/New_York]")

public class ClaimFrameTopic   {
  @JsonProperty("baseGraph")
  private String baseGraph;

  @JsonProperty("topic")
  private String topic;

  @JsonProperty("subtopic")
  private String subtopic;

  @JsonProperty("template")
  private String template;

  @JsonProperty("queryClaimId")
  private String queryClaimId;

  @JsonProperty("description")
  private String description;

  public ClaimFrameTopic baseGraph(String baseGraph) {
    this.baseGraph = baseGraph;
    return this;
  }

  /**
   * Get baseGraph
   * @return baseGraph
  */
  @ApiModelProperty(value = "")


  public String getBaseGraph() {
    return baseGraph;
  }

  public void setBaseGraph(String baseGraph) {
    this.baseGraph = baseGraph;
  }

  public ClaimFrameTopic topic(String topic) {
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

  public ClaimFrameTopic subtopic(String subtopic) {
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

  public ClaimFrameTopic template(String template) {
    this.template = template;
    return this;
  }

  /**
   * Get template
   * @return template
  */
  @ApiModelProperty(value = "")


  public String getTemplate() {
    return template;
  }

  public void setTemplate(String template) {
    this.template = template;
  }

  public ClaimFrameTopic queryClaimId(String queryClaimId) {
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

  public ClaimFrameTopic description(String description) {
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


  @Override
  public boolean equals(java.lang.Object o) {
    if (this == o) {
      return true;
    }
    if (o == null || getClass() != o.getClass()) {
      return false;
    }
    ClaimFrameTopic claimFrameTopic = (ClaimFrameTopic) o;
    return Objects.equals(this.baseGraph, claimFrameTopic.baseGraph) &&
        Objects.equals(this.topic, claimFrameTopic.topic) &&
        Objects.equals(this.subtopic, claimFrameTopic.subtopic) &&
        Objects.equals(this.template, claimFrameTopic.template) &&
        Objects.equals(this.queryClaimId, claimFrameTopic.queryClaimId) &&
        Objects.equals(this.description, claimFrameTopic.description);
  }

  @Override
  public int hashCode() {
    return Objects.hash(baseGraph, topic, subtopic, template, queryClaimId, description);
  }

  @Override
  public String toString() {
    StringBuilder sb = new StringBuilder();
    sb.append("class ClaimFrameTopic {\n");
    
    sb.append("    baseGraph: ").append(toIndentedString(baseGraph)).append("\n");
    sb.append("    topic: ").append(toIndentedString(topic)).append("\n");
    sb.append("    subtopic: ").append(toIndentedString(subtopic)).append("\n");
    sb.append("    template: ").append(toIndentedString(template)).append("\n");
    sb.append("    queryClaimId: ").append(toIndentedString(queryClaimId)).append("\n");
    sb.append("    description: ").append(toIndentedString(description)).append("\n");
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

