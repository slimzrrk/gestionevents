package com.polytec.gestionevents.Controllers;

import com.polytec.gestionevents.Entities.Manager;
import com.polytec.gestionevents.Services.IServiceManager;
import lombok.AllArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/managers")
@AllArgsConstructor
public class ManagerController {
    @Autowired
    private IServiceManager managerService;
    @PostMapping
    public ResponseEntity<String> addManager(@RequestBody Manager manager) {
        managerService.addManager(manager);
        return ResponseEntity.ok("Manager added successfully!");
    }


    @GetMapping
    public List<Manager> getAllManagers() {
        return managerService.getAllManagers();
    }

    @GetMapping("/{id}")
    public Manager getManager(@PathVariable Long id) {
        return managerService.getManager(id);
    }
    @PutMapping("/{id}")
    public ResponseEntity<String> updateManager(@PathVariable Long id, @RequestBody Manager updatedManager) {
        // Récupérer le manager existant
        Manager existingManager = managerService.getManager(id);
        if (existingManager != null) {
            // Mise à jour des champs
            existingManager.setName(updatedManager.getName());
            existingManager.setEmail(updatedManager.getEmail());
            existingManager.setVenue(updatedManager.getVenue());

            // Sauvegarder le manager mis à jour
            managerService.updateManager(existingManager);
            return ResponseEntity.ok("Manager updated successfully.");
        } else {
            return ResponseEntity.status(404).body("Manager not found for ID: " + id);
        }
    }


    @DeleteMapping("/{id}")
    public void deleteManager(@PathVariable Long id) {
        managerService.deleteManager(id);
    }
}
