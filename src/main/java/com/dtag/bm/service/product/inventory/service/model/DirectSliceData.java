package com.dtag.bm.service.product.inventory.service.model;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;


@JsonInclude(JsonInclude.Include.NON_NULL)
public class DirectSliceData {
	
	@JsonProperty("sst")
	private String sst;
	@JsonProperty("sd")
	private String sd;
	@JsonProperty("PLMN")
	private String plmn;
	@JsonProperty("dnnList")
	private String dnnList = "NA";
	
	
	public String getSst() {
		return sst;
	}
	public void setSst(String sst) {
		this.sst = sst;
	}
	public String getSd() {
		return sd;
	}
	public void setSd(String sd) {
		this.sd = sd;
	}
	
	public String getPlmn() {
		return plmn;
	}
	public void setPlmn(String plmn) {
		this.plmn = plmn;
	}
	public String getDnnList() {
		return dnnList;
	}
	public void setDnnList(String dnnList) {
		this.dnnList = dnnList;
	}
	@Override
	public String toString() {
		return "DirectSliceData [sst=" + sst + ", sd=" + sd + ", plmn=" + plmn + ", dnnList=" + dnnList + "]";
	}
	
	
}
