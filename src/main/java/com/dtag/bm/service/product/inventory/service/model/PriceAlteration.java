package com.dtag.bm.service.product.inventory.service.model;

import java.io.Serializable;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;

@JsonInclude(JsonInclude.Include.NON_NULL)

public class PriceAlteration implements Serializable {

	private static final long serialVersionUID = 1005494008777163161L;

	@JsonProperty("id")
	private String id;
	@JsonProperty("name")
	private String name;
	@JsonProperty("description")
	private String description;
	@JsonProperty("priceType")
	private String priceType;
	@JsonProperty("unitOfMeasure")
	private String unitOfMeasure;
	@JsonProperty("recurringChargePeriod")
	private String recurringChargePeriod;
	@JsonProperty("ValidFor")
	private ValidFor validFor;
	@JsonProperty("priority")
	private Integer priority;
	@JsonProperty("@type")
	private String type;
	@JsonProperty("@schemaLocation")
	private String schemaLocation;
	@JsonProperty("Price")
	private Price price;

	@JsonProperty("id")
	public String getId() {
		return id;
	}

	@JsonProperty("id")
	public void setId(String id) {
		this.id = id;
	}

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

	@JsonProperty("priceType")
	public String getPriceType() {
		return priceType;
	}

	@JsonProperty("priceType")
	public void setPriceType(String priceType) {
		this.priceType = priceType;
	}

	@JsonProperty("unitOfMeasure")
	public String getUnitOfMeasure() {
		return unitOfMeasure;
	}

	@JsonProperty("unitOfMeasure")
	public void setUnitOfMeasure(String unitOfMeasure) {
		this.unitOfMeasure = unitOfMeasure;
	}

	@JsonProperty("recurringChargePeriod")
	public String getRecurringChargePeriod() {
		return recurringChargePeriod;
	}

	@JsonProperty("recurringChargePeriod")
	public void setRecurringChargePeriod(String recurringChargePeriod) {
		this.recurringChargePeriod = recurringChargePeriod;
	}

	@JsonProperty("ValidFor")
	public ValidFor getValidFor() {
		return validFor;
	}

	@JsonProperty("ValidFor")
	public void setValidFor(ValidFor validFor) {
		this.validFor = validFor;
	}

	@JsonProperty("priority")
	public Integer getPriority() {
		return priority;
	}

	@JsonProperty("priority")
	public void setPriority(Integer priority) {
		this.priority = priority;
	}

	@JsonProperty("@type")
	public String getType() {
		return type;
	}

	@JsonProperty("@type")
	public void setType(String type) {
		this.type = type;
	}

	@JsonProperty("@schemaLocation")
	public String getSchemaLocation() {
		return schemaLocation;
	}

	@JsonProperty("@schemaLocation")
	public void setSchemaLocation(String schemaLocation) {
		this.schemaLocation = schemaLocation;
	}

	@JsonProperty("Price")
	public Price getPrice() {
		return price;
	}

	@JsonProperty("Price")
	public void setPrice(Price price) {
		this.price = price;
	}

}