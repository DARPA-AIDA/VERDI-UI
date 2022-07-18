package com.ncc.verdi.model;

import java.util.Objects;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonCreator;
import com.ncc.verdi.model.CompareHypothesisDetailRoles;
import com.ncc.verdi.model.LDCTime;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import java.util.ArrayList;
import java.util.List;
import org.openapitools.jackson.nullable.JsonNullable;
import javax.validation.Valid;
import javax.validation.constraints.*;

/**
 * CompareHypothesisDetailMembers
 */
@javax.annotation.Generated(value = "org.openapitools.codegen.languages.SpringCodegen", date = "2022-01-05T11:12:00.154-05:00[America/New_York]")

public class CompareHypothesisDetailMembers   {
  @JsonProperty("node")
  private String node;

  @JsonProperty("prototype")
  private String prototype;

  @JsonProperty("category")
  private String category;

  @JsonProperty("type")
  private String type;

  @JsonProperty("dates")
  @Valid
  private List<LDCTime> dates = null;

  @JsonProperty("roles")
  @Valid
  private List<CompareHypothesisDetailRoles> roles = null;

  public CompareHypothesisDetailMembers node(String node) {
    this.node = node;
    return this;
  }

  /**
   * Get node
   * @return node
  */
  @ApiModelProperty(value = "")


  public String getNode() {
    return node;
  }

  public void setNode(String node) {
    this.node = node;
  }

  public CompareHypothesisDetailMembers prototype(String prototype) {
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

  public CompareHypothesisDetailMembers category(String category) {
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

  public CompareHypothesisDetailMembers type(String type) {
    this.type = type;
    return this;
  }

  /**
   * Get type
   * @return type
  */
  @ApiModelProperty(value = "")


  public String getType() {
    return type;
  }

  public void setType(String type) {
    this.type = type;
  }

  public CompareHypothesisDetailMembers dates(List<LDCTime> dates) {
    this.dates = dates;
    return this;
  }

  public CompareHypothesisDetailMembers addDatesItem(LDCTime datesItem) {
    if (this.dates == null) {
      this.dates = new ArrayList<>();
    }
    this.dates.add(datesItem);
    return this;
  }

  /**
   * Get dates
   * @return dates
  */
  @ApiModelProperty(value = "")

  @Valid

  public List<LDCTime> getDates() {
    return dates;
  }

  public void setDates(List<LDCTime> dates) {
    this.dates = dates;
  }

  public CompareHypothesisDetailMembers roles(List<CompareHypothesisDetailRoles> roles) {
    this.roles = roles;
    return this;
  }

  public CompareHypothesisDetailMembers addRolesItem(CompareHypothesisDetailRoles rolesItem) {
    if (this.roles == null) {
      this.roles = new ArrayList<>();
    }
    this.roles.add(rolesItem);
    return this;
  }

  /**
   * Get roles
   * @return roles
  */
  @ApiModelProperty(value = "")

  @Valid

  public List<CompareHypothesisDetailRoles> getRoles() {
    return roles;
  }

  public void setRoles(List<CompareHypothesisDetailRoles> roles) {
    this.roles = roles;
  }


  @Override
  public boolean equals(java.lang.Object o) {
    if (this == o) {
      return true;
    }
    if (o == null || getClass() != o.getClass()) {
      return false;
    }
    CompareHypothesisDetailMembers compareHypothesisDetailMembers = (CompareHypothesisDetailMembers) o;
    return Objects.equals(this.node, compareHypothesisDetailMembers.node) &&
        Objects.equals(this.prototype, compareHypothesisDetailMembers.prototype) &&
        Objects.equals(this.category, compareHypothesisDetailMembers.category) &&
        Objects.equals(this.type, compareHypothesisDetailMembers.type) &&
        Objects.equals(this.dates, compareHypothesisDetailMembers.dates) &&
        Objects.equals(this.roles, compareHypothesisDetailMembers.roles);
  }

  @Override
  public int hashCode() {
    return Objects.hash(node, prototype, category, type, dates, roles);
  }

  @Override
  public String toString() {
    StringBuilder sb = new StringBuilder();
    sb.append("class CompareHypothesisDetailMembers {\n");
    
    sb.append("    node: ").append(toIndentedString(node)).append("\n");
    sb.append("    prototype: ").append(toIndentedString(prototype)).append("\n");
    sb.append("    category: ").append(toIndentedString(category)).append("\n");
    sb.append("    type: ").append(toIndentedString(type)).append("\n");
    sb.append("    dates: ").append(toIndentedString(dates)).append("\n");
    sb.append("    roles: ").append(toIndentedString(roles)).append("\n");
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

