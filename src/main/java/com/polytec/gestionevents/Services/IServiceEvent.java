package com.polytec.gestionevents.Services;

import com.polytec.gestionevents.Entities.Event;

import java.util.List;

public interface IServiceEvent {
    void addEvent(Event event);
    Event getEvent(Long id);
    List<Event> getAllEvents();
    void updateEvent(Event event);
    void deleteEvent(Long id);
}
