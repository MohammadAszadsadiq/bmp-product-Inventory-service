package com.dtag.bm.service.product.inventory.service.model;

import java.io.Serializable;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;

@JsonInclude(JsonInclude.Include.NON_NULL)

public class ValidFor implements Serializable {

	private static final long serialVersionUID = 7124989255068577226L;

	@JsonProperty("startDateTime")
	private String startDateTime;
	@JsonProperty("endDateTime")
	private String endDateTime;

	@JsonProperty("startDateTime")
	public String getStartDateTime() {
		return startDateTime;
	}

	@JsonProperty("startDateTime")
	public void setStartDateTime(String startDateTime) {
		this.startDateTime = startDateTime;
	}

	@JsonProperty("endDateTime")
	public String getEndDateTime() {
		return endDateTime;
	}

	@JsonProperty("endDateTime")
	public void setEndDateTime(String endDateTime) {
		this.endDateTime = endDateTime;
	}

}