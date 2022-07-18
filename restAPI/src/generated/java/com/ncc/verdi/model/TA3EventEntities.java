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
 * TA3EventEntities
 */
@javax.annotation.Generated(value = "org.openapitools.codegen.languages.SpringCodegen", date = "2022-01-05T11:12:00.154-05:00[America/New_York]")

public class TA3EventEntities   {
  @JsonProperty("headline")
  private String headline;

  @JsonProperty("names")
  @Valid
  private List<String> names = null;

  @JsonProperty("role_uri")
  @Valid
  private List<String> roleUri = null;

  @JsonProperty("role")
  @Valid
  private List<String> role = null;

  @JsonProperty("id")
  private String id;

  @JsonProperty("prototype_uri")
  private String prototypeUri;

  @JsonProperty("resolved_type")
  private String resolvedType;

  @JsonProperty("types")
  @Valid
  private List<String> types = null;

  public TA3EventEntities headline(String headline) {
    this.headline = headline;
    return this;
  }

  /**
   * Get headline
   * @return headline
  */
  @ApiModelProperty(value = "")


  public String getHeadline() {
    return headline;
  }

  public void setHeadline(String headline) {
    this.headline = headline;
  }

  public TA3EventEntities names(List<String> names) {
    this.names = names;
    return this;
  }

  public TA3EventEntities addNamesItem(String namesItem) {
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

  public TA3EventEntities roleUri(List<String> roleUri) {
    this.roleUri = roleUri;
    return this;
  }

  public TA3EventEntities addRoleUriItem(String roleUriItem) {
    if (this.roleUri == null) {
      this.roleUri = new ArrayList<>();
    }
    this.roleUri.add(roleUriItem);
    return this;
  }

  /**
   * Get roleUri
   * @return roleUri
  */
  @ApiModelProperty(value = "")


  public List<String> getRoleUri() {
    return roleUri;
  }

  public void setRoleUri(List<String> roleUri) {
    this.roleUri = roleUri;
  }

  public TA3EventEntities role(List<String> role) {
    this.role = role;
    return this;
  }

  public TA3EventEntities addRoleItem(String roleItem) {
    if (this.role == null) {
      this.role = new ArrayList<>();
    }
    this.role.add(roleItem);
    return this;
  }

  /**
   * Get role
   * @return role
  */
  @ApiModelProperty(value = "")


  public List<String> getRole() {
    return role;
  }

  public void setRole(List<String> role) {
    this.role = role;
  }

  public TA3EventEntities id(String id) {
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

  public TA3EventEntities prototypeUri(String prototypeUri) {
    this.prototypeUri = prototypeUri;
    return this;
  }

  /**
   * Get prototypeUri
   * @return prototypeUri
  */
  @ApiModelProperty(value = "")


  public String getPrototypeUri() {
    return prototypeUri;
  }

  public void setPrototypeUri(String prototypeUri) {
    this.prototypeUri = prototypeUri;
  }

  public TA3EventEntities resolvedType(String resolvedType) {
    this.resolvedType = resolvedType;
    return this;
  }

  /**
   * Get resolvedType
   * @return resolvedType
  */
  @ApiModelProperty(value = "")


  public String getResolvedType() {
    return resolvedType;
  }

  public void setResolvedType(String resolvedType) {
    this.resolvedType = resolvedType;
  }

  public TA3EventEntities types(List<String> types) {
    this.types = types;
    return this;
  }

  public TA3EventEntities addTypesItem(String typesItem) {
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
    TA3EventEntities ta3EventEntities = (TA3EventEntities) o;
    return Objects.equals(this.headline, ta3EventEntities.headline) &&
        Objects.equals(this.names, ta3EventEntities.names) &&
        Objects.equals(this.roleUri, ta3EventEntities.roleUri) &&
        Objects.equals(this.role, ta3EventEntities.role) &&
        Objects.equals(this.id, ta3EventEntities.id) &&
        Objects.equals(this.prototypeUri, ta3EventEntities.prototypeUri) &&
        Objects.equals(this.resolvedType, ta3EventEntities.resolvedType) &&
        Objects.equals(this.types, ta3EventEntities.types);
  }

  @Override
  public int hashCode() {
    return Objects.hash(headline, names, roleUri, role, id, prototypeUri, resolvedType, types);
  }

  @Override
  public String toString() {
    StringBuilder sb = new StringBuilder();
    sb.append("class TA3EventEntities {\n");
    
    sb.append("    headline: ").append(toIndentedString(headline)).append("\n");
    sb.append("    names: ").append(toIndentedString(names)).append("\n");
    sb.append("    roleUri: ").append(toIndentedString(roleUri)).append("\n");
    sb.append("    role: ").append(toIndentedString(role)).append("\n");
    sb.append("    id: ").append(toIndentedString(id)).append("\n");
    sb.append("    prototypeUri: ").append(toIndentedString(prototypeUri)).append("\n");
    sb.append("    resolvedType: ").append(toIndentedString(resolvedType)).append("\n");
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

