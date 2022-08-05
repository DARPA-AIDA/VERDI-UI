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
 * ClaimFrameProvenanceObjectValues
 */
@javax.annotation.Generated(value = "org.openapitools.codegen.languages.SpringCodegen", date = "2022-08-05T15:31:00.866110500-04:00[America/New_York]")

public class ClaimFrameProvenanceObjectValues   {
  @JsonProperty("keId")
  private String keId;

  @JsonProperty("category")
  private String category;

  @JsonProperty("types")
  @Valid
  private List<String> types = null;

  public ClaimFrameProvenanceObjectValues keId(String keId) {
    this.keId = keId;
    return this;
  }

  /**
   * Get keId
   * @return keId
  */
  @ApiModelProperty(value = "")


  public String getKeId() {
    return keId;
  }

  public void setKeId(String keId) {
    this.keId = keId;
  }

  public ClaimFrameProvenanceObjectValues category(String category) {
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

  public ClaimFrameProvenanceObjectValues types(List<String> types) {
    this.types = types;
    return this;
  }

  public ClaimFrameProvenanceObjectValues addTypesItem(String typesItem) {
    if (this.types == null) {
      this.types = new ArrayList<>();
    }
    this.types.add(typesItem);
    return this;
  }

  /**
   * Get types
   * @return types
  */
  @ApiModelProperty(value = "")


  public List<String> getTypes() {
    return types;
  }

  public void setTypes(List<String> types) {
    this.types = types;
  }


  @Override
  public boolean equals(java.lang.Object o) {
    if (this == o) {
      return true;
    }
    if (o == null || getClass() != o.getClass()) {
      return false;
    }
    ClaimFrameProvenanceObjectValues claimFrameProvenanceObjectValues = (ClaimFrameProvenanceObjectValues) o;
    return Objects.equals(this.keId, claimFrameProvenanceObjectValues.keId) &&
        Objects.equals(this.category, claimFrameProvenanceObjectValues.category) &&
        Objects.equals(this.types, claimFrameProvenanceObjectValues.types);
  }

  @Override
  public int hashCode() {
    return Objects.hash(keId, category, types);
  }

  @Override
  public String toString() {
    StringBuilder sb = new StringBuilder();
    sb.append("class ClaimFrameProvenanceObjectValues {\n");
    
    sb.append("    keId: ").append(toIndentedString(keId)).append("\n");
    sb.append("    category: ").append(toIndentedString(category)).append("\n");
    sb.append("    types: ").append(toIndentedString(types)).append("\n");
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

