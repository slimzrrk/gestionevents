package com.polytec.gestionevents.Repositories;

import com.polytec.gestionevents.Entities.Venue;
import org.springframework.data.jpa.repository.JpaRepository;


public interface VenueRepository extends JpaRepository<Venue, Long> {
}
