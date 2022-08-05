package com.ncc.verdi.model;

import java.util.Objects;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonValue;
import com.ncc.verdi.model.DocObject;
import com.ncc.verdi.model.Entity;
import com.ncc.verdi.model.EntityElementAllOf;
import com.ncc.verdi.model.EntityMember;
import com.ncc.verdi.model.ReverseRole;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import java.util.ArrayList;
import java.util.List;
import org.openapitools.jackson.nullable.JsonNullable;
import javax.validation.Valid;
import javax.validation.constraints.*;

/**
 * EntityElement
 */
@javax.annotation.Generated(value = "org.openapitools.codegen.languages.SpringCodegen", date = "2022-08-05T15:31:00.866110500-04:00[America/New_York]")

public class EntityElement extends EntityMember  {
  @JsonProperty("clusters")
  @Valid
  private List<Entity> clusters = null;

  public EntityElement clusters(List<Entity> clusters) {
    this.clusters = clusters;
    return this;
  }

  public EntityElement addClustersItem(Entity clustersItem) {
    if (this.clusters == null) {
      this.clusters = new ArrayList<>();
    }
    this.clusters.add(clustersItem);
    return this;
  }

  /**
   * Get clusters
   * @return clusters
  */
  @ApiModelProperty(value = "")

  @Valid

  public List<Entity> getClusters() {
    return clusters;
  }

  public void setClusters(List<Entity> clusters) {
    this.clusters = clusters;
  }


  @Override
  public boolean equals(java.lang.Object o) {
    if (this == o) {
      return true;
    }
    if (o == null || getClass() != o.getClass()) {
      return false;
    }
    EntityElement entityElement = (EntityElement) o;
    return Objects.equals(this.clusters, entityElement.clusters) &&
        super.equals(o);
  }

  @Override
  public int hashCode() {
    return Objects.hash(clusters, super.hashCode());
  }

  @Override
  public String toString() {
    StringBuilder sb = new StringBuilder();
    sb.append("class EntityElement {\n");
    sb.append("    ").append(toIndentedString(super.toString())).append("\n");
    sb.append("    clusters: ").append(toIndentedString(clusters)).append("\n");
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

