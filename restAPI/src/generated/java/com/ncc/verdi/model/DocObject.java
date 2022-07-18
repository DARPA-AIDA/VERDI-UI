package com.ncc.verdi.model;

import java.util.Objects;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonCreator;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import org.openapitools.jackson.nullable.JsonNullable;
import javax.validation.Valid;
import javax.validation.constraints.*;

/**
 * DocObject
 */
@javax.annotation.Generated(value = "org.openapitools.codegen.languages.SpringCodegen", date = "2022-01-05T11:12:00.154-05:00[America/New_York]")

public class DocObject   {
  @JsonProperty("id")
  private String id;

  @JsonProperty("title")
  private String title;

  @JsonProperty("contentDate")
  private String contentDate;

  @JsonProperty("downloadDate")
  private String downloadDate;

  public DocObject id(String id) {
    this.id = id;
    return this;
  }

  /**
   * Get id
   * @return id
  */
  @ApiModelProperty(value = "")


  public String getId() {
    return id;
  }

  public void setId(String id) {
    this.id = id;
  }

  public DocObject title(String title) {
    this.title = title;
    return this;
  }

  /**
   * Get title
   * @return title
  */
  @ApiModelProperty(value = "")


  public String getTitle() {
    return title;
  }

  public void setTitle(String title) {
    this.title = title;
  }

  public DocObject contentDate(String contentDate) {
    this.contentDate = contentDate;
    return this;
  }

  /**
   * Get contentDate
   * @return contentDate
  */
  @ApiModelProperty(value = "")


  public String getContentDate() {
    return contentDate;
  }

  public void setContentDate(String contentDate) {
    this.contentDate = contentDate;
  }

  public DocObject downloadDate(String downloadDate) {
    this.downloadDate = downloadDate;
    return this;
  }

  /**
   * Get downloadDate
   * @return downloadDate
  */
  @ApiModelProperty(value = "")


  public String getDownloadDate() {
    return downloadDate;
  }

  public void setDownloadDate(String downloadDate) {
    this.downloadDate = downloadDate;
  }


  @Override
  public boolean equals(java.lang.Object o) {
    if (this == o) {
      return true;
    }
    if (o == null || getClass() != o.getClass()) {
      return false;
    }
    DocObject docObject = (DocObject) o;
    return Objects.equals(this.id, docObject.id) &&
        Objects.equals(this.title, docObject.title) &&
        Objects.equals(this.contentDate, docObject.contentDate) &&
        Objects.equals(this.downloadDate, docObject.downloadDate);
  }

  @Override
  public int hashCode() {
    return Objects.hash(id, title, contentDate, downloadDate);
  }

  @Override
  public String toString() {
    StringBuilder sb = new StringBuilder();
    sb.append("class DocObject {\n");
    
    sb.append("    id: ").append(toIndentedString(id)).append("\n");
    sb.append("    title: ").append(toIndentedString(title)).append("\n");
    sb.append("    contentDate: ").append(toIndentedString(contentDate)).append("\n");
    sb.append("    downloadDate: ").append(toIndentedString(downloadDate)).append("\n");
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

