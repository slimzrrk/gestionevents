package com.polytec.gestionevents.Services;

import com.polytec.gestionevents.Entities.Venue;

import java.util.List;

public interface IServiceVenue {
    void addVenue(Venue venue);
    Venue getVenue(Long id);
    List<Venue> getAllVenues();
    void updateVenue(Venue venue);
    void deleteVenue(Long id);
}
