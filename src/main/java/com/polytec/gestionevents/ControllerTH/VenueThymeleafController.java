package com.polytec.gestionevents.ControllerTH;

import com.polytec.gestionevents.Entities.Venue;
import com.polytec.gestionevents.Services.IServiceManager;
import com.polytec.gestionevents.Services.IServiceVenue;
import lombok.AllArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Controller
@AllArgsConstructor
@RequestMapping("/venues")
public class VenueThymeleafController {

    private  IServiceVenue venueService;
    private  IServiceManager managerService;

    /*@Autowired
    public VenueThymeleafController(IServiceVenue venueService, IServiceManager managerService) {
        this.venueService = venueService;
        this.managerService = managerService;
    }*/

    // Afficher la liste des venues
    @GetMapping
    public String listVenues(Model model) {
        List<Venue> venues = venueService.getAllVenues();
        model.addAttribute("venues", venues);
        return "listVenue";
    }

    // Formulaire pour ajouter un venue
    @GetMapping("/form")
    public String showAddForm(Model model) {
        model.addAttribute("venue", new Venue());
        model.addAttribute("managers", managerService.getAllManagers());
        return "addVenue";
    }

    // Enregistrer un nouveau venue
    @PostMapping("/save")
    public String saveVenue(@ModelAttribute Venue venue) {
        venueService.addVenue(venue);
        return "redirect:/venues";
    }

    // Formulaire pour modifier un venue existant
    @GetMapping("/edit/{id}")
    public String showEditForm(@PathVariable Long id, Model model) {
        Venue venue = venueService.getVenue(id);
        if (venue != null) {
            model.addAttribute("venue", venue);
            model.addAttribute("managers", managerService.getAllManagers());
            return "addVenue";
        }
        return "redirect:/venues";
    }

    // Supprimer un venue
    @GetMapping("/delete/{id}")
    public String deleteVenue(@PathVariable Long id) {
        venueService.deleteVenue(id);
        return "redirect:/venues";
    }
}