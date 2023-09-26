package com.dtag.bm.service.product.inventory.service.model;

import java.io.Serializable;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;

@JsonInclude(JsonInclude.Include.NON_NULL)

public class Duration implements Serializable {

	private static final long serialVersionUID = 2958983733942509111L;

	@JsonProperty("amount")
	private Integer amount;
	@JsonProperty("units")
	private String units;

	@JsonProperty("amount")
	public Integer getAmount() {
		return amount;
	}

	@JsonProperty("amount")
	public void setAmount(Integer amount) {
		this.amount = amount;
	}

	@JsonProperty("units")
	public String getUnits() {
		return units;
	}

	@JsonProperty("units")
	public void setUnits(String units) {
		this.units = units;
	}

}