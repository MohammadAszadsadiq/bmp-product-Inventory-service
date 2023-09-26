package com.dtag.bm.service.product.inventory.service.type;


import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
@JsonInclude(JsonInclude.Include.NON_NULL)
public class ProductPrice_ {
	@JsonProperty("description")
	private String description;
	@JsonProperty("name")
	private String name;
	@JsonProperty("priceType")
	private String priceType;
	@JsonProperty("recurringChargePeriod")
	private String recurringChargePeriod;
	@JsonProperty("unitOfMeasure")
	private String unitOfMeasure;
	@JsonProperty("price")
	private Price_ price ;
	@JsonProperty("@baseType")
	private String baseType;
	@JsonProperty("@schemaLocation")
	private String schemaLocation;
	@JsonProperty("@type")
	private String type;
	public String getDescription() {
		return description;
	}
	public void setDescription(String description) {
		this.description = description;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
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
	public Price_ getPrice() {
		return price;
	}
	public void setPrice(Price_ price) {
		this.price = price;
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
	@Override
	public String toString() {
		return "ProductPrice_ [description=" + description + ", name=" + name + ", priceType=" + priceType
				+ ", recurringChargePeriod=" + recurringChargePeriod + ", unitOfMeasure=" + unitOfMeasure + ", price="
				+ price + ", baseType=" + baseType + ", schemaLocation=" + schemaLocation + ", type=" + type + "]";
	}
	

}
