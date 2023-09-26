package com.dtag.bm.service.product.inventory.service.model;

import java.io.Serializable;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;

@JsonInclude(JsonInclude.Include.NON_NULL)

public class ProductSpecification implements Serializable {

	private static final long serialVersionUID = 1990366911912771534L;

	@JsonProperty("_id")
	private String id;
	@JsonProperty("href")
	private String href;
	@JsonProperty("version")
	private String version;
	@JsonProperty("name")
	private String name;
	@JsonProperty("referredType")
	private String referredType;
	@JsonProperty("TargetResourceSchema")
	private TargetResourceSchema targetResourceSchema;
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
	public String getVersion() {
		return version;
	}
	public void setVersion(String version) {
		this.version = version;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public String getReferredType() {
		return referredType;
	}
	public void setReferredType(String referredType) {
		this.referredType = referredType;
	}
	public TargetResourceSchema getTargetResourceSchema() {
		return targetResourceSchema;
	}
	public void setTargetResourceSchema(TargetResourceSchema targetResourceSchema) {
		this.targetResourceSchema = targetResourceSchema;
	}
	@Override
	public String toString() {
		return "ProductSpecification [id=" + id + ", href=" + href + ", version=" + version + ", name=" + name
				+ ", referredType=" + referredType + ", targetResourceSchema=" + targetResourceSchema + "]";
	}

	
	

}