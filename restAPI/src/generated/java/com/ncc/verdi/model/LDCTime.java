package com.ncc.verdi.model;

import java.util.Objects;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonCreator;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import org.openapitools.jackson.nullable.JsonNullable;
import javax.validation.Valid;
import javax.validation.constraints.*;

/**
 * LDCTime
 */
@javax.annotation.Generated(value = "org.openapitools.codegen.languages.SpringCodegen", date = "2022-01-05T11:12:00.154-05:00[America/New_York]")

public class LDCTime   {
  @JsonProperty("startBefore")
  private String startBefore;

  @JsonProperty("startAfter")
  private String startAfter;

  @JsonProperty("endBefore")
  private String endBefore;

  @JsonProperty("endAfter")
  private String endAfter;

  public LDCTime startBefore(String startBefore) {
    this.startBefore = startBefore;
    return this;
  }

  /**
   * Get startBefore
   * @return startBefore
  */
  @ApiModelProperty(value = "")


  public String getStartBefore() {
    return startBefore;
  }

  public void setStartBefore(String startBefore) {
    this.startBefore = startBefore;
  }

  public LDCTime startAfter(String startAfter) {
    this.startAfter = startAfter;
    return this;
  }

  /**
   * Get startAfter
   * @return startAfter
  */
  @ApiModelProperty(value = "")


  public String getStartAfter() {
    return startAfter;
  }

  public void setStartAfter(String startAfter) {
    this.startAfter = startAfter;
  }

  public LDCTime endBefore(String endBefore) {
    this.endBefore = endBefore;
    return this;
  }

  /**
   * Get endBefore
   * @return endBefore
  */
  @ApiModelProperty(value = "")


  public String getEndBefore() {
    return endBefore;
  }

  public void setEndBefore(String endBefore) {
    this.endBefore = endBefore;
  }

  public LDCTime endAfter(String endAfter) {
    this.endAfter = endAfter;
    return this;
  }

  /**
   * Get endAfter
   * @return endAfter
  */
  @ApiModelProperty(value = "")


  public String getEndAfter() {
    return endAfter;
  }

  public void setEndAfter(String endAfter) {
    this.endAfter = endAfter;
  }


  @Override
  public boolean equals(java.lang.Object o) {
    if (this == o) {
      return true;
    }
    if (o == null || getClass() != o.getClass()) {
      return false;
    }
    LDCTime ldCTime = (LDCTime) o;
    return Objects.equals(this.startBefore, ldCTime.startBefore) &&
        Objects.equals(this.startAfter, ldCTime.startAfter) &&
        Objects.equals(this.endBefore, ldCTime.endBefore) &&
        Objects.equals(this.endAfter, ldCTime.endAfter);
  }

  @Override
  public int hashCode() {
    return Objects.hash(startBefore, startAfter, endBefore, endAfter);
  }

  @Override
  public String toString() {
    StringBuilder sb = new StringBuilder();
    sb.append("class LDCTime {\n");
    
    sb.append("    startBefore: ").append(toIndentedString(startBefore)).append("\n");
    sb.append("    startAfter: ").append(toIndentedString(startAfter)).append("\n");
    sb.append("    endBefore: ").append(toIndentedString(endBefore)).append("\n");
    sb.append("    endAfter: ").append(toIndentedString(endAfter)).append("\n");
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

