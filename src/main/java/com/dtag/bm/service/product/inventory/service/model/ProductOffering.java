package com.dtag.bm.service.product.inventory.service.model;

import java.io.Serializable;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;

@JsonInclude(JsonInclude.Include.NON_NULL)

public class ProductOffering implements Serializable {

	private static final long serialVersionUID = 5251797055828667838L;

	@JsonProperty("_id")
	private String id;
	@JsonProperty("href")
	private String href;
	@JsonProperty("name")
	private String name;
	@JsonProperty("referredType")
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
	public String getReferredType() {
		return referredType;
	}
	public void setReferredType(String referredType) {
		this.referredType = referredType;
	}
	@Override
	public String toString() {
		return "ProductOffering [id=" + id + ", href=" + href + ", name=" + name + ", referredType=" + referredType
				+ "]";
	}

	

}