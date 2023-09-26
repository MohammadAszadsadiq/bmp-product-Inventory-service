package com.dtag.bm.service.product.inventory.service.model;

import java.util.List;

public class Phenomresponse {

	private String ProductInstanceId;
	private String orderId;
	private String orderDate;
	private String customerId;
	private String customerName;
	private String offerName;
	private String offerCode;
	private String status;
	private String subscriptionDate;
	private Object plmn;
	private Object imsi;
	private Object sd;
	private Object sst;
	private Object type;
	private List<Phenomresponse> relatedProductInventory;
	private String parentNotification;
	public String getProductInstanceId() {
		return ProductInstanceId;
	}
	public void setProductInstanceId(String productInstanceId) {
		ProductInstanceId = productInstanceId;
	}
	public String getOrderId() {
		return orderId;
	}
	public void setOrderId(String orderId) {
		this.orderId = orderId;
	}
	public String getOrderDate() {
		return orderDate;
	}
	public void setOrderDate(String orderDate) {
		this.orderDate = orderDate;
	}
	public String getCustomerId() {
		return customerId;
	}
	public void setCustomerId(String customerId) {
		this.customerId = customerId;
	}
	public String getCustomerName() {
		return customerName;
	}
	public void setCustomerName(String customerName) {
		this.customerName = customerName;
	}
	public String getOfferName() {
		return offerName;
	}
	public void setOfferName(String offerName) {
		this.offerName = offerName;
	}
	public String getOfferCode() {
		return offerCode;
	}
	public void setOfferCode(String offerCode) {
		this.offerCode = offerCode;
	}
	public String getStatus() {
		return status;
	}
	public void setStatus(String status) {
		this.status = status;
	}
	public String getSubscriptionDate() {
		return subscriptionDate;
	}
	public void setSubscriptionDate(String subscriptionDate) {
		this.subscriptionDate = subscriptionDate;
	}
	public Object getPlmn() {
		return plmn;
	}
	public void setPlmn(Object plmn) {
		this.plmn = plmn;
	}
	public Object getImsi() {
		return imsi;
	}
	public void setImsi(Object imsi) {
		this.imsi = imsi;
	}
	public Object getSd() {
		return sd;
	}
	public void setSd(Object sd) {
		this.sd = sd;
	}
	public Object getSst() {
		return sst;
	}
	public void setSst(Object sst) {
		this.sst = sst;
	}
	public Object getType() {
		return type;
	}
	public void setType(Object type) {
		this.type = type;
	}
	public List<Phenomresponse> getRelatedProductInventory() {
		return relatedProductInventory;
	}
	public void setRelatedProductInventory(List<Phenomresponse> relatedProductInventory) {
		this.relatedProductInventory = relatedProductInventory;
	}
	public String getParentNotification() {
		return parentNotification;
	}
	public void setParentNotification(String parentNotification) {
		this.parentNotification = parentNotification;
	}
	@Override
	public String toString() {
		return "Phenomresponse [ProductInstanceId=" + ProductInstanceId + ", orderId=" + orderId + ", orderDate="
				+ orderDate + ", customerId=" + customerId + ", customerName=" + customerName + ", offerName="
				+ offerName + ", offerCode=" + offerCode + ", status=" + status + ", subscriptionDate="
				+ subscriptionDate + ", plmn=" + plmn + ", imsi=" + imsi + ", sd=" + sd + ", sst=" + sst + ", type="
				+ type + ", relatedProductInventory=" + relatedProductInventory + ", parentNotification="
				+ parentNotification + "]";
	}
	
	
	
}
