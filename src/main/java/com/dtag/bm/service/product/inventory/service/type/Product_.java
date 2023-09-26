package com.dtag.bm.service.product.inventory.service.type;
import java.util.List;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
@JsonInclude(JsonInclude.Include.NON_NULL)
public class Product_ {
	
	@JsonProperty("id")
	private String id;
	@JsonProperty("href")
	private String href;
	@JsonProperty("description")
	private String description;
	@JsonProperty("isBundle")
	private Boolean isBundle;
	@JsonProperty("isCustomerVisible")
	private Boolean isCustomerVisible;
	@JsonProperty("name")
	private String name;
	@JsonProperty("orderDate")
	private String orderDate;
	@JsonProperty("productSerialNumber")
	private String productSerialNumber;
	@JsonProperty("startDate")
	private String startDate;
	@JsonProperty("terminationDate")
	private String terminationDate;
	@JsonProperty("place")
	private List<Place_> place = null;
	@JsonProperty("productCharacteristic")
	private List<ProductCharacteristic_> productCharacteristic = null ;
	@JsonProperty("productSpecification")
	private ProductSpecification_ productSpecification = null;
	@JsonProperty("status")
	private String status;
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
	public String getDescription() {
		return description;
	}
	public void setDescription(String description) {
		this.description = description;
	}
	public Boolean getIsBundle() {
		return isBundle;
	}
	public void setIsBundle(Boolean isBundle) {
		this.isBundle = isBundle;
	}
	public Boolean getIsCustomerVisible() {
		return isCustomerVisible;
	}
	public void setIsCustomerVisible(Boolean isCustomerVisible) {
		this.isCustomerVisible = isCustomerVisible;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public String getOrderDate() {
		return orderDate;
	}
	public void setOrderDate(String orderDate) {
		this.orderDate = orderDate;
	}
	public String getProductSerialNumber() {
		return productSerialNumber;
	}
	public void setProductSerialNumber(String productSerialNumber) {
		this.productSerialNumber = productSerialNumber;
	}
	public String getStartDate() {
		return startDate;
	}
	public void setStartDate(String startDate) {
		this.startDate = startDate;
	}
	public String getTerminationDate() {
		return terminationDate;
	}
	public void setTerminationDate(String terminationDate) {
		this.terminationDate = terminationDate;
	}
	public List<Place_> getPlace() {
		return place;
	}
	public void setPlace(List<Place_> place) {
		this.place = place;
	}
	public List<ProductCharacteristic_> getProductCharacteristic() {
		return productCharacteristic;
	}
	public void setProductCharacteristic(List<ProductCharacteristic_> productCharacteristic) {
		this.productCharacteristic = productCharacteristic;
	}
	public ProductSpecification_ getProductSpecification() {
		return productSpecification;
	}
	public void setProductSpecification(ProductSpecification_ productSpecification) {
		this.productSpecification = productSpecification;
	}
	public String getStatus() {
		return status;
	}
	public void setStatus(String status) {
		this.status = status;
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
	public String getReferredType() {
		return referredType;
	}
	public void setReferredType(String referredType) {
		this.referredType = referredType;
	}
	@Override
	public String toString() {
		return "Product [id=" + id + ", href=" + href + ", description=" + description + ", isBundle=" + isBundle
				+ ", isCustomerVisible=" + isCustomerVisible + ", name=" + name + ", orderDate=" + orderDate
				+ ", productSerialNumber=" + productSerialNumber + ", startDate=" + startDate + ", terminationDate="
				+ terminationDate + ", place=" + place + ", productCharacteristic=" + productCharacteristic
				+ ", productSpecification=" + productSpecification + ", status=" + status + ", baseType=" + baseType
				+ ", schemaLocation=" + schemaLocation + ", type=" + type + ", referredType=" + referredType + "]";
	}
	
	
}
