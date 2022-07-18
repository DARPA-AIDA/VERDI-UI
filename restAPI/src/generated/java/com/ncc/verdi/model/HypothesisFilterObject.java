package com.ncc.verdi.model;

import java.util.Objects;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonCreator;
import com.ncc.verdi.model.HypothesisFilterObjectFilters;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import java.util.ArrayList;
import java.util.List;
import org.openapitools.jackson.nullable.JsonNullable;
import javax.validation.Valid;
import javax.validation.constraints.*;

/**
 * HypothesisFilterObject
 */
@javax.annotation.Generated(value = "org.openapitools.codegen.languages.SpringCodegen", date = "2022-01-05T11:12:00.154-05:00[America/New_York]")

public class HypothesisFilterObject   {
  @JsonProperty("graph")
  private String graph;

  @JsonProperty("filters")
  @Valid
  private List<HypothesisFilterObjectFilters> filters = null;

  public HypothesisFilterObject graph(String graph) {
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

  public HypothesisFilterObject filters(List<HypothesisFilterObjectFilters> filters) {
    this.filters = filters;
    return this;
  }

  public HypothesisFilterObject addFiltersItem(HypothesisFilterObjectFilters filtersItem) {
    if (this.filters == null) {
      this.filters = new ArrayList<>();
    }
    this.filters.add(filtersItem);
    return this;
  }

  /**
   * Get filters
   * @return filters
  */
  @ApiModelProperty(value = "")

  @Valid

  public List<HypothesisFilterObjectFilters> getFilters() {
    return filters;
  }

  public void setFilters(List<HypothesisFilterObjectFilters> filters) {
    this.filters = filters;
  }


  @Override
  public boolean equals(java.lang.Object o) {
    if (this == o) {
      return true;
    }
    if (o == null || getClass() != o.getClass()) {
      return false;
    }
    HypothesisFilterObject hypothesisFilterObject = (HypothesisFilterObject) o;
    return Objects.equals(this.graph, hypothesisFilterObject.graph) &&
        Objects.equals(this.filters, hypothesisFilterObject.filters);
  }

  @Override
  public int hashCode() {
    return Objects.hash(graph, filters);
  }

  @Override
  public String toString() {
    StringBuilder sb = new StringBuilder();
    sb.append("class HypothesisFilterObject {\n");
    
    sb.append("    graph: ").append(toIndentedString(graph)).append("\n");
    sb.append("    filters: ").append(toIndentedString(filters)).append("\n");
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

