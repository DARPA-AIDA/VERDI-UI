package com.ncc.verdi.model;

import java.util.Objects;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonCreator;
import com.ncc.verdi.model.HypothesisFilterArgument;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import java.util.ArrayList;
import java.util.List;
import org.openapitools.jackson.nullable.JsonNullable;
import javax.validation.Valid;
import javax.validation.constraints.*;

/**
 * HypothesisFilterObjectFilters
 */
@javax.annotation.Generated(value = "org.openapitools.codegen.languages.SpringCodegen", date = "2022-01-05T11:12:00.154-05:00[America/New_York]")

public class HypothesisFilterObjectFilters   {
  @JsonProperty("eventType")
  private String eventType;

  @JsonProperty("arguments")
  @Valid
  private List<HypothesisFilterArgument> arguments = null;

  public HypothesisFilterObjectFilters eventType(String eventType) {
    this.eventType = eventType;
    return this;
  }

  /**
   * Get eventType
   * @return eventType
  */
  @ApiModelProperty(value = "")


  public String getEventType() {
    return eventType;
  }

  public void setEventType(String eventType) {
    this.eventType = eventType;
  }

  public HypothesisFilterObjectFilters arguments(List<HypothesisFilterArgument> arguments) {
    this.arguments = arguments;
    return this;
  }

  public HypothesisFilterObjectFilters addArgumentsItem(HypothesisFilterArgument argumentsItem) {
    if (this.arguments == null) {
      this.arguments = new ArrayList<>();
    }
    this.arguments.add(argumentsItem);
    return this;
  }

  /**
   * Get arguments
   * @return arguments
  */
  @ApiModelProperty(value = "")

  @Valid

  public List<HypothesisFilterArgument> getArguments() {
    return arguments;
  }

  public void setArguments(List<HypothesisFilterArgument> arguments) {
    this.arguments = arguments;
  }


  @Override
  public boolean equals(java.lang.Object o) {
    if (this == o) {
      return true;
    }
    if (o == null || getClass() != o.getClass()) {
      return false;
    }
    HypothesisFilterObjectFilters hypothesisFilterObjectFilters = (HypothesisFilterObjectFilters) o;
    return Objects.equals(this.eventType, hypothesisFilterObjectFilters.eventType) &&
        Objects.equals(this.arguments, hypothesisFilterObjectFilters.arguments);
  }

  @Override
  public int hashCode() {
    return Objects.hash(eventType, arguments);
  }

  @Override
  public String toString() {
    StringBuilder sb = new StringBuilder();
    sb.append("class HypothesisFilterObjectFilters {\n");
    
    sb.append("    eventType: ").append(toIndentedString(eventType)).append("\n");
    sb.append("    arguments: ").append(toIndentedString(arguments)).append("\n");
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

