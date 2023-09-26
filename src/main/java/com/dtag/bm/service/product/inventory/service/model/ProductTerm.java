package com.dtag.bm.service.product.inventory.service.model;

import java.io.Serializable;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;

@JsonInclude(JsonInclude.Include.NON_NULL)

public class ProductTerm implements Serializable {

	private static final long serialVersionUID = 1802378359080262887L;

	@JsonProperty("name")
	private String name;
	@JsonProperty("description")
	private String description;
	@JsonProperty("Duration")
	private Duration duration;
	@JsonProperty("ValidFor")
	private ValidFor validFor;
	@JsonProperty("@type")
	private String type;

	@JsonProperty("name")
	public String getName() {
		return name;
	}

	@JsonProperty("name")
	public void setName(String name) {
		this.name = name;
	}

	@JsonProperty("description")
	public String getDescription() {
		return description;
	}

	@JsonProperty("description")
	public void setDescription(String description) {
		this.description = description;
	}

	@JsonProperty("Duration")
	public Duration getDuration() {
		return duration;
	}

	@JsonProperty("Duration")
	public void setDuration(Duration duration) {
		this.duration = duration;
	}

	@JsonProperty("ValidFor")
	public ValidFor getValidFor() {
		return validFor;
	}

	@JsonProperty("ValidFor")
	public void setValidFor(ValidFor validFor) {
		this.validFor = validFor;
	}

	@JsonProperty("@type")
	public String getType() {
		return type;
	}

	@JsonProperty("@type")
	public void setType(String type) {
		this.type = type;
	}

}