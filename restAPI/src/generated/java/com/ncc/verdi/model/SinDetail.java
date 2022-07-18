package com.ncc.verdi.model;

import java.util.Objects;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonCreator;
import com.ncc.verdi.model.CompareHypothesisDetail;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import java.util.ArrayList;
import java.util.List;
import org.openapitools.jackson.nullable.JsonNullable;
import javax.validation.Valid;
import javax.validation.constraints.*;

/**
 * SinDetail
 */
@javax.annotation.Generated(value = "org.openapitools.codegen.languages.SpringCodegen", date = "2022-01-05T11:12:00.154-05:00[America/New_York]")

public class SinDetail   {
  @JsonProperty("sin")
  private String sin;

  @JsonProperty("description")
  private String description;

  @JsonProperty("hypotheses")
  @Valid
  private List<CompareHypothesisDetail> hypotheses = null;

  public SinDetail sin(String sin) {
    this.sin = sin;
    return this;
  }

  /**
   * Get sin
   * @return sin
  */
  @ApiModelProperty(value = "")


  public String getSin() {
    return sin;
  }

  public void setSin(String sin) {
    this.sin = sin;
  }

  public SinDetail description(String description) {
    this.description = description;
    return this;
  }

  /**
   * Get description
   * @return description
  */
  @ApiModelProperty(value = "")


  public String getDescription() {
    return description;
  }

  public void setDescription(String description) {
    this.description = description;
  }

  public SinDetail hypotheses(List<CompareHypothesisDetail> hypotheses) {
    this.hypotheses = hypotheses;
    return this;
  }

  public SinDetail addHypothesesItem(CompareHypothesisDetail hypothesesItem) {
    if (this.hypotheses == null) {
      this.hypotheses = new ArrayList<>();
    }
    this.hypotheses.add(hypothesesItem);
    return this;
  }

  /**
   * Get hypotheses
   * @return hypotheses
  */
  @ApiModelProperty(value = "")

  @Valid

  public List<CompareHypothesisDetail> getHypotheses() {
    return hypotheses;
  }

  public void setHypotheses(List<CompareHypothesisDetail> hypotheses) {
    this.hypotheses = hypotheses;
  }


  @Override
  public boolean equals(java.lang.Object o) {
    if (this == o) {
      return true;
    }
    if (o == null || getClass() != o.getClass()) {
      return false;
    }
    SinDetail sinDetail = (SinDetail) o;
    return Objects.equals(this.sin, sinDetail.sin) &&
        Objects.equals(this.description, sinDetail.description) &&
        Objects.equals(this.hypotheses, sinDetail.hypotheses);
  }

  @Override
  public int hashCode() {
    return Objects.hash(sin, description, hypotheses);
  }

  @Override
  public String toString() {
    StringBuilder sb = new StringBuilder();
    sb.append("class SinDetail {\n");
    
    sb.append("    sin: ").append(toIndentedString(sin)).append("\n");
    sb.append("    description: ").append(toIndentedString(description)).append("\n");
    sb.append("    hypotheses: ").append(toIndentedString(hypotheses)).append("\n");
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

