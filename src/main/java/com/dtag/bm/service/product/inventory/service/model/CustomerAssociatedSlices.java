package com.dtag.bm.service.product.inventory.service.model;

public class CustomerAssociatedSlices {

	private String offerName;
	private Object sst;
	private Object sd="NULL";
	private Object type;
	private Object serviceId;


	public String getOfferName() {
		return offerName;
	}

	public void setOfferName(String offerName) {
		this.offerName = offerName;
	}

	public Object getSst() {
		return sst;
	}

	public void setSst(Object sst) {
		this.sst = sst;
	}

	public Object getSd() {
		return sd;
	}

	public void setSd(Object sd) {
		this.sd = sd;
	}

	public Object getType() {
		return type;
	}

	public void setType(Object type) {
		this.type = type;
	}

	public Object getServiceId() {
		return serviceId;
	}

	public void setServiceId(Object serviceId) {
		this.serviceId = serviceId;
	}

	@Override
	public String toString() {
		return "CustomerAssociatedSlices [offerName=" + offerName + ", sst=" + sst + ", sd=" + sd + ", type=" + type
				+ ", serviceId=" + serviceId + "]";
	}


}
