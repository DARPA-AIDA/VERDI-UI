package com.ncc.verdi.model;

import java.util.Objects;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonCreator;
import com.ncc.verdi.model.TA3DocObject;
import com.ncc.verdi.model.TA3EventEntities;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import java.util.ArrayList;
import java.util.List;
import org.openapitools.jackson.nullable.JsonNullable;
import javax.validation.Valid;
import javax.validation.constraints.*;

/**
 * TA3Event
 */
@javax.annotation.Generated(value = "org.openapitools.codegen.languages.SpringCodegen", date = "2022-01-05T11:12:00.154-05:00[America/New_York]")

public class TA3Event   {
  @JsonProperty("cluster")
  private String cluster;

  @JsonProperty("cluster_category")
  private String clusterCategory;

  @JsonProperty("cluster_type")
  private String clusterType;

  @JsonProperty("id")
  private String id;

  @JsonProperty("prototype_uri")
  private String prototypeUri;

  @JsonProperty("statement")
  private String statement;

  @JsonProperty("date")
  @Valid
  private List<String> date = null;

  @JsonProperty("category")
  private String category;

  @JsonProperty("sin")
  private String sin;

  @JsonProperty("run")
  private String run;

  @JsonProperty("hypothesis")
  private String hypothesis;

  @JsonProperty("entities")
  @Valid
  private List<TA3EventEntities> entities = null;

  @JsonProperty("docs")
  @Valid
  private List<TA3DocObject> docs = null;

  public TA3Event cluster(String cluster) {
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

  public TA3Event clusterCategory(String clusterCategory) {
    this.clusterCategory = clusterCategory;
    return this;
  }

  /**
   * Get clusterCategory
   * @return clusterCategory
  */
  @ApiModelProperty(value = "")


  public String getClusterCategory() {
    return clusterCategory;
  }

  public void setClusterCategory(String clusterCategory) {
    this.clusterCategory = clusterCategory;
  }

  public TA3Event clusterType(String clusterType) {
    this.clusterType = clusterType;
    return this;
  }

  /**
   * Get clusterType
   * @return clusterType
  */
  @ApiModelProperty(value = "")


  public String getClusterType() {
    return clusterType;
  }

  public void setClusterType(String clusterType) {
    this.clusterType = clusterType;
  }

  public TA3Event id(String id) {
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

  public TA3Event prototypeUri(String prototypeUri) {
    this.prototypeUri = prototypeUri;
    return this;
  }

  /**
   * Get prototypeUri
   * @return prototypeUri
  */
  @ApiModelProperty(value = "")


  public String getPrototypeUri() {
    return prototypeUri;
  }

  public void setPrototypeUri(String prototypeUri) {
    this.prototypeUri = prototypeUri;
  }

  public TA3Event statement(String statement) {
    this.statement = statement;
    return this;
  }

  /**
   * Get statement
   * @return statement
  */
  @ApiModelProperty(value = "")


  public String getStatement() {
    return statement;
  }

  public void setStatement(String statement) {
    this.statement = statement;
  }

  public TA3Event date(List<String> date) {
    this.date = date;
    return this;
  }

  public TA3Event addDateItem(String dateItem) {
    if (this.date == null) {
      this.date = new ArrayList<>();
    }
    this.date.add(dateItem);
    return this;
  }

  /**
   * Get date
   * @return date
  */
  @ApiModelProperty(value = "")


  public List<String> getDate() {
    return date;
  }

  public void setDate(List<String> date) {
    this.date = date;
  }

  public TA3Event category(String category) {
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

  public TA3Event sin(String sin) {
    this.sin = sin;
    return this;
  }

  /**
   * Get sin
   * @return sin
  */
  @ApiModelProperty(value = "")


  public String getSin() {
    return sin;
  }

  public void setSin(String sin) {
    this.sin = sin;
  }

  public TA3Event run(String run) {
    this.run = run;
    return this;
  }

  /**
   * Get run
   * @return run
  */
  @ApiModelProperty(value = "")


  public String getRun() {
    return run;
  }

  public void setRun(String run) {
    this.run = run;
  }

  public TA3Event hypothesis(String hypothesis) {
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

  public TA3Event entities(List<TA3EventEntities> entities) {
    this.entities = entities;
    return this;
  }

  public TA3Event addEntitiesItem(TA3EventEntities entitiesItem) {
    if (this.entities == null) {
      this.entities = new ArrayList<>();
    }
    this.entities.add(entitiesItem);
    return this;
  }

  /**
   * Get entities
   * @return entities
  */
  @ApiModelProperty(value = "")

  @Valid

  public List<TA3EventEntities> getEntities() {
    return entities;
  }

  public void setEntities(List<TA3EventEntities> entities) {
    this.entities = entities;
  }

  public TA3Event docs(List<TA3DocObject> docs) {
    this.docs = docs;
    return this;
  }

  public TA3Event addDocsItem(TA3DocObject docsItem) {
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

  public List<TA3DocObject> getDocs() {
    return docs;
  }

  public void setDocs(List<TA3DocObject> docs) {
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
    TA3Event ta3Event = (TA3Event) o;
    return Objects.equals(this.cluster, ta3Event.cluster) &&
        Objects.equals(this.clusterCategory, ta3Event.clusterCategory) &&
        Objects.equals(this.clusterType, ta3Event.clusterType) &&
        Objects.equals(this.id, ta3Event.id) &&
        Objects.equals(this.prototypeUri, ta3Event.prototypeUri) &&
        Objects.equals(this.statement, ta3Event.statement) &&
        Objects.equals(this.date, ta3Event.date) &&
        Objects.equals(this.category, ta3Event.category) &&
        Objects.equals(this.sin, ta3Event.sin) &&
        Objects.equals(this.run, ta3Event.run) &&
        Objects.equals(this.hypothesis, ta3Event.hypothesis) &&
        Objects.equals(this.entities, ta3Event.entities) &&
        Objects.equals(this.docs, ta3Event.docs);
  }

  @Override
  public int hashCode() {
    return Objects.hash(cluster, clusterCategory, clusterType, id, prototypeUri, statement, date, category, sin, run, hypothesis, entities, docs);
  }

  @Override
  public String toString() {
    StringBuilder sb = new StringBuilder();
    sb.append("class TA3Event {\n");
    
    sb.append("    cluster: ").append(toIndentedString(cluster)).append("\n");
    sb.append("    clusterCategory: ").append(toIndentedString(clusterCategory)).append("\n");
    sb.append("    clusterType: ").append(toIndentedString(clusterType)).append("\n");
    sb.append("    id: ").append(toIndentedString(id)).append("\n");
    sb.append("    prototypeUri: ").append(toIndentedString(prototypeUri)).append("\n");
    sb.append("    statement: ").append(toIndentedString(statement)).append("\n");
    sb.append("    date: ").append(toIndentedString(date)).append("\n");
    sb.append("    category: ").append(toIndentedString(category)).append("\n");
    sb.append("    sin: ").append(toIndentedString(sin)).append("\n");
    sb.append("    run: ").append(toIndentedString(run)).append("\n");
    sb.append("    hypothesis: ").append(toIndentedString(hypothesis)).append("\n");
    sb.append("    entities: ").append(toIndentedString(entities)).append("\n");
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

