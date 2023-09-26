package com.dtag.bm.service.product.inventory.service.model;

import org.springframework.data.mongodb.core.mapping.Document;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonPropertyOrder;

import io.swagger.annotations.ApiModelProperty;

@JsonInclude(JsonInclude.Include.NON_NULL)
@JsonPropertyOrder({ "id", "callback", "query" })

@Document(collection = "HubEventNotification")
public class EventData {

	@JsonProperty("callback")
	private String callback;

	@JsonProperty("query")
	private String query;

	@JsonProperty("id")
	private String id;

	public String getCallback() {
		return callback;
	}

	public void setCallback(String callback) {
		this.callback = callback;
	}

	public String getQuery() {
		return query;
	}

	public void setQuery(String query) {
		this.query = query;
	}

	@ApiModelProperty(hidden = true)
	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	@Override
	public String toString() {
		return "EventData [callback=" + callback + ", query=" + query + ", id=" + id + "]";
	}
}
