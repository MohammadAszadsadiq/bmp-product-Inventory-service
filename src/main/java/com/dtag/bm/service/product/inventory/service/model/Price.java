package com.dtag.bm.service.product.inventory.service.model;

import java.io.Serializable;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;

@JsonInclude(JsonInclude.Include.NON_NULL)

public class Price implements Serializable {

	private static final long serialVersionUID = -4188360700894077598L;

	@JsonProperty("taxIncludedAmount")

	private TaxIncludedAmount taxIncludedAmount;

	@JsonProperty("DutyFreeAmount")

	private DutyFreeAmount dutyFreeAmount;

	@JsonProperty("percentage")

	private float percentage;

	@JsonProperty("taxRate")

	private float taxRate;

	@JsonProperty("@type")

	private String type;

	@JsonProperty("@schemaLocation")

	private String schemaLocation;

	@JsonProperty("taxIncludedAmount")

	public TaxIncludedAmount getTaxIncludedAmount() {

		return taxIncludedAmount;

	}

	@JsonProperty("taxIncludedAmount")

	public void setTaxIncludedAmount(TaxIncludedAmount taxIncludedAmount) {

		this.taxIncludedAmount = taxIncludedAmount;

	}

	@JsonProperty("DutyFreeAmount")

	public DutyFreeAmount getDutyFreeAmount() {

		return dutyFreeAmount;

	}

	@JsonProperty("DutyFreeAmount")

	public void setDutyFreeAmount(DutyFreeAmount dutyFreeAmount) {

		this.dutyFreeAmount = dutyFreeAmount;

	}

	@JsonProperty("percentage")

	public float getPercentage() {

		return percentage;

	}

	@JsonProperty("percentage")

	public void setPercentage(float percentage) {

		this.percentage = percentage;

	}

	@JsonProperty("taxRate")

	public float getTaxRate() {

		return taxRate;

	}

	@JsonProperty("taxRate")

	public void setTaxRate(float taxRate) {

		this.taxRate = taxRate;

	}

	@JsonProperty("@type")

	public String getType() {

		return type;

	}

	@JsonProperty("@type")

	public void setType(String type) {

		this.type = type;

	}

	@JsonProperty("@schemaLocation")

	public String getSchemaLocation() {

		return schemaLocation;

	}

	@JsonProperty("@schemaLocation")

	public void setSchemaLocation(String schemaLocation) {

		this.schemaLocation = schemaLocation;

	}

	@Override

	public String toString() {

		return "Price [taxIncludedAmount=" + taxIncludedAmount + ", dutyFreeAmount=" + dutyFreeAmount + ", percentage="

				+ percentage + ", taxRate=" + taxRate + ", type=" + type + ", schemaLocation=" + schemaLocation + "]";

	}
}