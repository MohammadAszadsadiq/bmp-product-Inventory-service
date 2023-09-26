package com.dtag.bm.service.product.inventory.service.model;

import java.io.Serializable;
import java.util.List;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;

@JsonInclude(JsonInclude.Include.NON_NULL)

@Document(collection = "ProductInventoryDetails")
public class Product implements Serializable {

	private static final long serialVersionUID = -2181525571815285129L;

	@Id
	@JsonProperty("_id")
	private String id;
	@JsonProperty("href")
	private String href;
	@JsonProperty("description")
	private String description;
	@JsonProperty("type")
	private String type;
	@JsonProperty("isBundle")
	private Boolean isBundle;
	@JsonProperty("isCustomerVisible")
	private Boolean isCustomerVisible;
	@JsonProperty("name")
	private String name;
	@JsonProperty("productSerialNumber")
	private String productSerialNumber;
	@JsonProperty("startDate")
	private String startDate;
	@JsonProperty("status")
	private String status;
	@JsonProperty("terminationDate")
	private String terminationDate;
	@JsonProperty("@baseType")
	private String baseType;
	@JsonProperty("@schemaLocation")
	private String schemaLocation;
	@JsonProperty("productSpecification")
	private ProductSpecification productSpecification;
	@JsonProperty("productOffering")
	private ProductOffering productOffering;
	@JsonProperty("productOrder")
	private List<ProductOrder> productOrder = null;
	@JsonProperty("realizingResource")
	private List<RealizingResource> realizingResource;
	@JsonProperty("realizingService")
	private List<RealizingService> realizingService = null;
	@JsonProperty("productTerm")
	private List<ProductTerm> productTerm = null;
	@JsonProperty("relatedParty")
	private List<RelatedParty> relatedParty;
	@JsonProperty("place")
	private List<Place> place;
	@JsonProperty("productCharacteristic")
	private List<ProductCharacteristic> productCharacteristic;
	@JsonProperty("billingAccount")
	private BillingAccount billingAccount;
	@JsonProperty("productRelationship")
	private List<ProductRelationship> productRelationship = null;
	@JsonProperty("productPrice")
	private List<ProductPrice> productPrice = null;

	@JsonProperty("_id")
	public String getId() {
		return id;
	}

	@JsonProperty("_id")
	public void setId(String id) {
		this.id = id;
	}

	@JsonProperty("href")
	public String getHref() {
		return href;
	}

	@JsonProperty("href")
	public void setHref(String href) {
		this.href = href;
	}

	@JsonProperty("description")
	public String getDescription() {
		return description;
	}

	@JsonProperty("description")
	public void setDescription(String description) {
		this.description = description;
	}

	@JsonProperty("type")
	public String getType() {
		return type;
	}

	@JsonProperty("type")
	public void setType(String type) {
		this.type = type;
	}

	@JsonProperty("isBundle")
	public Boolean getIsBundle() {
		return isBundle;
	}

	@JsonProperty("isBundle")
	public void setIsBundle(Boolean isBundle) {
		this.isBundle = isBundle;
	}

	@JsonProperty("isCustomerVisible")
	public Boolean getIsCustomerVisible() {
		return isCustomerVisible;
	}

	@JsonProperty("isCustomerVisible")
	public void setIsCustomerVisible(Boolean isCustomerVisible) {
		this.isCustomerVisible = isCustomerVisible;
	}

	@JsonProperty("name")
	public String getName() {
		return name;
	}

	@JsonProperty("name")
	public void setName(String name) {
		this.name = name;
	}

	@JsonProperty("productSerialNumber")
	public String getProductSerialNumber() {
		return productSerialNumber;
	}

	@JsonProperty("productSerialNumber")
	public void setProductSerialNumber(String productSerialNumber) {
		this.productSerialNumber = productSerialNumber;
	}

	@JsonProperty("startDate")
	public String getStartDate() {
		return startDate;
	}

	@JsonProperty("startDate")
	public void setStartDate(String startDate) {
		this.startDate = startDate;
	}

	@JsonProperty("status")
	public String getStatus() {
		return status;
	}

	@JsonProperty("status")
	public void setStatus(String status) {
		this.status = status;
	}

	@JsonProperty("terminationDate")
	public String getTerminationDate() {
		return terminationDate;
	}

	@JsonProperty("terminationDate")
	public void setTerminationDate(String terminationDate) {
		this.terminationDate = terminationDate;
	}

	@JsonProperty("@baseType")
	public String getBaseType() {
		return baseType;
	}

	@JsonProperty("@baseType")
	public void setBaseType(String baseType) {
		this.baseType = baseType;
	}

