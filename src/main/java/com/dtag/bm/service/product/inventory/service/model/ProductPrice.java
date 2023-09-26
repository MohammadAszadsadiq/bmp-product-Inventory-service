package com.dtag.bm.service.product.inventory.service.model;

import java.io.Serializable;
import java.util.List;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;

@JsonInclude(JsonInclude.Include.NON_NULL)

public class ProductPrice implements Serializable {

	private static final long serialVersionUID = -103436383956817649L;

	@JsonProperty("_id")
	private String id;
	@JsonProperty("name")
	private String name;
	@JsonProperty("description")
	private String description;
	@JsonProperty("priceType")
	private String priceType;
	@JsonProperty("recurringChargePeriod")
	private String recurringChargePeriod;
	@JsonProperty("unitOfMeasure")
	private String unitOfMeasure;
	@JsonProperty("@type")
	private String type;
	@JsonProperty("@schemaLocation")
	private String schemaLocation;
	@JsonProperty("BillingAccountRef")
	private BillingAccount billingAccountRef;
	@JsonProperty("price")
	private Price price;
	@JsonProperty("PriceAlteration")
	private List<PriceAlteration> priceAlteration = null;
	public String getId() {
		return id;
	}
	public void setId(String id) {
		this.id = id;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public String getDescription() {
		return description;
	}
	public void setDescription(String description) {
		this.description = description;
	}
	public String getPriceType() {
		return priceType;
	}
	public void setPriceType(String priceType) {
		this.priceType = priceType;
	}
	public String getRecurringChargePeriod() {
		return recurringChargePeriod;
	}
	public void setRecurringChargePeriod(String recurringChargePeriod) {
		this.recurringChargePeriod = recurringChargePeriod;
	}
	public String getUnitOfMeasure() {
		return unitOfMeasure;
	}
	public void setUnitOfMeasure(String unitOfMeasure) {
		this.unitOfMeasure = unitOfMeasure;
	}
	public String getType() {
		return type;
	}
	public void setType(String type) {
		this.type = type;
	}
	public String getSchemaLocation() {
		return schemaLocation;
	}
	public void setSchemaLocation(String schemaLocation) {
		this.schemaLocation = schemaLocation;
	}
	public BillingAccount getBillingAccountRef() {
		return billingAccountRef;
	}
	public void setBillingAccountRef(BillingAccount billingAccountRef) {
		this.billingAccountRef = billingAccountRef;
	}
	public Price getPrice() {
		return price;
	}
	public void setPrice(Price price) {
		this.price = price;
	}
	public List<PriceAlteration> getPriceAlteration() {
		return priceAlteration;
	}
	public void setPriceAlteration(List<PriceAlteration> priceAlteration) {
		this.priceAlteration = priceAlteration;
	}
	@Override
	public String toString() {
		return "ProductPrice [id=" + id + ", name=" + name + ", description=" + description + ", priceType=" + priceType
				+ ", recurringChargePeriod=" + recurringChargePeriod + ", unitOfMeasure=" + unitOfMeasure + ", type="
				+ type + ", schemaLocation=" + schemaLocation + ", billingAccountRef=" + billingAccountRef + ", price="
				+ price + ", priceAlteration=" + priceAlteration + "]";
	}

	
	
}