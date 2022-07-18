package com.ncc.verdi.model;

import java.util.Objects;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonCreator;
import com.ncc.verdi.model.DocObject;
import com.ncc.verdi.model.Entity;
import com.ncc.verdi.model.Event;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import java.util.ArrayList;
import java.util.List;
import org.openapitools.jackson.nullable.JsonNullable;
import javax.validation.Valid;
import javax.validation.constraints.*;

/**
 * HypothesisDetail
 */
@javax.annotation.Generated(value = "org.openapitools.codegen.languages.SpringCodegen", date = "2022-01-05T11:12:00.154-05:00[America/New_York]")

public class HypothesisDetail   {
  @JsonProperty("hypothesis")
  private String hypothesis;

  @JsonProperty("fillers")
  @Valid
  private List<Entity> fillers = null;

  @JsonProperty("members")
  @Valid
  private List<Event> members = null;

  @JsonProperty("docs")
  @Valid
  private List<DocObject> docs = null;

  public HypothesisDetail hypothesis(String hypothesis) {
    this.hypothesis = hypothesis;
    return this;
  }

  /**
   * Get hypothesis
   * @return hypothesis
  */
  @ApiModelProperty(value = "")


  public String getHypothesis() {
    return hypothesis;
  }

  public void setHypothesis(String hypothesis) {
    this.hypothesis = hypothesis;
  }

  public HypothesisDetail fillers(List<Entity> fillers) {
    this.fillers = fillers;
    return this;
  }

  public HypothesisDetail addFillersItem(Entity fillersItem) {
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

  public HypothesisDetail members(List<Event> members) {
    this.members = members;
    return this;
  }

  public HypothesisDetail addMembersItem(Event membersItem) {
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

  public List<Event> getMembers() {
    return members;
  }

  public void setMembers(List<Event> members) {
    this.members = members;
  }

  public HypothesisDetail docs(List<DocObject> docs) {
    this.docs = docs;
    return this;
  }

  public HypothesisDetail addDocsItem(DocObject docsItem) {
    if (this.docs == null) {
      this.docs = new ArrayList<>();
    }
    this.docs.add(docsItem);
    return this;
  }

  /**
   * Get docs
   * @return docs
  */
  @ApiModelProperty(value = "")

  @Valid

  public List<DocObject> getDocs() {
    return docs;
  }

  public void setDocs(List<DocObject> docs) {
    this.docs = docs;
  }


  @Override
  public boolean equals(java.lang.Object o) {
    if (this == o) {
      return true;
    }
    if (o == null || getClass() != o.getClass()) {
      return false;
    }
    HypothesisDetail hypothesisDetail = (HypothesisDetail) o;
    return Objects.equals(this.hypothesis, hypothesisDetail.hypothesis) &&
        Objects.equals(this.fillers, hypothesisDetail.fillers) &&
        Objects.equals(this.members, hypothesisDetail.members) &&
        Objects.equals(this.docs, hypothesisDetail.docs);
  }

  @Override
  public int hashCode() {
    return Objects.hash(hypothesis, fillers, members, docs);
  }

  @Override
  public String toString() {
    StringBuilder sb = new StringBuilder();
    sb.append("class HypothesisDetail {\n");
    
    sb.append("    hypothesis: ").append(toIndentedString(hypothesis)).append("\n");
    sb.append("    fillers: ").append(toIndentedString(fillers)).append("\n");
    sb.append("    members: ").append(toIndentedString(members)).append("\n");
    sb.append("    docs: ").append(toIndentedString(docs)).append("\n");
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

