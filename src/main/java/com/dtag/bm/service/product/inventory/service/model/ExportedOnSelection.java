package com.dtag.bm.service.product.inventory.service.model;
import java.util.List;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonPropertyOrder;

@JsonInclude(JsonInclude.Include.NON_NULL)
@JsonPropertyOrder({ "imsi"})

public class ExportedOnSelection {
	
	
	@JsonProperty("imsi")
	private List<String> imsi = null;

	public List<String> getImsi() {
		return imsi;
	}

	public void setImsi(List<String> imsi) {
		this.imsi = imsi;
	}

}
