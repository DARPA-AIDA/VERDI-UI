package com.ncc.verdi.model;

import java.util.Objects;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonCreator;
import com.ncc.verdi.model.EntityMember;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import java.util.ArrayList;
import java.util.List;
import org.openapitools.jackson.nullable.JsonNullable;
import javax.validation.Valid;
import javax.validation.constraints.*;

/**
 * EntityDetail
 */
@javax.annotation.Generated(value = "org.openapitools.codegen.languages.SpringCodegen", date = "2022-08-05T15:31:00.866110500-04:00[America/New_York]")

public class EntityDetail   {
  @JsonProperty("cluster")
  private String cluster;

  @JsonProperty("handle")
  private String handle;

  @JsonProperty("prototype")
  private EntityMember prototype = null;

  @JsonProperty("members")
  @Valid
  private List<EntityMember> members = null;

  public EntityDetail cluster(String cluster) {
    this.cluster = cluster;
    return this;
  }

  /**
   * Get cluster
   * @return cluster
  */
  @ApiModelProperty(value = "")


  public String getCluster() {
    return cluster;
  }

  public void setCluster(String cluster) {
    this.cluster = cluster;
  }

  public EntityDetail handle(String handle) {
    this.handle = handle;
    return this;
  }

  /**
   * Get handle
   * @return handle
  */
  @ApiModelProperty(value = "")


  public String getHandle() {
    return handle;
  }

  public void setHandle(String handle) {
    this.handle = handle;
  }

  public EntityDetail prototype(EntityMember prototype) {
    this.prototype = prototype;
    return this;
  }

  /**
   * Get prototype
   * @return prototype
  */
  @ApiModelProperty(value = "")

  @Valid

  public EntityMember getPrototype() {
    return prototype;
  }

  public void setPrototype(EntityMember prototype) {
    this.prototype = prototype;
  }

  public EntityDetail members(List<EntityMember> members) {
    this.members = members;
    return this;
  }

  public EntityDetail addMembersItem(EntityMember membersItem) {
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

  public List<EntityMember> getMembers() {
    return members;
  }

  public void setMembers(List<EntityMember> members) {
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
    EntityDetail entityDetail = (EntityDetail) o;
    return Objects.equals(this.cluster, entityDetail.cluster) &&
        Objects.equals(this.handle, entityDetail.handle) &&
        Objects.equals(this.prototype, entityDetail.prototype) &&
        Objects.equals(this.members, entityDetail.members);
  }

  @Override
  public int hashCode() {
    return Objects.hash(cluster, handle, prototype, members);
  }

  @Override
  public String toString() {
    StringBuilder sb = new StringBuilder();
    sb.append("class EntityDetail {\n");
    
    sb.append("    cluster: ").append(toIndentedString(cluster)).append("\n");
    sb.append("    handle: ").append(toIndentedString(handle)).append("\n");
    sb.append("    prototype: ").append(toIndentedString(prototype)).append("\n");
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

