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
 * EntityAllOf
 */
@javax.annotation.Generated(value = "org.openapitools.codegen.languages.SpringCodegen", date = "2022-01-05T11:12:00.154-05:00[America/New_York]")

public class EntityAllOf   {
  @JsonProperty("names")
  @Valid
  private List<String> names = null;

  @JsonProperty("category")
  private String category;

  @JsonProperty("clusterIds")
  @Valid
  private List<String> clusterIds = null;

  public EntityAllOf names(List<String> names) {
    this.names = names;
    return this;
  }

  public EntityAllOf addNamesItem(String namesItem) {
    if (this.names == null) {
      this.names = new ArrayList<>();
    }
    this.names.add(namesItem);
    return this;
  }

  /**
   * Get names
   * @return names
  */
  @ApiModelProperty(value = "")


  public List<String> getNames() {
    return names;
  }

  public void setNames(List<String> names) {
    this.names = names;
  }

  public EntityAllOf category(String category) {
    this.category = category;
    return this;
  }

  /**
   * Get category
   * @return category
  */
  @ApiModelProperty(value = "")


  public String getCategory() {
    return category;
  }

  public void setCategory(String category) {
    this.category = category;
  }

  public EntityAllOf clusterIds(List<String> clusterIds) {
    this.clusterIds = clusterIds;
    return this;
  }

  public EntityAllOf addClusterIdsItem(String clusterIdsItem) {
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


  @Override
  public boolean equals(java.lang.Object o) {
    if (this == o) {
      return true;
    }
    if (o == null || getClass() != o.getClass()) {
      return false;
    }
    EntityAllOf entityAllOf = (EntityAllOf) o;
    return Objects.equals(this.names, entityAllOf.names) &&
        Objects.equals(this.category, entityAllOf.category) &&
        Objects.equals(this.clusterIds, entityAllOf.clusterIds);
  }

  @Override
  public int hashCode() {
    return Objects.hash(names, category, clusterIds);
  }

  @Override
  public String toString() {
    StringBuilder sb = new StringBuilder();
    sb.append("class EntityAllOf {\n");
    
    sb.append("    names: ").append(toIndentedString(names)).append("\n");
    sb.append("    category: ").append(toIndentedString(category)).append("\n");
    sb.append("    clusterIds: ").append(toIndentedString(clusterIds)).append("\n");
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

