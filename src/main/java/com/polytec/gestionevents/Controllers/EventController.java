package com.polytec.gestionevents.Controllers;

import com.polytec.gestionevents.Entities.Event;
import com.polytec.gestionevents.Entities.Participant;
import com.polytec.gestionevents.Entities.Venue;
import com.polytec.gestionevents.Services.IServiceEvent;
import com.polytec.gestionevents.Services.ServiceParticipant;
import com.polytec.gestionevents.Services.ServiceVenue;
import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/api/events")
public class EventController {

    private final IServiceEvent service;
    private final ServiceVenue serviceVenue;
    private final ServiceParticipant serviceParticipant;

    // Constructor for dependency injection
    public EventController(IServiceEvent service, ServiceVenue serviceVenue, ServiceParticipant serviceParticipant) {
        this.service = service;
        this.serviceVenue = serviceVenue;
        this.serviceParticipant = serviceParticipant;
    }

    @PostMapping
    public ResponseEntity<String> addEvent(@Valid @RequestBody Event event) {
        System.out.println("Event received: " + event);
        service.addEvent(event);
        return ResponseEntity.ok("Event added successfully!");
    }

    @GetMapping
    public List<Event> getAllEvents() {
        return service.getAllEvents();
    }

    @GetMapping("/{id}")
    public ResponseEntity<Event> getEvent(@PathVariable Long id) {
        Event event = service.getEvent(id);
        if (event != null) {
            return ResponseEntity.ok(event);
        } else {
            return ResponseEntity.status(404).body(null);
        }
    }

    @PutMapping("/{id}")
    public ResponseEntity<String> updateEvent(@PathVariable Long id, @RequestBody Event updatedEvent) {
        // Vérifiez si l'événement existe
        Event existingEvent = service.getEvent(id);
        if (existingEvent != null) {
            // Mise à jour des champs uniquement s'ils sont fournis
            if (updatedEvent.getName() != null && !updatedEvent.getName().isEmpty()) {
                existingEvent.setName(updatedEvent.getName());
            }

            if (updatedEvent.getDate() != null) {
                existingEvent.setDate(updatedEvent.getDate());
            }

            if (updatedEvent.getVenue() != null) {
                // Vérifier si un ID est fourni pour le Venue
                if (updatedEvent.getVenue().getId() != null) {
                    Venue venue = serviceVenue.getVenue(updatedEvent.getVenue().getId());
                    if (venue != null) {
                        // Associer le Venue existant
                        existingEvent.setVenue(venue);
                    } else {
                        // Venue avec ID non valide
                        return ResponseEntity.status(400).body("Invalid Venue ID provided.");
                    }
                } else {
                    // Ignorer les nouveaux Venue sans ID
                    return ResponseEntity.status(400).body("Venue ID is required to associate a Venue.");
                }
            }


            // Mise à jour conditionnelle des participants en utilisant uniquement les ID existants
            if (updatedEvent.getParticipants() != null && !updatedEvent.getParticipants().isEmpty()) {
                List<Participant> validParticipants = updatedEvent.getParticipants().stream()
                        .map(participant -> {
                            if (participant.getId() != null) {
                                return serviceParticipant.getParticipant(participant.getId());
                            }
                            return null; // Ignore si l'ID n'est pas fourni
                        })
                        .filter(Objects::nonNull) // Filtre les participants nuls
                        .collect(Collectors.toList());

                // Ajouter les participants existants uniquement
                existingEvent.setParticipants(validParticipants);
            }
else return null;
            // Sauvegarde de l'événement mis à jour
            service.updateEvent(existingEvent);
            return ResponseEntity.ok("Event updated successfully.");
        } else {
            return ResponseEntity.status(404).body("Event not found for ID: " + id);
        }
    }


    @DeleteMapping("/{id}")
    public ResponseEntity<String> deleteEvent(@PathVariable Long id) {
        try {
            service.deleteEvent(id);
            return ResponseEntity.ok("Event deleted successfully.");
        } catch (Exception e) {
            return ResponseEntity.status(500).body("Failed to delete event: " + e.getMessage());
        }
    }
}
