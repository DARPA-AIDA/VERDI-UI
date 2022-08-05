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
 * EventTypesFilterObject
 */
@javax.annotation.Generated(value = "org.openapitools.codegen.languages.SpringCodegen", date = "2022-08-05T15:31:00.866110500-04:00[America/New_York]")

public class EventTypesFilterObject   {
  @JsonProperty("clusterIds")
  @Valid
  private List<String> clusterIds = null;

  @JsonProperty("roleTypes")
  @Valid
  private List<String> roleTypes = null;

  public EventTypesFilterObject clusterIds(List<String> clusterIds) {
    this.clusterIds = clusterIds;
    return this;
  }

  public EventTypesFilterObject addClusterIdsItem(String clusterIdsItem) {
    if (this.clusterIds == null) {
      this.clusterIds = new ArrayList<>();
    }
    this.clusterIds.add(clusterIdsItem);
    return this;
  }

  /**
   * Get clusterIds
   * @return clusterIds
  */
  @ApiModelProperty(value = "")


  public List<String> getClusterIds() {
    return clusterIds;
  }

  public void setClusterIds(List<String> clusterIds) {
    this.clusterIds = clusterIds;
  }

  public EventTypesFilterObject roleTypes(List<String> roleTypes) {
    this.roleTypes = roleTypes;
    return this;
  }

  public EventTypesFilterObject addRoleTypesItem(String roleTypesItem) {
    if (this.roleTypes == null) {
      this.roleTypes = new ArrayList<>();
    }
    this.roleTypes.add(roleTypesItem);
    return this;
  }

  /**
   * Get roleTypes
   * @return roleTypes
  */
  @ApiModelProperty(value = "")


  public List<String> getRoleTypes() {
    return roleTypes;
  }

  public void setRoleTypes(List<String> roleTypes) {
    this.roleTypes = roleTypes;
  }


  @Override
  public boolean equals(java.lang.Object o) {
    if (this == o) {
      return true;
    }
    if (o == null || getClass() != o.getClass()) {
      return false;
    }
    EventTypesFilterObject eventTypesFilterObject = (EventTypesFilterObject) o;
    return Objects.equals(this.clusterIds, eventTypesFilterObject.clusterIds) &&
        Objects.equals(this.roleTypes, eventTypesFilterObject.roleTypes);
  }

  @Override
  public int hashCode() {
    return Objects.hash(clusterIds, roleTypes);
  }

  @Override
  public String toString() {
    StringBuilder sb = new StringBuilder();
    sb.append("class EventTypesFilterObject {\n");
    
    sb.append("    clusterIds: ").append(toIndentedString(clusterIds)).append("\n");
    sb.append("    roleTypes: ").append(toIndentedString(roleTypes)).append("\n");
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

