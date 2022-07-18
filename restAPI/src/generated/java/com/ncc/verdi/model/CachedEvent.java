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
 * CachedEvent
 */
@javax.annotation.Generated(value = "org.openapitools.codegen.languages.SpringCodegen", date = "2022-01-05T11:12:00.154-05:00[America/New_York]")

public class CachedEvent   {
  @JsonProperty("cluster")
  private String cluster;

  @JsonProperty("handle")
  private String handle;

  @JsonProperty("prototype")
  private String prototype;

  @JsonProperty("types")
  @Valid
  private List<String> types = null;

  @JsonProperty("names")
  @Valid
  private List<String> names = null;

  @JsonProperty("argumentCount")
  private Integer argumentCount;

  public CachedEvent cluster(String cluster) {
    this.cluster = cluster;
    return this;
  }

  /**
   * Get cluster
   * @return cluster
  */
  @ApiModelProperty(value = "")


  public String getCluster() {
    return cluster;
  }

  public void setCluster(String cluster) {
    this.cluster = cluster;
  }

  public CachedEvent handle(String handle) {
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

  public CachedEvent prototype(String prototype) {
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

  public CachedEvent types(List<String> types) {
    this.types = types;
    return this;
  }

  public CachedEvent addTypesItem(String typesItem) {
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

  public CachedEvent names(List<String> names) {
    this.names = names;
    return this;
  }

  public CachedEvent addNamesItem(String namesItem) {
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

  public CachedEvent argumentCount(Integer argumentCount) {
    this.argumentCount = argumentCount;
    return this;
  }

  /**
   * Get argumentCount
   * @return argumentCount
  */
  @ApiModelProperty(value = "")


  public Integer getArgumentCount() {
    return argumentCount;
  }

  public void setArgumentCount(Integer argumentCount) {
    this.argumentCount = argumentCount;
  }


  @Override
  public boolean equals(java.lang.Object o) {
    if (this == o) {
      return true;
    }
    if (o == null || getClass() != o.getClass()) {
      return false;
    }
    CachedEvent cachedEvent = (CachedEvent) o;
    return Objects.equals(this.cluster, cachedEvent.cluster) &&
        Objects.equals(this.handle, cachedEvent.handle) &&
        Objects.equals(this.prototype, cachedEvent.prototype) &&
        Objects.equals(this.types, cachedEvent.types) &&
        Objects.equals(this.names, cachedEvent.names) &&
        Objects.equals(this.argumentCount, cachedEvent.argumentCount);
  }

  @Override
  public int hashCode() {
    return Objects.hash(cluster, handle, prototype, types, names, argumentCount);
  }

  @Override
  public String toString() {
    StringBuilder sb = new StringBuilder();
    sb.append("class CachedEvent {\n");
    
    sb.append("    cluster: ").append(toIndentedString(cluster)).append("\n");
    sb.append("    handle: ").append(toIndentedString(handle)).append("\n");
    sb.append("    prototype: ").append(toIndentedString(prototype)).append("\n");
    sb.append("    types: ").append(toIndentedString(types)).append("\n");
    sb.append("    names: ").append(toIndentedString(names)).append("\n");
    sb.append("    argumentCount: ").append(toIndentedString(argumentCount)).append("\n");
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

