package com.squasage.alarm.service;

import com.squasage.alarm.model.Event;
import com.squasage.alarm.model.EventType;
import com.squasage.alarm.repository.EventRepository;
import org.springframework.stereotype.Service;

@Service
public class EventService {

    private final EventRepository eventRepository;

    public EventService(EventRepository eventRepository) {
        this.eventRepository = eventRepository;
    }

    public void publishEvent(EventType eventType) {
        publishEvent(eventType, null, null);
    }

    public void publishEvent(EventType eventType, String location, String notes) {
        Event event = new Event();
        event.setEventType(eventType);
        event.setLocation(location);
        event.setNotes(notes);

        eventRepository.save(event);
    }


}
