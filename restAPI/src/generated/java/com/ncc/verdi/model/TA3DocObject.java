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
 * TA3DocObject
 */
@javax.annotation.Generated(value = "org.openapitools.codegen.languages.SpringCodegen", date = "2022-08-05T15:31:00.866110500-04:00[America/New_York]")

public class TA3DocObject   {
  @JsonProperty("document_id")
  private String documentId;

  @JsonProperty("document_title")
  private String documentTitle;

  @JsonProperty("document_contentDate")
  private String documentContentDate;

  @JsonProperty("document_downloadDate")
  private String documentDownloadDate;

  @JsonProperty("headline")
  private String headline;

  public TA3DocObject documentId(String documentId) {
    this.documentId = documentId;
    return this;
  }

  /**
   * Get documentId
   * @return documentId
  */
  @ApiModelProperty(value = "")


  public String getDocumentId() {
    return documentId;
  }

  public void setDocumentId(String documentId) {
    this.documentId = documentId;
  }

  public TA3DocObject documentTitle(String documentTitle) {
    this.documentTitle = documentTitle;
    return this;
  }

  /**
   * Get documentTitle
   * @return documentTitle
  */
  @ApiModelProperty(value = "")


  public String getDocumentTitle() {
    return documentTitle;
  }

  public void setDocumentTitle(String documentTitle) {
    this.documentTitle = documentTitle;
  }

  public TA3DocObject documentContentDate(String documentContentDate) {
    this.documentContentDate = documentContentDate;
    return this;
  }

  /**
   * Get documentContentDate
   * @return documentContentDate
  */
  @ApiModelProperty(value = "")


  public String getDocumentContentDate() {
    return documentContentDate;
  }

  public void setDocumentContentDate(String documentContentDate) {
    this.documentContentDate = documentContentDate;
  }

  public TA3DocObject documentDownloadDate(String documentDownloadDate) {
    this.documentDownloadDate = documentDownloadDate;
    return this;
  }

  /**
   * Get documentDownloadDate
   * @return documentDownloadDate
  */
  @ApiModelProperty(value = "")


  public String getDocumentDownloadDate() {
    return documentDownloadDate;
  }

  public void setDocumentDownloadDate(String documentDownloadDate) {
    this.documentDownloadDate = documentDownloadDate;
  }

  public TA3DocObject headline(String headline) {
    this.headline = headline;
    return this;
  }

  /**
   * Get headline
   * @return headline
  */
  @ApiModelProperty(value = "")


  public String getHeadline() {
    return headline;
  }

  public void setHeadline(String headline) {
    this.headline = headline;
  }


  @Override
  public boolean equals(java.lang.Object o) {
    if (this == o) {
      return true;
    }
    if (o == null || getClass() != o.getClass()) {
      return false;
    }
    TA3DocObject ta3DocObject = (TA3DocObject) o;
    return Objects.equals(this.documentId, ta3DocObject.documentId) &&
        Objects.equals(this.documentTitle, ta3DocObject.documentTitle) &&
        Objects.equals(this.documentContentDate, ta3DocObject.documentContentDate) &&
        Objects.equals(this.documentDownloadDate, ta3DocObject.documentDownloadDate) &&
        Objects.equals(this.headline, ta3DocObject.headline);
  }

  @Override
  public int hashCode() {
    return Objects.hash(documentId, documentTitle, documentContentDate, documentDownloadDate, headline);
  }

  @Override
  public String toString() {
    StringBuilder sb = new StringBuilder();
    sb.append("class TA3DocObject {\n");
    
    sb.append("    documentId: ").append(toIndentedString(documentId)).append("\n");
    sb.append("    documentTitle: ").append(toIndentedString(documentTitle)).append("\n");
    sb.append("    documentContentDate: ").append(toIndentedString(documentContentDate)).append("\n");
    sb.append("    documentDownloadDate: ").append(toIndentedString(documentDownloadDate)).append("\n");
    sb.append("    headline: ").append(toIndentedString(headline)).append("\n");
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

