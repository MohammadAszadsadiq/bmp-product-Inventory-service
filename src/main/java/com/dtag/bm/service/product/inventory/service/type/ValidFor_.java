package com.dtag.bm.service.product.inventory.service.type;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
@JsonInclude(JsonInclude.Include.NON_NULL)
public class ValidFor_ {
	@JsonProperty("endDateTime")
	private String endDateTime;
	@JsonProperty("startDateTime")
	private String startDateTime;
	public String getEndDateTime() {
		return endDateTime;
	}
	public void setEndDateTime(String endDateTime) {
		this.endDateTime = endDateTime;
	}
	public String getStartDateTime() {
		return startDateTime;
	}
	public void setStartDateTime(String startDateTime) {
		this.startDateTime = startDateTime;
	}
	@Override
	public String toString() {
		return "ValidFor [endDateTime=" + endDateTime + ", startDateTime=" + startDateTime + "]";
	}
	
	

}
