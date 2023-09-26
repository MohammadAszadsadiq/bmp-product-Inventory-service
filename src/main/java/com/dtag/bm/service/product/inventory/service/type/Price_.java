package com.dtag.bm.service.product.inventory.service.type;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
@JsonInclude(JsonInclude.Include.NON_NULL)
public class Price_ {
	
	@JsonProperty("dutyFreeAmount")
	private String dutyFreeAmount;
	@JsonProperty("taxIncludedAmount")
	private com.dtag.bm.service.product.inventory.service.type.DutyFreeAmount_ taxIncludedAmount;
	@JsonProperty("@baseType")
	private String baseType;
	@JsonProperty("@schemaLocation")
	private String schemaLocation;
	@JsonProperty("@type")
	private String type;
	
	
	public String getDutyFreeAmount() {
		return dutyFreeAmount;
	}
	public void setDutyFreeAmount(String dutyFreeAmount) {
		this.dutyFreeAmount = dutyFreeAmount;
	}
	public com.dtag.bm.service.product.inventory.service.type.DutyFreeAmount_ getTaxIncludedAmount() {
		return taxIncludedAmount;
	}
	public void setTaxIncludedAmount(com.dtag.bm.service.product.inventory.service.type.DutyFreeAmount_ taxIncludedAmount) {
		this.taxIncludedAmount = taxIncludedAmount;
	}
	public String getBaseType() {
		return baseType;
	}
	public void setBaseType(String baseType) {
		this.baseType = baseType;
	}
	public String getSchemaLocation() {
		return schemaLocation;
	}
	public void setSchemaLocation(String schemaLocation) {
		this.schemaLocation = schemaLocation;
	}
	public String getType() {
		return type;
	}
	public void setType(String type) {
		this.type = type;
	}
	@Override
	public String toString() {
		return "Price_ [dutyFreeAmount=" + dutyFreeAmount + ", taxIncludedAmount=" + taxIncludedAmount + ", baseType="
				+ baseType + ", schemaLocation=" + schemaLocation + ", type=" + type + "]";
	}

}
