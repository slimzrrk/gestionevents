package com.polytec.gestionevents.Repositories;

import com.polytec.gestionevents.Entities.Participant;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ParticipantRepository extends JpaRepository<Participant, Long> {
}
