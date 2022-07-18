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
 * CompareHypothesisDetailArguments
 */
@javax.annotation.Generated(value = "org.openapitools.codegen.languages.SpringCodegen", date = "2022-01-05T11:12:00.154-05:00[America/New_York]")

public class CompareHypothesisDetailArguments   {
  @JsonProperty("id")
  private String id;

  @JsonProperty("prototype")
  private String prototype;

  @JsonProperty("names")
  @Valid
  private List<String> names = null;

  @JsonProperty("handle")
  private String handle;

  @JsonProperty("types")
  @Valid
  private List<String> types = null;

  @JsonProperty("category")
  private String category;

  public CompareHypothesisDetailArguments id(String id) {
    this.id = id;
    return this;
  }

  /**
   * Get id
   * @return id
  */
  @ApiModelProperty(value = "")


  public String getId() {
    return id;
  }

  public void setId(String id) {
    this.id = id;
  }

  public CompareHypothesisDetailArguments prototype(String prototype) {
    this.prototype = prototype;
    return this;
  }

  /**
   * Get prototype
   * @return prototype
  */
  @ApiModelProperty(value = "")


  public String getPrototype() {
    return prototype;
  }

  public void setPrototype(String prototype) {
    this.prototype = prototype;
  }

  public CompareHypothesisDetailArguments names(List<String> names) {
    this.names = names;
    return this;
  }

  public CompareHypothesisDetailArguments addNamesItem(String namesItem) {
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

  public CompareHypothesisDetailArguments handle(String handle) {
    this.handle = handle;
    return this;
  }

  /**
   * Get handle
   * @return handle
  */
  @ApiModelProperty(value = "")


  public String getHandle() {
    return handle;
  }

  public void setHandle(String handle) {
    this.handle = handle;
  }

  public CompareHypothesisDetailArguments types(List<String> types) {
    this.types = types;
    return this;
  }

  public CompareHypothesisDetailArguments addTypesItem(String typesItem) {
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

  public CompareHypothesisDetailArguments category(String category) {
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


  @Override
  public boolean equals(java.lang.Object o) {
    if (this == o) {
      return true;
    }
    if (o == null || getClass() != o.getClass()) {
      return false;
    }
    CompareHypothesisDetailArguments compareHypothesisDetailArguments = (CompareHypothesisDetailArguments) o;
    return Objects.equals(this.id, compareHypothesisDetailArguments.id) &&
        Objects.equals(this.prototype, compareHypothesisDetailArguments.prototype) &&
        Objects.equals(this.names, compareHypothesisDetailArguments.names) &&
        Objects.equals(this.handle, compareHypothesisDetailArguments.handle) &&
        Objects.equals(this.types, compareHypothesisDetailArguments.types) &&
        Objects.equals(this.category, compareHypothesisDetailArguments.category);
  }

  @Override
  public int hashCode() {
    return Objects.hash(id, prototype, names, handle, types, category);
  }

  @Override
  public String toString() {
    StringBuilder sb = new StringBuilder();
    sb.append("class CompareHypothesisDetailArguments {\n");
    
    sb.append("    id: ").append(toIndentedString(id)).append("\n");
    sb.append("    prototype: ").append(toIndentedString(prototype)).append("\n");
    sb.append("    names: ").append(toIndentedString(names)).append("\n");
    sb.append("    handle: ").append(toIndentedString(handle)).append("\n");
    sb.append("    types: ").append(toIndentedString(types)).append("\n");
    sb.append("    category: ").append(toIndentedString(category)).append("\n");
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

