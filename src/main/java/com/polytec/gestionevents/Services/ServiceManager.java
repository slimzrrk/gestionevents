package com.polytec.gestionevents.Services;

import com.polytec.gestionevents.Entities.Manager;
import com.polytec.gestionevents.Repositories.ManagerRepository;
import lombok.AllArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@AllArgsConstructor
@Service
public class ServiceManager implements IServiceManager{
    @Autowired
    private ManagerRepository managerRepository;

    @Override
    public void addManager(Manager manager) {
        managerRepository.save(manager);
    }

    @Override
    public Manager getManager(Long id) {
        return managerRepository.findById(id).orElseThrow(() -> new RuntimeException("Manager not found"));
    }

    @Override
    public List<Manager> getAllManagers() {
        return managerRepository.findAll();
    }

    @Override
    public void updateManager(Manager manager) {
        if (manager.getId() != null && managerRepository.existsById(manager.getId())) {
            managerRepository.save(manager);
        } else {
            throw new RuntimeException("Manager not found for update with ID: " + manager.getId());
        }
    }


    @Override
    public void deleteManager(Long id) {
        managerRepository.deleteById(id);
    }
}
