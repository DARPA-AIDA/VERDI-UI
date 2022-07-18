package com.ncc.verdi.model;

import java.util.Objects;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonSubTypes;
import com.fasterxml.jackson.annotation.JsonTypeInfo;
import com.fasterxml.jackson.annotation.JsonValue;
import com.ncc.verdi.model.DocObject;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import java.util.ArrayList;
import java.util.List;
import org.openapitools.jackson.nullable.JsonNullable;
import javax.validation.Valid;
import javax.validation.constraints.*;

/**
 * JustifiedNode
 */
@javax.annotation.Generated(value = "org.openapitools.codegen.languages.SpringCodegen", date = "2022-01-05T11:12:00.154-05:00[America/New_York]")

@JsonTypeInfo(use = JsonTypeInfo.Id.NAME, include = JsonTypeInfo.As.EXISTING_PROPERTY, property = "objectType", visible = true)
@JsonSubTypes({
  @JsonSubTypes.Type(value = Member.class, name = "Member"),
  @JsonSubTypes.Type(value = Entity.class, name = "Entity"),
  @JsonSubTypes.Type(value = Element.class, name = "Element"),
  @JsonSubTypes.Type(value = EntityMember.class, name = "EntityMember"),
  @JsonSubTypes.Type(value = EntityElement.class, name = "EntityElement"),
})

public class JustifiedNode   {
  @JsonProperty("id")
  private String id;

  @JsonProperty("handle")
  private String handle;

  /**
   * Gets or Sets objectType
   */
  public enum ObjectTypeEnum {
    ENTITY("Entity"),
    
    MEMBER("Member"),
    
    ELEMENT("Element"),
    
    ENTITYMEMBER("EntityMember"),
    
    ENTITYELEMENT("EntityElement"),
    
    JUSTIFIEDNODE("JustifiedNode");

    private String value;

    ObjectTypeEnum(String value) {
      this.value = value;
    }

    @JsonValue
    public String getValue() {
      return value;
    }

    @Override
    public String toString() {
      return String.valueOf(value);
    }

    @JsonCreator
    public static ObjectTypeEnum fromValue(String value) {
      for (ObjectTypeEnum b : ObjectTypeEnum.values()) {
        if (b.value.equals(value)) {
          return b;
        }
      }
      throw new IllegalArgumentException("Unexpected value '" + value + "'");
    }
  }

  @JsonProperty("objectType")
  private ObjectTypeEnum objectType;

  @JsonProperty("types")
  @Valid
  private List<String> types = null;

  @JsonProperty("docs")
  @Valid
  private List<DocObject> docs = null;

  public JustifiedNode id(String id) {
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

  public JustifiedNode handle(String handle) {
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

  public JustifiedNode objectType(ObjectTypeEnum objectType) {
    this.objectType = objectType;
    return this;
  }

  /**
   * Get objectType
   * @return objectType
  */
  @ApiModelProperty(value = "")


  public ObjectTypeEnum getObjectType() {
    return objectType;
  }

  public void setObjectType(ObjectTypeEnum objectType) {
    this.objectType = objectType;
  }

  public JustifiedNode types(List<String> types) {
    this.types = types;
    return this;
  }

  public JustifiedNode addTypesItem(String typesItem) {
    if (this.types == null) {
      this.types = new ArrayList<>();
    }
    this.types.add(typesItem);
    return this;
  }

  /**
   * Get types
   * @return types
  */
  @ApiModelProperty(value = "")


  public List<String> getTypes() {
    return types;
  }

  public void setTypes(List<String> types) {
    this.types = types;
  }

  public JustifiedNode docs(List<DocObject> docs) {
    this.docs = docs;
    return this;
  }

  public JustifiedNode addDocsItem(DocObject docsItem) {
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
    JustifiedNode justifiedNode = (JustifiedNode) o;
    return Objects.equals(this.id, justifiedNode.id) &&
        Objects.equals(this.handle, justifiedNode.handle) &&
        Objects.equals(this.objectType, justifiedNode.objectType) &&
        Objects.equals(this.types, justifiedNode.types) &&
        Objects.equals(this.docs, justifiedNode.docs);
  }

  @Override
  public int hashCode() {
    return Objects.hash(id, handle, objectType, types, docs);
  }

  @Override
  public String toString() {
    StringBuilder sb = new StringBuilder();
    sb.append("class JustifiedNode {\n");
    
    sb.append("    id: ").append(toIndentedString(id)).append("\n");
    sb.append("    handle: ").append(toIndentedString(handle)).append("\n");
    sb.append("    objectType: ").append(toIndentedString(objectType)).append("\n");
    sb.append("    types: ").append(toIndentedString(types)).append("\n");
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

