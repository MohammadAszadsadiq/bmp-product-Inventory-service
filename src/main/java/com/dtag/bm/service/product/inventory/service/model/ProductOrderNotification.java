package com.dtag.bm.service.product.inventory.service.model;

import java.io.Serializable;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;

@JsonInclude(JsonInclude.Include.NON_NULL)

public class ProductOrderNotification extends Notification implements Serializable {

	public ProductOrderNotification(Object source, String eventId, String eventDate, String eventType, Product event) {
		super(source, eventId, eventDate, eventType);
		this.event = event;
	}

	private static final long serialVersionUID = 8546385061810070867L;

	@JsonProperty("event")
	private Product event;

	public Product getEvent() {
		return event;
	}

	public void setEvent(Product event) {
		this.event = event;
	}

	public static long getSerialversionuid() {
		return serialVersionUID;
	}

	@Override
	public String toString() {
		return "ProductOrderNotification [event=" + event + "]";
	}

}
