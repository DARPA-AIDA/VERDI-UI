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
 * ClaimFrameComponentObjectValues
 */
@javax.annotation.Generated(value = "org.openapitools.codegen.languages.SpringCodegen", date = "2022-08-05T15:31:00.866110500-04:00[America/New_York]")

public class ClaimFrameComponentObjectValues   {
  @JsonProperty("componentId")
  private String componentId;

  @JsonProperty("componentURI")
  private String componentURI;

  @JsonProperty("componentName")
  private String componentName;

  @JsonProperty("componentKE")
  private String componentKE;

  @JsonProperty("componentProvenance")
  private String componentProvenance;

  @JsonProperty("componentTypes")
  @Valid
  private List<String> componentTypes = null;

  @JsonProperty("system")
  private String system;

  public ClaimFrameComponentObjectValues componentId(String componentId) {
    this.componentId = componentId;
    return this;
  }

  /**
   * Get componentId
   * @return componentId
  */
  @ApiModelProperty(value = "")


  public String getComponentId() {
    return componentId;
  }

  public void setComponentId(String componentId) {
    this.componentId = componentId;
  }

  public ClaimFrameComponentObjectValues componentURI(String componentURI) {
    this.componentURI = componentURI;
    return this;
  }

  /**
   * Get componentURI
   * @return componentURI
  */
  @ApiModelProperty(value = "")


  public String getComponentURI() {
    return componentURI;
  }

  public void setComponentURI(String componentURI) {
    this.componentURI = componentURI;
  }

  public ClaimFrameComponentObjectValues componentName(String componentName) {
    this.componentName = componentName;
    return this;
  }

  /**
   * Get componentName
   * @return componentName
  */
  @ApiModelProperty(value = "")


  public String getComponentName() {
    return componentName;
  }

  public void setComponentName(String componentName) {
    this.componentName = componentName;
  }

  public ClaimFrameComponentObjectValues componentKE(String componentKE) {
    this.componentKE = componentKE;
    return this;
  }

  /**
   * Get componentKE
   * @return componentKE
  */
  @ApiModelProperty(value = "")


  public String getComponentKE() {
    return componentKE;
  }

  public void setComponentKE(String componentKE) {
    this.componentKE = componentKE;
  }

  public ClaimFrameComponentObjectValues componentProvenance(String componentProvenance) {
    this.componentProvenance = componentProvenance;
    return this;
  }

  /**
   * Get componentProvenance
   * @return componentProvenance
  */
  @ApiModelProperty(value = "")


  public String getComponentProvenance() {
    return componentProvenance;
  }

  public void setComponentProvenance(String componentProvenance) {
    this.componentProvenance = componentProvenance;
  }

  public ClaimFrameComponentObjectValues componentTypes(List<String> componentTypes) {
    this.componentTypes = componentTypes;
    return this;
  }

  public ClaimFrameComponentObjectValues addComponentTypesItem(String componentTypesItem) {
    if (this.componentTypes == null) {
      this.componentTypes = new ArrayList<>();
    }
    this.componentTypes.add(componentTypesItem);
    return this;
  }

  /**
   * Get componentTypes
   * @return componentTypes
  */
  @ApiModelProperty(value = "")


  public List<String> getComponentTypes() {
    return componentTypes;
  }

  public void setComponentTypes(List<String> componentTypes) {
    this.componentTypes = componentTypes;
  }

  public ClaimFrameComponentObjectValues system(String system) {
    this.system = system;
    return this;
  }

  /**
   * Get system
   * @return system
  */
  @ApiModelProperty(value = "")


  public String getSystem() {
    return system;
  }

  public void setSystem(String system) {
    this.system = system;
  }


  @Override
  public boolean equals(java.lang.Object o) {
    if (this == o) {
      return true;
    }
    if (o == null || getClass() != o.getClass()) {
      return false;
    }
    ClaimFrameComponentObjectValues claimFrameComponentObjectValues = (ClaimFrameComponentObjectValues) o;
    return Objects.equals(this.componentId, claimFrameComponentObjectValues.componentId) &&
        Objects.equals(this.componentURI, claimFrameComponentObjectValues.componentURI) &&
        Objects.equals(this.componentName, claimFrameComponentObjectValues.componentName) &&
        Objects.equals(this.componentKE, claimFrameComponentObjectValues.componentKE) &&
        Objects.equals(this.componentProvenance, claimFrameComponentObjectValues.componentProvenance) &&
        Objects.equals(this.componentTypes, claimFrameComponentObjectValues.componentTypes) &&
        Objects.equals(this.system, claimFrameComponentObjectValues.system);
  }

  @Override
  public int hashCode() {
    return Objects.hash(componentId, componentURI, componentName, componentKE, componentProvenance, componentTypes, system);
  }

  @Override
  public String toString() {
    StringBuilder sb = new StringBuilder();
    sb.append("class ClaimFrameComponentObjectValues {\n");
    
    sb.append("    componentId: ").append(toIndentedString(componentId)).append("\n");
    sb.append("    componentURI: ").append(toIndentedString(componentURI)).append("\n");
    sb.append("    componentName: ").append(toIndentedString(componentName)).append("\n");
    sb.append("    componentKE: ").append(toIndentedString(componentKE)).append("\n");
    sb.append("    componentProvenance: ").append(toIndentedString(componentProvenance)).append("\n");
    sb.append("    componentTypes: ").append(toIndentedString(componentTypes)).append("\n");
    sb.append("    system: ").append(toIndentedString(system)).append("\n");
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

