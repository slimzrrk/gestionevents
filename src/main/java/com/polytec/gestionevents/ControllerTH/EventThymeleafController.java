package com.polytec.gestionevents.ControllerTH;

import com.polytec.gestionevents.Entities.Event;
import com.polytec.gestionevents.Services.IServiceEvent;
import com.polytec.gestionevents.Services.ServiceParticipant;
import com.polytec.gestionevents.Services.ServiceVenue;
import lombok.AllArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Controller
@AllArgsConstructor
@RequestMapping("/events")
public class EventThymeleafController {

    private IServiceEvent serviceEvent;
    private ServiceVenue serviceVenue;
    private ServiceParticipant serviceParticipant;

    /*@Autowired
    public EventThymeleafController(IServiceEvent serviceEvent,
                                    ServiceVenue serviceVenue,
                                    ServiceParticipant serviceParticipant) {
        this.serviceEvent = serviceEvent;
        this.serviceVenue = serviceVenue;
        this.serviceParticipant = serviceParticipant;
    }*/
    @GetMapping
    public String listEvents(Model model) {
        List<Event> events = serviceEvent.getAllEvents();
        model.addAttribute("events", events);
        return "listEvent"; // Nom du template Thymeleaf
    }

    @GetMapping("/form")
    public String showAddForm(Model model) {
        model.addAttribute("event", new Event());
        model.addAttribute("venues", serviceVenue.getAllVenues());
        model.addAttribute("participants", serviceParticipant.getAllParticipants());
        return "addEvent"; // Nom du template Thymeleaf
    }

    @PostMapping("/save")
    public String saveEvent(@ModelAttribute Event event) {
        serviceEvent.addEvent(event);
        return "redirect:/events";
    }

    @GetMapping("/edit/{id}")
    public String showEditForm(@PathVariable Long id, Model model) {
        Event event = serviceEvent.getEvent(id);
        if (event != null) {
            model.addAttribute("event", event);
            model.addAttribute("venues", serviceVenue.getAllVenues());
            model.addAttribute("participants", serviceParticipant.getAllParticipants());
            return "addEvent"; // Utilise le même formulaire pour ajouter et éditer
        }
        return "redirect:/events";
    }

    @GetMapping("/delete/{id}")
    public String deleteEvent(@PathVariable Long id) {
        serviceEvent.deleteEvent(id);
        return "redirect:/events";
    }
}
