package com.polytec.gestionevents.Controllers;

import com.polytec.gestionevents.Entities.Participant;
import com.polytec.gestionevents.Services.IServiceParticipant;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/participants")
@RequiredArgsConstructor
public class ParticipantController {
    @Autowired
    private IServiceParticipant participantService;

    @PostMapping
    public ResponseEntity<String> addParticipant(@RequestBody Participant participant) {
        System.out.println("Received Participant: " + participant);
        participantService.addParticipant(participant);
        return ResponseEntity.status(HttpStatus.CREATED).body("Participant added successfully!");
    }

    @GetMapping
    public List<Participant> getAllParticipants() {
        return participantService.getAllParticipants();
    }

    @GetMapping("/{id}")
    public Participant getParticipant(@PathVariable Long id) {
        return participantService.getParticipant(id);
    }

    @PutMapping("/{id}")
    public ResponseEntity<String> updateParticipant(@PathVariable Long id, @RequestBody Participant updatedParticipant) {
        // Récupérer le participant existant
        Participant existingParticipant = participantService.getParticipant(id);
        if (existingParticipant != null) {
            // Mise à jour des champs
            existingParticipant.setName(updatedParticipant.getName());
            existingParticipant.setEmail(updatedParticipant.getEmail());
            existingParticipant.setPassword(updatedParticipant.getPassword());
            existingParticipant.setEvents(updatedParticipant.getEvents());

            // Sauvegarder le participant mis à jour
            participantService.updateParticipant(existingParticipant);
            return ResponseEntity.ok("Participant updated successfully.");
        } else {
            return ResponseEntity.status(404).body("Participant not found for ID: " + id);
        }
    }


    @DeleteMapping("/{id}")
    public void deleteParticipant(@PathVariable Long id) {
        participantService.deleteParticipant(id);
    }
}
