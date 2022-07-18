package com.ncc.verdi.model;

import java.util.Objects;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonCreator;
import com.ncc.verdi.model.CompareHypothesisDetailArguments;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import java.util.ArrayList;
import java.util.List;
import org.openapitools.jackson.nullable.JsonNullable;
import javax.validation.Valid;
import javax.validation.constraints.*;

/**
 * CompareHypothesisDetailRoles
 */
@javax.annotation.Generated(value = "org.openapitools.codegen.languages.SpringCodegen", date = "2022-01-05T11:12:00.154-05:00[America/New_York]")

public class CompareHypothesisDetailRoles   {
  @JsonProperty("role")
  private String role;

  @JsonProperty("arguments")
  @Valid
  private List<CompareHypothesisDetailArguments> arguments = null;

  public CompareHypothesisDetailRoles role(String role) {
    this.role = role;
    return this;
  }

  /**
   * Get role
   * @return role
  */
  @ApiModelProperty(value = "")


  public String getRole() {
    return role;
  }

  public void setRole(String role) {
    this.role = role;
  }

  public CompareHypothesisDetailRoles arguments(List<CompareHypothesisDetailArguments> arguments) {
    this.arguments = arguments;
    return this;
  }

  public CompareHypothesisDetailRoles addArgumentsItem(CompareHypothesisDetailArguments argumentsItem) {
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

  public List<CompareHypothesisDetailArguments> getArguments() {
    return arguments;
  }

  public void setArguments(List<CompareHypothesisDetailArguments> arguments) {
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
    CompareHypothesisDetailRoles compareHypothesisDetailRoles = (CompareHypothesisDetailRoles) o;
    return Objects.equals(this.role, compareHypothesisDetailRoles.role) &&
        Objects.equals(this.arguments, compareHypothesisDetailRoles.arguments);
  }

  @Override
  public int hashCode() {
    return Objects.hash(role, arguments);
  }

  @Override
  public String toString() {
    StringBuilder sb = new StringBuilder();
    sb.append("class CompareHypothesisDetailRoles {\n");
    
    sb.append("    role: ").append(toIndentedString(role)).append("\n");
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

