package com.dtag.bm.service.product.inventory.service.model;

import java.io.Serializable;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;

@JsonInclude(JsonInclude.Include.NON_NULL)

public class RelatedParty implements Serializable {

	private static final long serialVersionUID = 6943410552791058106L;
	
	@JsonProperty("_id")
	private String id;
	@JsonProperty("href")
	private String href;
	@JsonProperty("role")
	private String role;
	@JsonProperty("name")
	private String name;
	@JsonProperty("ValidFor")
	private ValidFor validFor;
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
	public String getRole() {
		return role;
	}
	public void setRole(String role) {
		this.role = role;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public ValidFor getValidFor() {
		return validFor;
	}
	public void setValidFor(ValidFor validFor) {
		this.validFor = validFor;
	}
	public String getReferredType() {
		return referredType;
	}
	public void setReferredType(String referredType) {
		this.referredType = referredType;
	}
	@Override
	public String toString() {
		return "RelatedParty [id=" + id + ", href=" + href + ", role=" + role + ", name=" + name + ", validFor="
				+ validFor + ", referredType=" + referredType + "]";
	}

	

}