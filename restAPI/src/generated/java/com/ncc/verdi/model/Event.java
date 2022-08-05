package com.ncc.verdi.model;

import java.util.Objects;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonCreator;
import com.ncc.verdi.model.Member;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import java.util.ArrayList;
import java.util.List;
import org.openapitools.jackson.nullable.JsonNullable;
import javax.validation.Valid;
import javax.validation.constraints.*;

/**
 * Event
 */
@javax.annotation.Generated(value = "org.openapitools.codegen.languages.SpringCodegen", date = "2022-08-05T15:31:00.866110500-04:00[America/New_York]")

public class Event   {
  @JsonProperty("cluster")
  private String cluster;

  @JsonProperty("handle")
  private String handle;

  @JsonProperty("category")
  private String category;

  @JsonProperty("prototype")
  private Member prototype = null;

  @JsonProperty("members")
  @Valid
  private List<Member> members = null;

  public Event cluster(String cluster) {
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

  public Event handle(String handle) {
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

  public Event category(String category) {
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

  public Event prototype(Member prototype) {
    this.prototype = prototype;
    return this;
  }

  /**
   * Get prototype
   * @return prototype
  */
  @ApiModelProperty(value = "")

  @Valid

  public Member getPrototype() {
    return prototype;
  }

  public void setPrototype(Member prototype) {
    this.prototype = prototype;
  }

  public Event members(List<Member> members) {
    this.members = members;
    return this;
  }

  public Event addMembersItem(Member membersItem) {
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

  public List<Member> getMembers() {
    return members;
  }

  public void setMembers(List<Member> members) {
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
    Event event = (Event) o;
    return Objects.equals(this.cluster, event.cluster) &&
        Objects.equals(this.handle, event.handle) &&
        Objects.equals(this.category, event.category) &&
        Objects.equals(this.prototype, event.prototype) &&
        Objects.equals(this.members, event.members);
  }

  @Override
  public int hashCode() {
    return Objects.hash(cluster, handle, category, prototype, members);
  }

  @Override
  public String toString() {
    StringBuilder sb = new StringBuilder();
    sb.append("class Event {\n");
    
    sb.append("    cluster: ").append(toIndentedString(cluster)).append("\n");
    sb.append("    handle: ").append(toIndentedString(handle)).append("\n");
    sb.append("    category: ").append(toIndentedString(category)).append("\n");
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

