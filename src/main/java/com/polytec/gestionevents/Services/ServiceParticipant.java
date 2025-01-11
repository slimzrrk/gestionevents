package com.polytec.gestionevents.Services;

import com.polytec.gestionevents.Entities.Participant;
import com.polytec.gestionevents.Repositories.ParticipantRepository;
import lombok.AllArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.List;

@AllArgsConstructor
@Service
public class ServiceParticipant implements IServiceParticipant {

    @Autowired
    private ParticipantRepository participantRepository;

    @Autowired
    private PasswordEncoder passwordEncoder; // Injection du PasswordEncoder pour le hachage des mots de passe

    @Override
    public void addParticipant(Participant participant) {
        // Vérifier et hacher le mot de passe avant la sauvegarde
        if (participant.getPassword() != null && !participant.getPassword().isEmpty()) {
            String hashedPassword = passwordEncoder.encode(participant.getPassword());
            participant.setPassword(hashedPassword);
        }
        System.out.println("Participant avant sauvegarde : " + participant);
        participantRepository.save(participant);
    }

    @Override
    public Participant getParticipant(Long id) {
        return participantRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Participant not found for ID: " + id));
    }

    @Override
    public List<Participant> getAllParticipants() {
        return participantRepository.findAll();
    }

    @Override
    public void updateParticipant(Participant participant) {
        if (participant.getId() != null && participantRepository.existsById(participant.getId())) {
            // Hachage du mot de passe seulement s'il est fourni et mis à jour
            if (participant.getPassword() != null && !participant.getPassword().isEmpty()) {
                String hashedPassword = passwordEncoder.encode(participant.getPassword());
                participant.setPassword(hashedPassword);
            }
            System.out.println("Participant mis à jour : " + participant);
            participantRepository.save(participant);
        } else {
            throw new RuntimeException("Participant not found for update with ID: " + participant.getId());
        }
    }

    @Override
    public void deleteParticipant(Long id) {
        if (!participantRepository.existsById(id)) {
            throw new RuntimeException("Participant not found for deletion with ID: " + id);
        }
        participantRepository.deleteById(id);
    }
}
