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
@javax.annotation.Generated(value = "org.openapitools.codegen.languages.SpringCodegen", date = "2022-08-05T15:31:00.866110500-04:00[America/New_York]")

public class ClaimFrameFilterObject   {
  @JsonProperty("graph")
  private String graph;

  @JsonProperty("baseGraphs")
  @Valid
  private List<String> baseGraphs = null;

  @JsonProperty("topics")
  @Valid
  private List<String> topics = null;

  @JsonProperty("subtopics")
  @Valid
  private List<String> subtopics = null;

  @JsonProperty("queryClaimIds")
  @Valid
  private List<String> queryClaimIds = null;

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

  public ClaimFrameFilterObject baseGraphs(List<String> baseGraphs) {
    this.baseGraphs = baseGraphs;
    return this;
  }

  public ClaimFrameFilterObject addBaseGraphsItem(String baseGraphsItem) {
    if (this.baseGraphs == null) {
      this.baseGraphs = new ArrayList<>();
    }
    this.baseGraphs.add(baseGraphsItem);
    return this;
  }

  /**
   * Get baseGraphs
   * @return baseGraphs
  */
  @ApiModelProperty(value = "")


  public List<String> getBaseGraphs() {
    return baseGraphs;
  }

  public void setBaseGraphs(List<String> baseGraphs) {
    this.baseGraphs = baseGraphs;
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

  public ClaimFrameFilterObject queryClaimIds(List<String> queryClaimIds) {
    this.queryClaimIds = queryClaimIds;
    return this;
  }

  public ClaimFrameFilterObject addQueryClaimIdsItem(String queryClaimIdsItem) {
    if (this.queryClaimIds == null) {
      this.queryClaimIds = new ArrayList<>();
    }
    this.queryClaimIds.add(queryClaimIdsItem);
    return this;
  }

  /**
   * Get queryClaimIds
   * @return queryClaimIds
  */
  @ApiModelProperty(value = "")


  public List<String> getQueryClaimIds() {
    return queryClaimIds;
  }

  public void setQueryClaimIds(List<String> queryClaimIds) {
    this.queryClaimIds = queryClaimIds;
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
        Objects.equals(this.baseGraphs, claimFrameFilterObject.baseGraphs) &&
        Objects.equals(this.topics, claimFrameFilterObject.topics) &&
        Objects.equals(this.subtopics, claimFrameFilterObject.subtopics) &&
        Objects.equals(this.queryClaimIds, claimFrameFilterObject.queryClaimIds);
  }

  @Override
  public int hashCode() {
    return Objects.hash(graph, baseGraphs, topics, subtopics, queryClaimIds);
  }

  @Override
  public String toString() {
    StringBuilder sb = new StringBuilder();
    sb.append("class ClaimFrameFilterObject {\n");
    
    sb.append("    graph: ").append(toIndentedString(graph)).append("\n");
    sb.append("    baseGraphs: ").append(toIndentedString(baseGraphs)).append("\n");
    sb.append("    topics: ").append(toIndentedString(topics)).append("\n");
    sb.append("    subtopics: ").append(toIndentedString(subtopics)).append("\n");
    sb.append("    queryClaimIds: ").append(toIndentedString(queryClaimIds)).append("\n");
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

