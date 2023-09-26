package com.dtag.bm.service.product.inventory.service.model;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;

@JsonInclude(JsonInclude.Include.NON_NULL)
@Document(collection = "ProductInventoryCSVDetails")
public class ProductCsv {

	private static final long serialVersionUID = 5654979305364025427L;

	@Id
	@JsonProperty("id")
	private String id;
	@JsonProperty("OMO_identifier")
	private String oMOidentifier;
	@JsonProperty("Priority")
	private String priority;
	@JsonProperty("OMO_description")
	private String OMOdescription;
	@JsonProperty("start_date")
	private String startDate;
	@JsonProperty("Maximum_latency")
	private String maximumLatency;
	@JsonProperty("Minimum_Bitrate_Uplink")
	private String MinimumBitrateUplink;
	@JsonProperty("Minimum_Bitrate_Downlink")
	private String MinimumBitrateDownlink;
	@JsonProperty("modified_date")
	private String modifiedDate;
	@JsonProperty("status")
	private String Status;
	@JsonProperty("5QI")
	private String QI;
	@JsonProperty("QCI")
	private String QCI;
	@JsonProperty("Price")
	private String price;

	@JsonProperty("OMO_description")
	public String getOMOdescription() {
		return OMOdescription;
	}

	@JsonProperty("OMO_description")
	public void setOMOdescription(String oMOdescription) {
		OMOdescription = oMOdescription;
	}

	@JsonProperty("start_date")
	public String getStartDate() {
		return startDate;
	}

	@JsonProperty("start_date")
	public String setStartDate(String startDate) {
		return this.startDate = startDate;
	}

	@JsonProperty("Maximum_latency")
	public String getMaximumLatency() {
		return maximumLatency;
	}

	@JsonProperty("Maximum_latency")
	public void setMaximumLatency(String maximumLatency) {
		this.maximumLatency = maximumLatency;
	}

	@JsonProperty("Minimum_Bitrate_Uplink")
	public String getMinimumBitrateUplink() {
		return MinimumBitrateUplink;
	}

	@JsonProperty("Minimum_Bitrate_Uplink")
	public void setMinimumBitrateUplink(String minimumBitrateUplink) {
		MinimumBitrateUplink = minimumBitrateUplink;
	}

	@JsonProperty("Minimum_Bitrate_Downlink")
	public String getMinimumBitrateDownlink() {
		return MinimumBitrateDownlink;
	}

	@JsonProperty("Minimum_Bitrate_Downlink")
	public void setMinimumBitrateDownlink(String minimumBitrateDownlink) {
		MinimumBitrateDownlink = minimumBitrateDownlink;
	}

	@JsonProperty("modified_date")
	public String getModifiedDate() {
		return modifiedDate;
	}

	@JsonProperty("modified_date")
	public void setModifiedDate(String modifiedDate) {
		this.modifiedDate = modifiedDate;
	}

	@JsonProperty("status")
	public String getStatus() {
		return Status;
	}

	@JsonProperty("status")
	public void setStatus(String status) {
		Status = status;
	}

	@JsonProperty("5QI")
	public String getQI() {
		return QI;
	}

	@JsonProperty("5QI")
	public void setQI(String qI) {
		QI = qI;
	}

	@JsonProperty("QCI")
	public String getQCI() {
		return QCI;
	}

	@JsonProperty("QCI")
	public void setQCI(String qCI) {
		QCI = qCI;
	}

	public static long getSerialversionuid() {
		return serialVersionUID;
	}

	public String getoMOidentifier() {
		return oMOidentifier;
	}

	public void setoMOidentifier(String oMOidentifier) {
		this.oMOidentifier = oMOidentifier;
	}

	public String getPriority() {
		return priority;
	}

	public void setPriority(String priority) {
		this.priority = priority;
	}

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	
	
	public String getPrice() {
		return price;
	}

	public void setPrice(String price) {
		this.price = price;
	}

	@Override
	public String toString() {
		return "ProductCsv [id=" + id + ", oMOidentifier=" + oMOidentifier + ", priority=" + priority
				+ ", OMOdescription=" + OMOdescription + ", startDate=" + startDate + ", maximumLatency="
				+ maximumLatency + ", MinimumBitrateUplink=" + MinimumBitrateUplink + ", MinimumBitrateDownlink="
				+ MinimumBitrateDownlink + ", modifiedDate=" + modifiedDate + ", Status=" + Status + ", QI=" + QI
				+ ", QCI=" + QCI + ", price=" + price + "]";
	}

}