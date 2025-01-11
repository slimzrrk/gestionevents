package com.polytec.gestionevents.Repositories;

import com.polytec.gestionevents.Entities.Manager;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ManagerRepository extends JpaRepository<Manager, Long> {
}
