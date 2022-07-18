package com.ncc.verdi.model;

import java.util.Objects;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonCreator;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import java.util.ArrayList;
import java.util.List;
import org.openapitools.jackson.nullable.JsonNullable;
import javax.validation.Valid;
import javax.validation.constraints.*;

/**
 * ClaimFrameFilterObject
 */
@javax.annotation.Generated(value = "org.openapitools.codegen.languages.SpringCodegen", date = "2022-01-05T11:12:00.154-05:00[America/New_York]")

public class ClaimFrameFilterObject   {
  @JsonProperty("graph")
  private String graph;

  @JsonProperty("topics")
  @Valid
  private List<String> topics = null;

  @JsonProperty("subtopics")
  @Valid
  private List<String> subtopics = null;

  public ClaimFrameFilterObject graph(String graph) {
    this.graph = graph;
    return this;
  }

  /**
   * Get graph
   * @return graph
  */
  @ApiModelProperty(value = "")


  public String getGraph() {
    return graph;
  }

  public void setGraph(String graph) {
    this.graph = graph;
  }

  public ClaimFrameFilterObject topics(List<String> topics) {
    this.topics = topics;
    return this;
  }

  public ClaimFrameFilterObject addTopicsItem(String topicsItem) {
    if (this.topics == null) {
      this.topics = new ArrayList<>();
    }
    this.topics.add(topicsItem);
    return this;
  }

  /**
   * Get topics
   * @return topics
  */
  @ApiModelProperty(value = "")


  public List<String> getTopics() {
    return topics;
  }

  public void setTopics(List<String> topics) {
    this.topics = topics;
  }

  public ClaimFrameFilterObject subtopics(List<String> subtopics) {
    this.subtopics = subtopics;
    return this;
  }

  public ClaimFrameFilterObject addSubtopicsItem(String subtopicsItem) {
    if (this.subtopics == null) {
      this.subtopics = new ArrayList<>();
    }
    this.subtopics.add(subtopicsItem);
    return this;
  }

  /**
   * Get subtopics
   * @return subtopics
  */
  @ApiModelProperty(value = "")


  public List<String> getSubtopics() {
    return subtopics;
  }

  public void setSubtopics(List<String> subtopics) {
    this.subtopics = subtopics;
  }


  @Override
  public boolean equals(java.lang.Object o) {
    if (this == o) {
      return true;
    }
    if (o == null || getClass() != o.getClass()) {
      return false;
    }
    ClaimFrameFilterObject claimFrameFilterObject = (ClaimFrameFilterObject) o;
    return Objects.equals(this.graph, claimFrameFilterObject.graph) &&
        Objects.equals(this.topics, claimFrameFilterObject.topics) &&
        Objects.equals(this.subtopics, claimFrameFilterObject.subtopics);
  }

  @Override
  public int hashCode() {
    return Objects.hash(graph, topics, subtopics);
  }

  @Override
  public String toString() {
    StringBuilder sb = new StringBuilder();
    sb.append("class ClaimFrameFilterObject {\n");
    
    sb.append("    graph: ").append(toIndentedString(graph)).append("\n");
    sb.append("    topics: ").append(toIndentedString(topics)).append("\n");
    sb.append("    subtopics: ").append(toIndentedString(subtopics)).append("\n");
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

