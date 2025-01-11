package com.polytec.gestionevents.Services;

import com.polytec.gestionevents.Entities.Event;
import com.polytec.gestionevents.Repositories.EventRepository;
import lombok.AllArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@AllArgsConstructor
public class ServiceEvent implements IServiceEvent{
    @Autowired
    private EventRepository eventRepository;

    @Override
    public void addEvent(Event event) {
        eventRepository.save(event);
    }

    @Override
    public Event getEvent(Long id) {
        return eventRepository.findById(id).orElseThrow(() -> new RuntimeException("Event not found"));
    }

    @Override
    public List<Event> getAllEvents() {
        return eventRepository.findAll();
    }
    @Override
    public void updateEvent(Event event) {
        if (event.getId() != null && eventRepository.existsById(event.getId())) {
            eventRepository.save(event);
        } else {
            throw new RuntimeException("Event not found for update with ID: " + event.getId());
        }
    }


    @Override
    public void deleteEvent(Long id) {
        eventRepository.deleteById(id);
    }
}
