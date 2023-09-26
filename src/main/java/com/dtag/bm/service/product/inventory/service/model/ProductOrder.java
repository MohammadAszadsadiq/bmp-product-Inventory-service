package com.dtag.bm.service.product.inventory.service.model;

import java.io.Serializable;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;

@JsonInclude(JsonInclude.Include.NON_NULL)

public class ProductOrder implements Serializable {

	private static final long serialVersionUID = 5654979305364025427L;

	@JsonProperty("_id")
	private String id;
	@JsonProperty("href")
	private String href;
	@JsonProperty("referredType")
	private String referredType;
	@JsonProperty("orderItemId")
	private String orderItemId;
	@JsonProperty("orderItemAction")
	private String orderItemAction;
	@JsonProperty("orderDate")
	private String orderDate;
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
	public String getReferredType() {
		return referredType;
	}
	public void setReferredType(String referredType) {
		this.referredType = referredType;
	}
	public String getOrderItemId() {
		return orderItemId;
	}
	public void setOrderItemId(String orderItemId) {
		this.orderItemId = orderItemId;
	}
	public String getOrderItemAction() {
		return orderItemAction;
	}
	public void setOrderItemAction(String orderItemAction) {
		this.orderItemAction = orderItemAction;
	}
	public String getOrderDate() {
		return orderDate;
	}
	public void setOrderDate(String orderDate) {
		this.orderDate = orderDate;
	}
	@Override
	public String toString() {
		return "ProductOrder [id=" + id + ", href=" + href + ", referredType=" + referredType + ", orderItemId="
				+ orderItemId + ", orderItemAction=" + orderItemAction + ", orderDate=" + orderDate + "]";
	}

	

}