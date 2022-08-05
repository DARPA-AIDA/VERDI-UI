package com.ncc.verdi.model;

import java.util.Objects;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonCreator;
import com.ncc.verdi.model.ClaimFrameProvenanceObjectValues;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import java.util.ArrayList;
import java.util.List;
import org.openapitools.jackson.nullable.JsonNullable;
import javax.validation.Valid;
import javax.validation.constraints.*;

/**
 * ClaimFrameProvenanceObject
 */
@javax.annotation.Generated(value = "org.openapitools.codegen.languages.SpringCodegen", date = "2022-08-05T15:31:00.866110500-04:00[America/New_York]")

public class ClaimFrameProvenanceObject   {
  @JsonProperty("propertyName")
  private String propertyName;

  @JsonProperty("values")
  @Valid
  private List<ClaimFrameProvenanceObjectValues> values = null;

  public ClaimFrameProvenanceObject propertyName(String propertyName) {
    this.propertyName = propertyName;
    return this;
  }

  /**
   * Get propertyName
   * @return propertyName
  */
  @ApiModelProperty(value = "")


  public String getPropertyName() {
    return propertyName;
  }

  public void setPropertyName(String propertyName) {
    this.propertyName = propertyName;
  }

  public ClaimFrameProvenanceObject values(List<ClaimFrameProvenanceObjectValues> values) {
    this.values = values;
    return this;
  }

  public ClaimFrameProvenanceObject addValuesItem(ClaimFrameProvenanceObjectValues valuesItem) {
    if (this.values == null) {
      this.values = new ArrayList<>();
    }
    this.values.add(valuesItem);
    return this;
  }

  /**
   * Get values
   * @return values
  */
  @ApiModelProperty(value = "")

  @Valid

  public List<ClaimFrameProvenanceObjectValues> getValues() {
    return values;
  }

  public void setValues(List<ClaimFrameProvenanceObjectValues> values) {
    this.values = values;
  }


  @Override
  public boolean equals(java.lang.Object o) {
    if (this == o) {
      return true;
    }
    if (o == null || getClass() != o.getClass()) {
      return false;
    }
    ClaimFrameProvenanceObject claimFrameProvenanceObject = (ClaimFrameProvenanceObject) o;
    return Objects.equals(this.propertyName, claimFrameProvenanceObject.propertyName) &&
        Objects.equals(this.values, claimFrameProvenanceObject.values);
  }

  @Override
  public int hashCode() {
    return Objects.hash(propertyName, values);
  }

  @Override
  public String toString() {
    StringBuilder sb = new StringBuilder();
    sb.append("class ClaimFrameProvenanceObject {\n");
    
    sb.append("    propertyName: ").append(toIndentedString(propertyName)).append("\n");
    sb.append("    values: ").append(toIndentedString(values)).append("\n");
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

