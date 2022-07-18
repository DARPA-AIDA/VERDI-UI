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
 * Hypothesis
 */
@javax.annotation.Generated(value = "org.openapitools.codegen.languages.SpringCodegen", date = "2022-01-05T11:12:00.154-05:00[America/New_York]")

public class Hypothesis   {
  @JsonProperty("hypothesis")
  private String hypothesis;

  @JsonProperty("entityCount")
  private Integer entityCount;

  @JsonProperty("eventCount")
  private Integer eventCount;

  @JsonProperty("relationCount")
  private Integer relationCount;

  public Hypothesis hypothesis(String hypothesis) {
    this.hypothesis = hypothesis;
    return this;
  }

  /**
   * Get hypothesis
   * @return hypothesis
  */
  @ApiModelProperty(value = "")


  public String getHypothesis() {
    return hypothesis;
  }

  public void setHypothesis(String hypothesis) {
    this.hypothesis = hypothesis;
  }

  public Hypothesis entityCount(Integer entityCount) {
    this.entityCount = entityCount;
    return this;
  }

  /**
   * Get entityCount
   * @return entityCount
  */
  @ApiModelProperty(value = "")


  public Integer getEntityCount() {
    return entityCount;
  }

  public void setEntityCount(Integer entityCount) {
    this.entityCount = entityCount;
  }

  public Hypothesis eventCount(Integer eventCount) {
    this.eventCount = eventCount;
    return this;
  }

  /**
   * Get eventCount
   * @return eventCount
  */
  @ApiModelProperty(value = "")


  public Integer getEventCount() {
    return eventCount;
  }

  public void setEventCount(Integer eventCount) {
    this.eventCount = eventCount;
  }

  public Hypothesis relationCount(Integer relationCount) {
    this.relationCount = relationCount;
    return this;
  }

  /**
   * Get relationCount
   * @return relationCount
  */
  @ApiModelProperty(value = "")


  public Integer getRelationCount() {
    return relationCount;
  }

  public void setRelationCount(Integer relationCount) {
    this.relationCount = relationCount;
  }


  @Override
  public boolean equals(java.lang.Object o) {
    if (this == o) {
      return true;
    }
    if (o == null || getClass() != o.getClass()) {
      return false;
    }
    Hypothesis hypothesis = (Hypothesis) o;
    return Objects.equals(this.hypothesis, hypothesis.hypothesis) &&
        Objects.equals(this.entityCount, hypothesis.entityCount) &&
        Objects.equals(this.eventCount, hypothesis.eventCount) &&
        Objects.equals(this.relationCount, hypothesis.relationCount);
  }

  @Override
  public int hashCode() {
    return Objects.hash(hypothesis, entityCount, eventCount, relationCount);
  }

  @Override
  public String toString() {
    StringBuilder sb = new StringBuilder();
    sb.append("class Hypothesis {\n");
    
    sb.append("    hypothesis: ").append(toIndentedString(hypothesis)).append("\n");
    sb.append("    entityCount: ").append(toIndentedString(entityCount)).append("\n");
    sb.append("    eventCount: ").append(toIndentedString(eventCount)).append("\n");
    sb.append("    relationCount: ").append(toIndentedString(relationCount)).append("\n");
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

