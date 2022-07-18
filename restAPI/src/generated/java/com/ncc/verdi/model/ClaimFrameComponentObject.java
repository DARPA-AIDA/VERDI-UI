package com.ncc.verdi.model;

import java.util.Objects;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonCreator;
import com.ncc.verdi.model.ClaimFrameComponentObjectValues;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import java.util.ArrayList;
import java.util.List;
import org.openapitools.jackson.nullable.JsonNullable;
import javax.validation.Valid;
import javax.validation.constraints.*;

/**
 * ClaimFrameComponentObject
 */
@javax.annotation.Generated(value = "org.openapitools.codegen.languages.SpringCodegen", date = "2022-01-05T11:12:00.154-05:00[America/New_York]")

public class ClaimFrameComponentObject   {
  @JsonProperty("propertyName")
  private String propertyName;

  @JsonProperty("values")
  @Valid
  private List<ClaimFrameComponentObjectValues> values = null;

  public ClaimFrameComponentObject propertyName(String propertyName) {
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

  public ClaimFrameComponentObject values(List<ClaimFrameComponentObjectValues> values) {
    this.values = values;
    return this;
  }

  public ClaimFrameComponentObject addValuesItem(ClaimFrameComponentObjectValues valuesItem) {
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

  public List<ClaimFrameComponentObjectValues> getValues() {
    return values;
  }

  public void setValues(List<ClaimFrameComponentObjectValues> values) {
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
    ClaimFrameComponentObject claimFrameComponentObject = (ClaimFrameComponentObject) o;
    return Objects.equals(this.propertyName, claimFrameComponentObject.propertyName) &&
        Objects.equals(this.values, claimFrameComponentObject.values);
  }

  @Override
  public int hashCode() {
    return Objects.hash(propertyName, values);
  }

  @Override
  public String toString() {
    StringBuilder sb = new StringBuilder();
    sb.append("class ClaimFrameComponentObject {\n");
    
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

