package com.dtag.bm.service.product.inventory.service.type;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
@JsonInclude(JsonInclude.Include.NON_NULL)
public class ProductOffering_ {

	@JsonProperty("id")
	private String id;
	@JsonProperty("href")
	private String href;
	@JsonProperty("name")
	private String name;
	@JsonProperty("@baseType")
	private String baseType;
	@JsonProperty("@schemaLocation")
	private String schemaLocation;
	@JsonProperty("@type")
	private String type;
	@JsonProperty("@referredType")
	private String referredType;
	
	public String getId() {
		return id;
	}
	public void setId(String id) {
		this.id = id;
	}
	public String getHref() {
		return href;
	}
	public void setHref(String href) {
		this.href = href;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public String getBaseType() {
		return baseType;
	}
	public void setBaseType(String baseType) {
		this.baseType = baseType;
	}
	public String getSchemaLocation() {
		return schemaLocation;
	}
	public void setSchemaLocation(String schemaLocation) {
		this.schemaLocation = schemaLocation;
	}
	public String getType() {
		return type;
	}
	public void setType(String type) {
		this.type = type;
	}
	@JsonProperty("@referredType")
	public String getReferredType() {
		return referredType;
	}
	@JsonProperty("@referredType")
	public void setReferredType(String referredType) {
		this.referredType = referredType;
	}
	@Override
	public String toString() {
		return "ProductOffering [id=" + id + ", href=" + href + ", name=" + name + ", baseType=" + baseType
				+ ", schemaLocation=" + schemaLocation + ", type=" + type + ", referredType=" + referredType + "]";
	}
	
	
}