	@JsonProperty("@schemaLocation")
	public String getSchemaLocation() {
		return schemaLocation;
	}

	@JsonProperty("@schemaLocation")
	public void setSchemaLocation(String schemaLocation) {
		this.schemaLocation = schemaLocation;
	}

	@JsonProperty("productSpecification")
	public ProductSpecification getProductSpecificationRef() {
		return productSpecification;
	}

	@JsonProperty("productSpecification")
	public void setProductSpecificationRef(ProductSpecification productSpecificationRef) {
		this.productSpecification = productSpecificationRef;
	}

	@JsonProperty("productOffering")
	public ProductOffering getProductOfferingRef() {
		return productOffering;
	}

	@JsonProperty("productOffering")
	public void setProductOfferingRef(ProductOffering productOfferingRef) {
		this.productOffering = productOfferingRef;
	}

	@JsonProperty("productOrder")
	public List<ProductOrder> getProductOrderRef() {
		return productOrder;
	}

	@JsonProperty("productOrder")
	public void setProductOrderRef(List<ProductOrder> productOrderRef) {
		this.productOrder = productOrderRef;
	}

	@JsonProperty("realizingResource")
	public List<RealizingResource> getRealizingResourceRef() {
		return realizingResource;
	}

	@JsonProperty("realizingResource")
	public void setRealizingResourceRef(List<RealizingResource> realizingResourceRef) {
		this.realizingResource = realizingResourceRef;
	}

	@JsonProperty("realizingService")
	public List<RealizingService> getRealizingServiceRef() {
		return realizingService;
	}

	@JsonProperty("realizingService")
	public void setRealizingServiceRef(List<RealizingService> realizingServiceRef) {
		this.realizingService = realizingServiceRef;
	}

	@JsonProperty("productTerm")
	public List<ProductTerm> getProductTerm() {
		return productTerm;
	}

	@JsonProperty("productTerm")
	public void setProductTerm(List<ProductTerm> productTerm) {
		this.productTerm = productTerm;
	}

	@JsonProperty("relatedParty")
	public List<RelatedParty> getRelatedPartyRef() {
		return relatedParty;
	}

	@JsonProperty("relatedParty")
	public void setRelatedPartyRef(List<RelatedParty> relatedPartyRef) {
		this.relatedParty = relatedPartyRef;
	}

	@JsonProperty("place")
	public List<Place> getPlace() {
		return place;
	}

	@JsonProperty("place")
	public void setPlace(List<Place> place) {
		this.place = place;
	}

	@JsonProperty("productCharacteristic")
	public List<ProductCharacteristic> getProductCharacteristic() {
		return productCharacteristic;
	}

	@JsonProperty("productCharacteristic")
	public void setProductCharacteristic(List<ProductCharacteristic> productCharacteristic) {
		this.productCharacteristic = productCharacteristic;
	}

	@JsonProperty("billingAccount")
	public BillingAccount getBillingAccountRef() {
		return billingAccount;
	}

	@JsonProperty("billingAccount")
	public void setBillingAccountRef(BillingAccount billingAccountRef) {
		this.billingAccount = billingAccountRef;
	}

	@JsonProperty("productRelationship")
	public List<ProductRelationship> getProductRelationship() {
		return productRelationship;
	}

	@JsonProperty("productRelationship")
	public void setProductRelationship(List<ProductRelationship> productRelationship) {
		this.productRelationship = productRelationship;
	}

	@JsonProperty("productPrice")
	public List<ProductPrice> getProductPrice() {
		return productPrice;
	}

	@JsonProperty("productPrice")
	public void setProductPrice(List<ProductPrice> productPrice) {
		this.productPrice = productPrice;
	}

	@Override
	public String toString() {
		return "Product [id=" + id + ", href=" + href + ", description=" + description + ", type=" + type
				+ ", isBundle=" + isBundle + ", isCustomerVisible=" + isCustomerVisible + ", name=" + name
				+ ", productSerialNumber=" + productSerialNumber + ", startDate=" + startDate + ", status=" + status
				+ ", terminationDate=" + terminationDate + ", baseType=" + baseType + ", schemaLocation="
				+ schemaLocation + ", productSpecification=" + productSpecification + ", productOffering="
				+ productOffering + ", productOrder=" + productOrder + ", realizingResource=" + realizingResource
				+ ", realizingService=" + realizingService + ", productTerm=" + productTerm + ", relatedParty="
				+ relatedParty + ", place=" + place + ", productCharacteristic=" + productCharacteristic
				+ ", billingAccount=" + billingAccount + ", productRelationship=" + productRelationship
				+ ", productPrice=" + productPrice + "]";
	}

	
	

}