package com.dtag.bm.service.product.inventory.service.model;

import java.util.List;

public class CheckOfferDetailsRequest {

	private String custId;
	private String productOfferingCode;
	private List<RelationWithOfferCode> relation;

	public String getCustId() {
		return custId;
	}

	public void setCustId(String custId) {
		this.custId = custId;
	}

	public String getProductOfferingCode() {
		return productOfferingCode;
	}

	public void setProductOfferingCode(String productOfferingCode) {
		this.productOfferingCode = productOfferingCode;
	}

	public List<RelationWithOfferCode> getRelation() {
		return relation;
	}

	public void setRelation(List<RelationWithOfferCode> relation) {
		this.relation = relation;
	}

	@Override
	public String toString() {
		return "CheckOfferDetailsRequest [custId=" + custId + ", productOfferingCode=" + productOfferingCode
				+ ", relation=" + relation + "]";
	}

}
