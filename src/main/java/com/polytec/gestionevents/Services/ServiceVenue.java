package com.polytec.gestionevents.Services;

import com.polytec.gestionevents.Entities.Event;
import com.polytec.gestionevents.Entities.Venue;
import com.polytec.gestionevents.Repositories.EventRepository;
import com.polytec.gestionevents.Repositories.VenueRepository;
import lombok.AllArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@AllArgsConstructor
@Service
public class ServiceVenue implements IServiceVenue{
    @Autowired
    private VenueRepository venueRepository;
    private EventRepository eventRepository;

    @Override
    public void addVenue(Venue venue) {
        venueRepository.save(venue);
    }

    @Override
    public Venue getVenue(Long id) {
        return venueRepository.findById(id).orElseThrow(() -> new RuntimeException("Venue not found"));
    }

    @Override
    public List<Venue> getAllVenues() {
        return venueRepository.findAll();
    }

    @Override
    public void updateVenue(Venue venue) {
        if (venue.getId() != null && venueRepository.existsById(venue.getId())) {
            venueRepository.save(venue);
        } else {
            throw new RuntimeException("Venue not found for update with ID: " + venue.getId());
        }
    }

    @Override
    public void deleteVenue(Long id) {
        Venue venue = venueRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Venue not found for deletion with ID: " + id));

        venueRepository.deleteById(id);
    }


}
