package com.ncc.verdi.model;

import java.util.Objects;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonCreator;
import com.ncc.verdi.model.JustifiedNode;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import java.util.ArrayList;
import java.util.List;
import org.openapitools.jackson.nullable.JsonNullable;
import javax.validation.Valid;
import javax.validation.constraints.*;

/**
 * ReverseRole
 */
@javax.annotation.Generated(value = "org.openapitools.codegen.languages.SpringCodegen", date = "2022-08-05T15:31:00.866110500-04:00[America/New_York]")

public class ReverseRole   {
  @JsonProperty("role")
  private String role;

  @JsonProperty("category")
  private String category;

  @JsonProperty("connections")
  @Valid
  private List<JustifiedNode> connections = null;

  public ReverseRole role(String role) {
    this.role = role;
    return this;
  }

  /**
   * Get role
   * @return role
  */
  @ApiModelProperty(value = "")


  public String getRole() {
    return role;
  }

  public void setRole(String role) {
    this.role = role;
  }

  public ReverseRole category(String category) {
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

  public ReverseRole connections(List<JustifiedNode> connections) {
    this.connections = connections;
    return this;
  }

  public ReverseRole addConnectionsItem(JustifiedNode connectionsItem) {
    if (this.connections == null) {
      this.connections = new ArrayList<>();
    }
    this.connections.add(connectionsItem);
    return this;
  }

  /**
   * Get connections
   * @return connections
  */
  @ApiModelProperty(value = "")

  @Valid

  public List<JustifiedNode> getConnections() {
    return connections;
  }

  public void setConnections(List<JustifiedNode> connections) {
    this.connections = connections;
  }


  @Override
  public boolean equals(java.lang.Object o) {
    if (this == o) {
      return true;
    }
    if (o == null || getClass() != o.getClass()) {
      return false;
    }
    ReverseRole reverseRole = (ReverseRole) o;
    return Objects.equals(this.role, reverseRole.role) &&
        Objects.equals(this.category, reverseRole.category) &&
        Objects.equals(this.connections, reverseRole.connections);
  }

  @Override
  public int hashCode() {
    return Objects.hash(role, category, connections);
  }

  @Override
  public String toString() {
    StringBuilder sb = new StringBuilder();
    sb.append("class ReverseRole {\n");
    
    sb.append("    role: ").append(toIndentedString(role)).append("\n");
    sb.append("    category: ").append(toIndentedString(category)).append("\n");
    sb.append("    connections: ").append(toIndentedString(connections)).append("\n");
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

