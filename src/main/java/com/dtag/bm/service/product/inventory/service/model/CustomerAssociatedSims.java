package com.dtag.bm.service.product.inventory.service.model;

import java.util.Set;

public class CustomerAssociatedSims {

	private String custId;
	private Set<Object> sims;

	public Set<Object> getSims() {
		return sims;
	}

	public void setSims(Set<Object> sims) {
		this.sims = sims;
	}

	public String getCustId() {
		return custId;
	}

	public void setCustId(String custId) {
		this.custId = custId;
	}

	@Override
	public String toString() {
		return "CustomerAssociatedSims [custId=" + custId + ", sims=" + sims + "]";
	}

}
