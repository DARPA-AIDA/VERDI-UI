package com.ncc.verdi.model;

import java.util.Objects;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonCreator;
import com.ncc.verdi.model.Entity;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import java.util.ArrayList;
import java.util.List;
import org.openapitools.jackson.nullable.JsonNullable;
import javax.validation.Valid;
import javax.validation.constraints.*;

/**
 * Role
 */
@javax.annotation.Generated(value = "org.openapitools.codegen.languages.SpringCodegen", date = "2022-08-05T15:31:00.866110500-04:00[America/New_York]")

public class Role   {
  @JsonProperty("role")
  private String role;

  @JsonProperty("fillers")
  @Valid
  private List<Entity> fillers = null;

  public Role role(String role) {
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

  public Role fillers(List<Entity> fillers) {
    this.fillers = fillers;
    return this;
  }

  public Role addFillersItem(Entity fillersItem) {
    if (this.fillers == null) {
      this.fillers = new ArrayList<>();
    }
    this.fillers.add(fillersItem);
    return this;
  }

  /**
   * Get fillers
   * @return fillers
  */
  @ApiModelProperty(value = "")

  @Valid

  public List<Entity> getFillers() {
    return fillers;
  }

  public void setFillers(List<Entity> fillers) {
    this.fillers = fillers;
  }


  @Override
  public boolean equals(java.lang.Object o) {
    if (this == o) {
      return true;
    }
    if (o == null || getClass() != o.getClass()) {
      return false;
    }
    Role role = (Role) o;
    return Objects.equals(this.role, role.role) &&
        Objects.equals(this.fillers, role.fillers);
  }

  @Override
  public int hashCode() {
    return Objects.hash(role, fillers);
  }

  @Override
  public String toString() {
    StringBuilder sb = new StringBuilder();
    sb.append("class Role {\n");
    
    sb.append("    role: ").append(toIndentedString(role)).append("\n");
    sb.append("    fillers: ").append(toIndentedString(fillers)).append("\n");
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

