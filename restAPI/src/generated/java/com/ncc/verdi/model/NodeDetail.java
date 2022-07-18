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
 * NodeDetail
 */
@javax.annotation.Generated(value = "org.openapitools.codegen.languages.SpringCodegen", date = "2022-01-05T11:12:00.154-05:00[America/New_York]")

public class NodeDetail   {
  @JsonProperty("qnode")
  private String qnode;

  @JsonProperty("description")
  @Valid
  private List<String> description = null;

  @JsonProperty("label")
  @Valid
  private List<String> label = null;

  @JsonProperty("alias")
  @Valid
  private List<String> alias = null;

  @JsonProperty("data_type")
  private String dataType;

  public NodeDetail qnode(String qnode) {
    this.qnode = qnode;
    return this;
  }

  /**
   * Get qnode
   * @return qnode
  */
  @ApiModelProperty(value = "")


  public String getQnode() {
    return qnode;
  }

  public void setQnode(String qnode) {
    this.qnode = qnode;
  }

  public NodeDetail description(List<String> description) {
    this.description = description;
    return this;
  }

  public NodeDetail addDescriptionItem(String descriptionItem) {
    if (this.description == null) {
      this.description = new ArrayList<>();
    }
    this.description.add(descriptionItem);
    return this;
  }

  /**
   * Get description
   * @return description
  */
  @ApiModelProperty(value = "")


  public List<String> getDescription() {
    return description;
  }

  public void setDescription(List<String> description) {
    this.description = description;
  }

  public NodeDetail label(List<String> label) {
    this.label = label;
    return this;
  }

  public NodeDetail addLabelItem(String labelItem) {
    if (this.label == null) {
      this.label = new ArrayList<>();
    }
    this.label.add(labelItem);
    return this;
  }

  /**
   * Get label
   * @return label
  */
  @ApiModelProperty(value = "")


  public List<String> getLabel() {
    return label;
  }

  public void setLabel(List<String> label) {
    this.label = label;
  }

  public NodeDetail alias(List<String> alias) {
    this.alias = alias;
    return this;
  }

  public NodeDetail addAliasItem(String aliasItem) {
    if (this.alias == null) {
      this.alias = new ArrayList<>();
    }
    this.alias.add(aliasItem);
    return this;
  }

  /**
   * Get alias
   * @return alias
  */
  @ApiModelProperty(value = "")


  public List<String> getAlias() {
    return alias;
  }

  public void setAlias(List<String> alias) {
    this.alias = alias;
  }

  public NodeDetail dataType(String dataType) {
    this.dataType = dataType;
    return this;
  }

  /**
   * Get dataType
   * @return dataType
  */
  @ApiModelProperty(value = "")


  public String getDataType() {
    return dataType;
  }

  public void setDataType(String dataType) {
    this.dataType = dataType;
  }


  @Override
  public boolean equals(java.lang.Object o) {
    if (this == o) {
      return true;
    }
    if (o == null || getClass() != o.getClass()) {
      return false;
    }
    NodeDetail nodeDetail = (NodeDetail) o;
    return Objects.equals(this.qnode, nodeDetail.qnode) &&
        Objects.equals(this.description, nodeDetail.description) &&
        Objects.equals(this.label, nodeDetail.label) &&
        Objects.equals(this.alias, nodeDetail.alias) &&
        Objects.equals(this.dataType, nodeDetail.dataType);
  }

  @Override
  public int hashCode() {
    return Objects.hash(qnode, description, label, alias, dataType);
  }

  @Override
  public String toString() {
    StringBuilder sb = new StringBuilder();
    sb.append("class NodeDetail {\n");
    
    sb.append("    qnode: ").append(toIndentedString(qnode)).append("\n");
    sb.append("    description: ").append(toIndentedString(description)).append("\n");
    sb.append("    label: ").append(toIndentedString(label)).append("\n");
    sb.append("    alias: ").append(toIndentedString(alias)).append("\n");
    sb.append("    dataType: ").append(toIndentedString(dataType)).append("\n");
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

