package com.ncc.verdi.model;

import java.util.Objects;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonCreator;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import java.util.ArrayList;
import java.util.List;
import org.openapitools.jackson.nullable.JsonNullable;
import javax.validation.Valid;
import javax.validation.constraints.*;

/**
 * CacheObject
 */
@javax.annotation.Generated(value = "org.openapitools.codegen.languages.SpringCodegen", date = "2022-08-05T15:31:00.866110500-04:00[America/New_York]")

public class CacheObject   {
  @JsonProperty("taskArea")
  private String taskArea;

  @JsonProperty("overwrite")
  private Boolean overwrite;

  @JsonProperty("graphRootURI")
  @Valid
  private List<String> graphRootURI = null;

  public CacheObject taskArea(String taskArea) {
    this.taskArea = taskArea;
    return this;
  }

  /**
   * Get taskArea
   * @return taskArea
  */
  @ApiModelProperty(example = "TA3", value = "")


  public String getTaskArea() {
    return taskArea;
  }

  public void setTaskArea(String taskArea) {
    this.taskArea = taskArea;
  }

  public CacheObject overwrite(Boolean overwrite) {
    this.overwrite = overwrite;
    return this;
  }

  /**
   * Get overwrite
   * @return overwrite
  */
  @ApiModelProperty(example = "true", value = "")


  public Boolean getOverwrite() {
    return overwrite;
  }

  public void setOverwrite(Boolean overwrite) {
    this.overwrite = overwrite;
  }

  public CacheObject graphRootURI(List<String> graphRootURI) {
    this.graphRootURI = graphRootURI;
    return this;
  }

  public CacheObject addGraphRootURIItem(String graphRootURIItem) {
    if (this.graphRootURI == null) {
      this.graphRootURI = new ArrayList<>();
    }
    this.graphRootURI.add(graphRootURIItem);
    return this;
  }

  /**
   * Get graphRootURI
   * @return graphRootURI
  */
  @ApiModelProperty(example = "[\"https://www.nextcentury.com/TA3/E201/BBN-20210922/COLORADO-20210121/BBN-20210120\",\"https://www.nextcentury.com/TA3/E202/BBN-20210922/COLORADO-20210121/BBN-20210120\",\"https://www.nextcentury.com/TA3/E203/BBN-20210922/COLORADO-20210121/BBN-20210120\"]", value = "")


  public List<String> getGraphRootURI() {
    return graphRootURI;
  }

  public void setGraphRootURI(List<String> graphRootURI) {
    this.graphRootURI = graphRootURI;
  }


  @Override
  public boolean equals(java.lang.Object o) {
    if (this == o) {
      return true;
    }
    if (o == null || getClass() != o.getClass()) {
      return false;
    }
    CacheObject cacheObject = (CacheObject) o;
    return Objects.equals(this.taskArea, cacheObject.taskArea) &&
        Objects.equals(this.overwrite, cacheObject.overwrite) &&
        Objects.equals(this.graphRootURI, cacheObject.graphRootURI);
  }

  @Override
  public int hashCode() {
    return Objects.hash(taskArea, overwrite, graphRootURI);
  }

  @Override
  public String toString() {
    StringBuilder sb = new StringBuilder();
    sb.append("class CacheObject {\n");
    
    sb.append("    taskArea: ").append(toIndentedString(taskArea)).append("\n");
    sb.append("    overwrite: ").append(toIndentedString(overwrite)).append("\n");
    sb.append("    graphRootURI: ").append(toIndentedString(graphRootURI)).append("\n");
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

