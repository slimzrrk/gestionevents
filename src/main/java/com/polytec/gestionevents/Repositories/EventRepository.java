package com.polytec.gestionevents.Repositories;

import com.polytec.gestionevents.Entities.Event;
import org.springframework.data.jpa.repository.JpaRepository;


public interface EventRepository extends JpaRepository<Event, Long> {
}
