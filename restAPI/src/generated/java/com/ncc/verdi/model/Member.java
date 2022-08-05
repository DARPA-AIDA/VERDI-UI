package com.ncc.verdi.model;

import java.util.Objects;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonValue;
import com.ncc.verdi.model.DocObject;
import com.ncc.verdi.model.JustifiedNode;
import com.ncc.verdi.model.MemberAllOf;
import com.ncc.verdi.model.Role;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import java.util.ArrayList;
import java.util.List;
import org.openapitools.jackson.nullable.JsonNullable;
import javax.validation.Valid;
import javax.validation.constraints.*;

/**
 * Member
 */
@javax.annotation.Generated(value = "org.openapitools.codegen.languages.SpringCodegen", date = "2022-08-05T15:31:00.866110500-04:00[America/New_York]")

public class Member extends JustifiedNode  {
  @JsonProperty("arguments")
  @Valid
  private List<Role> arguments = null;

  public Member arguments(List<Role> arguments) {
    this.arguments = arguments;
    return this;
  }

  public Member addArgumentsItem(Role argumentsItem) {
    if (this.arguments == null) {
      this.arguments = new ArrayList<>();
    }
    this.arguments.add(argumentsItem);
    return this;
  }

  /**
   * Get arguments
   * @return arguments
  */
  @ApiModelProperty(value = "")

  @Valid

  public List<Role> getArguments() {
    return arguments;
  }

  public void setArguments(List<Role> arguments) {
    this.arguments = arguments;
  }


  @Override
  public boolean equals(java.lang.Object o) {
    if (this == o) {
      return true;
    }
    if (o == null || getClass() != o.getClass()) {
      return false;
    }
    Member member = (Member) o;
    return Objects.equals(this.arguments, member.arguments) &&
        super.equals(o);
  }

  @Override
  public int hashCode() {
    return Objects.hash(arguments, super.hashCode());
  }

  @Override
  public String toString() {
    StringBuilder sb = new StringBuilder();
    sb.append("class Member {\n");
    sb.append("    ").append(toIndentedString(super.toString())).append("\n");
    sb.append("    arguments: ").append(toIndentedString(arguments)).append("\n");
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

