package com.dtag.bm.service.product.inventory.service.model;

public class RelationWithOfferCode {

	private String sliceOfferCodes;
	private String orderReferenceNumbers;

	public String getSliceOfferCodes() {
		return sliceOfferCodes;
	}

	public void setSliceOfferCodes(String sliceOfferCodes) {
		this.sliceOfferCodes = sliceOfferCodes;
	}

	public String getOrderReferenceNumbers() {
		return orderReferenceNumbers;
	}

	public void setOrderReferenceNumbers(String orderReferenceNumbers) {
		this.orderReferenceNumbers = orderReferenceNumbers;
	}

	@Override
	public String toString() {
		return "RelationWithOfferCode [sliceOfferCodes=" + sliceOfferCodes + ", orderReferenceNumbers="
				+ orderReferenceNumbers + "]";
	}
}
