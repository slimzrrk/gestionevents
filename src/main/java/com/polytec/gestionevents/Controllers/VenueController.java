package com.polytec.gestionevents.Controllers;

import com.polytec.gestionevents.Entities.Venue;
import com.polytec.gestionevents.Services.IServiceVenue;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/venues")
@RequiredArgsConstructor
public class VenueController {
    @Autowired
    private  IServiceVenue venueService;

    @PostMapping
    public void addVenue(@RequestBody Venue venue) {
        venueService.addVenue(venue);
    }

    @GetMapping
    public List<Venue> getAllVenues() {
        return venueService.getAllVenues();
    }

    @GetMapping("/{id}")
    public Venue getVenue(@PathVariable Long id) {
        return venueService.getVenue(id);
    }
    @PutMapping("/{id}")
    public ResponseEntity<String> updateVenue(@PathVariable Long id, @RequestBody Venue updatedVenue) {
        // Récupérer le venue existant
        Venue existingVenue = venueService.getVenue(id);

        if (existingVenue != null) {
            // Mise à jour des champs uniquement s'ils sont fournis
            if (updatedVenue.getName() != null && !updatedVenue.getName().isEmpty()) {
                existingVenue.setName(updatedVenue.getName());
            }

            if (updatedVenue.getLocation() != null && !updatedVenue.getLocation().isEmpty()) {
                existingVenue.setLocation(updatedVenue.getLocation());
            }

            // Mise à jour ou ajout conditionnel du manager
            if (updatedVenue.getManager() != null) {
                if (existingVenue.getManager() == null) {
                    // Ajouter le manager s'il n'existe pas
                    existingVenue.setManager(updatedVenue.getManager());
                } else {
                    // Mettre à jour les informations du manager existant
                    existingVenue.getManager().setName(updatedVenue.getManager().getName());
                    existingVenue.getManager().setEmail(updatedVenue.getManager().getEmail());
                }
            }

            // Mise à jour des événements si fournis
            if (updatedVenue.getEvents() != null && !updatedVenue.getEvents().isEmpty()) {
                existingVenue.setEvents(updatedVenue.getEvents());
            }

            // Sauvegarder le venue mis à jour
            venueService.updateVenue(existingVenue);

            return ResponseEntity.ok("Venue updated successfully.");
        } else {
            return ResponseEntity.status(404).body("Venue not found for ID: " + id);
        }
    }



    @DeleteMapping("/{id}")
    public void deleteVenue(@PathVariable Long id) {
        venueService.deleteVenue(id);
    }
}
