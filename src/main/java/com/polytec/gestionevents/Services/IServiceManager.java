package com.polytec.gestionevents.Services;

import com.polytec.gestionevents.Entities.Manager;

import java.util.List;

public interface IServiceManager {
    void addManager(Manager manager);
    Manager getManager(Long id);
    List<Manager> getAllManagers();
    void updateManager(Manager manager);
    void deleteManager(Long id);
}