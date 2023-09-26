package com.dtag.bm.service.product.inventory.service.model;

import java.io.Serializable;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonPropertyOrder;
@JsonInclude(JsonInclude.Include.NON_NULL)
@JsonPropertyOrder({
	
	"event"
	
})

public class ProductInventoryNotification extends Notification implements Serializable{
	
	

	public ProductInventoryNotification(Object source, String eventId, String eventDate, String eventType,
			Product event) {
		super(source, eventId, eventDate, eventType);
		this.event = event;
	}

	/**
	 * 
	 */
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
		return "ProductInventoryNotification [event=" + event + "]";
	}
	
	

	
}
