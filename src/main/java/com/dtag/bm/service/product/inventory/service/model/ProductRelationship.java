package com.dtag.bm.service.product.inventory.service.model;

import java.io.Serializable;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;

@JsonInclude(JsonInclude.Include.NON_NULL)

public class ProductRelationship implements Serializable {

	private static final long serialVersionUID = 6976365449708153691L;

	@JsonProperty("type")
	private String type;
	@JsonProperty("productRef")
	private ProductRef productRef;

	@JsonProperty("type")
	public String getType() {
		return type;
	}

	@JsonProperty("type")
	public void setType(String type) {
		this.type = type;
	}

	@JsonProperty("productRef")
	public ProductRef getProductRef() {
		return productRef;
	}

	@JsonProperty("productRef")
	public void setProductRef(ProductRef productRef) {
		this.productRef = productRef;
	}

}