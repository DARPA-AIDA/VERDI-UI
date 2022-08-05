package com.ncc.verdi.model;

import java.util.Objects;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonValue;
import com.ncc.verdi.model.DocObject;
import com.ncc.verdi.model.Entity;
import com.ncc.verdi.model.EntityMemberAllOf;
import com.ncc.verdi.model.ReverseRole;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import java.util.ArrayList;
import java.util.List;
import org.openapitools.jackson.nullable.JsonNullable;
import javax.validation.Valid;
import javax.validation.constraints.*;

/**
 * EntityMember
 */
@javax.annotation.Generated(value = "org.openapitools.codegen.languages.SpringCodegen", date = "2022-08-05T15:31:00.866110500-04:00[America/New_York]")

public class EntityMember extends Entity  {
  @JsonProperty("connections")
  @Valid
  private List<ReverseRole> connections = null;

  public EntityMember connections(List<ReverseRole> connections) {
    this.connections = connections;
    return this;
  }

  public EntityMember addConnectionsItem(ReverseRole connectionsItem) {
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

  public List<ReverseRole> getConnections() {
    return connections;
  }

  public void setConnections(List<ReverseRole> connections) {
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
    EntityMember entityMember = (EntityMember) o;
    return Objects.equals(this.connections, entityMember.connections) &&
        super.equals(o);
  }

  @Override
  public int hashCode() {
    return Objects.hash(connections, super.hashCode());
  }

  @Override
  public String toString() {
    StringBuilder sb = new StringBuilder();
    sb.append("class EntityMember {\n");
    sb.append("    ").append(toIndentedString(super.toString())).append("\n");
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

