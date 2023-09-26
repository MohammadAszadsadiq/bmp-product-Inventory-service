package com.dtag.bm.service.product.inventory.service.service;



import java.util.List;

import org.springframework.stereotype.Service;

import com.dtag.bm.service.product.inventory.service.model.EventData;



@Service
public interface EventService {
	
	public EventData createNotification(EventData eventData);
	public String deleteEvent(String id);
	public List<EventData> getEvents();

}
