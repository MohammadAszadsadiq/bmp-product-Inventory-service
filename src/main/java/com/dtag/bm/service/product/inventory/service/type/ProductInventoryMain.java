package com.dtag.bm.service.product.inventory.service.type;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import java.util.List;


import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;

@JsonInclude(JsonInclude.Include.NON_NULL)
@JsonIgnoreProperties
public class ProductInventoryMain {

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
	@JsonProperty("startDate")
	private String startDate;
	@JsonProperty("terminationDate")
	private String terminationDate;
	@JsonProperty("place")
	private List<Place_> place = null;

	@JsonProperty("product")
	private List<Product_> product = null;

	@JsonProperty("productOffering")
	private ProductOffering_ productOffering = null;

	@JsonProperty("productPrice")
	private List<ProductPrice_> productPrice;

	@JsonProperty("productRelationship")
	private List<ProductRelationship_> productRelationship = null;

	@JsonProperty("productTerm")
	private List<ProductTerm_> productTerm = null;

	@JsonProperty("relatedParty")
	private List<RelatedParty_> relatedParty;

	@JsonProperty("status")
	private String status;

	@JsonProperty("@baseType")
	private String baseType;
	@JsonProperty("@schemaLocation")
	private String schemaLocation;
	@JsonProperty("@type")
	private String type;

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

	public List<com.dtag.bm.service.product.inventory.service.type.Place_> getPlace() {
		return place;
	}

	public void setPlace(List<com.dtag.bm.service.product.inventory.service.type.Place_> place) {
		this.place = place;
	}

	public List<Product_> getProduct() {
		return product;
	}

	public void setProduct(List<Product_> product) {
		this.product = product;
	}

	public ProductOffering_ getProductOffering() {
		return productOffering;
	}

	public void setProductOffering(ProductOffering_ productOffering) {
		this.productOffering = productOffering;
	}

	public List<ProductPrice_> getProductPrice() {
		return productPrice;
	}

	public void setProductPrice(List<ProductPrice_> productPrice) {
		this.productPrice = productPrice;
	}

	public List<ProductRelationship_> getProductRelationship() {
		return productRelationship;
	}

	public void setProductRelationship(List<ProductRelationship_> productRelationship) {
		this.productRelationship = productRelationship;
	}

	public List<ProductTerm_> getProductTerm() {
		return productTerm;
	}

	public void setProductTerm(List<ProductTerm_> productTerm) {
		this.productTerm = productTerm;
	}

	public List<RelatedParty_> getRelatedParty() {
		return relatedParty;
	}

	public void setRelatedParty(List<RelatedParty_> relatedParty) {
		this.relatedParty = relatedParty;
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

	@Override
	public String toString() {
		return "ProductInventoryMain [id=" + id + ", href=" + href + ", description=" + description + ", isBundle="
				+ isBundle + ", isCustomerVisible=" + isCustomerVisible + ", name=" + name + ", orderDate=" + orderDate
				+ ", startDate=" + startDate + ", terminationDate=" + terminationDate + ", place=" + place
				+ ", product=" + product + ", productOffering=" + productOffering + ", productPrice=" + productPrice
				+ ", productRelationship=" + productRelationship + ", productTerm=" + productTerm + ", relatedParty="
				+ relatedParty + ", status=" + status + ", baseType=" + baseType + ", schemaLocation=" + schemaLocation
				+ ", type=" + type + "]";
	}
	
	

}
