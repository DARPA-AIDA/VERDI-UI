package com.ncc.verdi.model;

import java.util.Objects;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonCreator;
import com.ncc.verdi.model.DocObject;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import java.util.ArrayList;
import java.util.List;
import org.openapitools.jackson.nullable.JsonNullable;
import javax.validation.Valid;
import javax.validation.constraints.*;

/**
 * Doc
 */
@javax.annotation.Generated(value = "org.openapitools.codegen.languages.SpringCodegen", date = "2022-08-05T15:31:00.866110500-04:00[America/New_York]")

public class Doc   {
  @JsonProperty("queryObject")
  private String queryObject;

  @JsonProperty("docs")
  @Valid
  private List<DocObject> docs = null;

  public Doc queryObject(String queryObject) {
    this.queryObject = queryObject;
    return this;
  }

  /**
   * Get queryObject
   * @return queryObject
  */
  @ApiModelProperty(value = "")


  public String getQueryObject() {
    return queryObject;
  }

  public void setQueryObject(String queryObject) {
    this.queryObject = queryObject;
  }

  public Doc docs(List<DocObject> docs) {
    this.docs = docs;
    return this;
  }

  public Doc addDocsItem(DocObject docsItem) {
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
    Doc doc = (Doc) o;
    return Objects.equals(this.queryObject, doc.queryObject) &&
        Objects.equals(this.docs, doc.docs);
  }

  @Override
  public int hashCode() {
    return Objects.hash(queryObject, docs);
  }

  @Override
  public String toString() {
    StringBuilder sb = new StringBuilder();
    sb.append("class Doc {\n");
    
    sb.append("    queryObject: ").append(toIndentedString(queryObject)).append("\n");
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

