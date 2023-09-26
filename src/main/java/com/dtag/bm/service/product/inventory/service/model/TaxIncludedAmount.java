package com.dtag.bm.service.product.inventory.service.model;

import java.io.Serializable;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;

@JsonInclude(JsonInclude.Include.NON_NULL)

public class TaxIncludedAmount implements Serializable {

	private static final long serialVersionUID = -365790394605560556L;

	@JsonProperty("value")
	private float value;
	@JsonProperty("unit")
	private String unit;

	@JsonProperty("value")
	public float getValue() {
		return value;
	}

	@JsonProperty("value")
	public void setValue(float value) {
		this.value = value;
	}

	@JsonProperty("unit")
	public String getUnit() {
		return unit;
	}

	@JsonProperty("unit")
	public void setUnit(String unit) {
		this.unit = unit;
	}

}