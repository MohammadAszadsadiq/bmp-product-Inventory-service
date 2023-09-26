package com.dtag.bm.service.product.inventory.service.model;
import java.util.List;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;

@JsonInclude(JsonInclude.Include.NON_NULL)

public class UpdateTerminatedStatusRequest {
	
	private static final long serialVersionUID = -2181525571815285129L;
	
	@JsonProperty("ids")
	private List<String> ids = null;
	
	@JsonProperty("Status")
	private String Status;

	public static long getSerialversionuid() {
		return serialVersionUID;
	}

	public List<String> getIds() {
		return ids;
	}

	public String getStatus() {
		return Status;
	}

	public void setIds(List<String> ids) {
		this.ids = ids;
	}

	public void setStatus(String status) {
		Status = status;
	}

}
