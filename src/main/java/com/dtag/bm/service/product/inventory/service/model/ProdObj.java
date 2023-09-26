package com.dtag.bm.service.product.inventory.service.model;

import java.util.List;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonPropertyOrder;

@JsonInclude(JsonInclude.Include.NON_NULL)
@JsonPropertyOrder({ "prodId", "customerId", "offcerId" })
public class ProdObj {

	@JsonProperty("prodId")
	private List<String> prodId = null;
	@JsonProperty("customerId")
	private String customerId;
	@JsonProperty("offcerId")
	private String offcerId;

	@JsonProperty("prodId")
	public List<String> getProdId() {
		return prodId;
	}

	@JsonProperty("prodId")
	public void setProdId(List<String> prodId) {
		this.prodId = prodId;
	}

	@JsonProperty("customerId")
	public String getCustomerId() {
		return customerId;
	}

	@JsonProperty("customerId")
	public void setCustomerId(String customerId) {
		this.customerId = customerId;
	}

	@JsonProperty("offcerId")
	public String getOffcerId() {
		return offcerId;
	}

	@JsonProperty("offcerId")
	public void setOffcerId(String offcerId) {
		this.offcerId = offcerId;
	}

	@Override
	public String toString() {
		return "ProdObj [prodId=" + prodId + ", customerId=" + customerId + ", offcerId=" + offcerId + ", getProdId()="
				+ getProdId() + ", getCustomerId()=" + getCustomerId() + ", getOffcerId()=" + getOffcerId()
				+ ", getClass()=" + getClass() + ", hashCode()=" + hashCode() + ", toString()=" + super.toString()
				+ "]";
	}

}
