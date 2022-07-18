package com.ncc.verdi.model;

import java.util.Objects;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonCreator;
import com.ncc.verdi.model.CompareHypothesisDetailMembers;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;
import org.openapitools.jackson.nullable.JsonNullable;
import javax.validation.Valid;
import javax.validation.constraints.*;

/**
 * CompareHypothesisDetail
 */
@javax.annotation.Generated(value = "org.openapitools.codegen.languages.SpringCodegen", date = "2022-01-05T11:12:00.154-05:00[America/New_York]")

public class CompareHypothesisDetail   {
  @JsonProperty("hypothesis")
  private String hypothesis;

  @JsonProperty("percentage")
  private BigDecimal percentage;

  @JsonProperty("types")
  @Valid
  private List<String> types = null;

  @JsonProperty("members")
  @Valid
  private List<CompareHypothesisDetailMembers> members = null;

  public CompareHypothesisDetail hypothesis(String hypothesis) {
    this.hypothesis = hypothesis;
    return this;
  }

  /**
   * Get hypothesis
   * @return hypothesis
  */
  @ApiModelProperty(value = "")


  public String getHypothesis() {
    return hypothesis;
  }

  public void setHypothesis(String hypothesis) {
    this.hypothesis = hypothesis;
  }

  public CompareHypothesisDetail percentage(BigDecimal percentage) {
    this.percentage = percentage;
    return this;
  }

  /**
   * Get percentage
   * @return percentage
  */
  @ApiModelProperty(value = "")

  @Valid

  public BigDecimal getPercentage() {
    return percentage;
  }

  public void setPercentage(BigDecimal percentage) {
    this.percentage = percentage;
  }

  public CompareHypothesisDetail types(List<String> types) {
    this.types = types;
    return this;
  }

  public CompareHypothesisDetail addTypesItem(String typesItem) {
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

  public CompareHypothesisDetail members(List<CompareHypothesisDetailMembers> members) {
    this.members = members;
    return this;
  }

  public CompareHypothesisDetail addMembersItem(CompareHypothesisDetailMembers membersItem) {
    if (this.members == null) {
      this.members = new ArrayList<>();
    }
    this.members.add(membersItem);
    return this;
  }

  /**
   * Get members
   * @return members
  */
  @ApiModelProperty(value = "")

  @Valid

  public List<CompareHypothesisDetailMembers> getMembers() {
    return members;
  }

  public void setMembers(List<CompareHypothesisDetailMembers> members) {
    this.members = members;
  }


  @Override
  public boolean equals(java.lang.Object o) {
    if (this == o) {
      return true;
    }
    if (o == null || getClass() != o.getClass()) {
      return false;
    }
    CompareHypothesisDetail compareHypothesisDetail = (CompareHypothesisDetail) o;
    return Objects.equals(this.hypothesis, compareHypothesisDetail.hypothesis) &&
        Objects.equals(this.percentage, compareHypothesisDetail.percentage) &&
        Objects.equals(this.types, compareHypothesisDetail.types) &&
        Objects.equals(this.members, compareHypothesisDetail.members);
  }

  @Override
  public int hashCode() {
    return Objects.hash(hypothesis, percentage, types, members);
  }

  @Override
  public String toString() {
    StringBuilder sb = new StringBuilder();
    sb.append("class CompareHypothesisDetail {\n");
    
    sb.append("    hypothesis: ").append(toIndentedString(hypothesis)).append("\n");
    sb.append("    percentage: ").append(toIndentedString(percentage)).append("\n");
    sb.append("    types: ").append(toIndentedString(types)).append("\n");
    sb.append("    members: ").append(toIndentedString(members)).append("\n");
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

