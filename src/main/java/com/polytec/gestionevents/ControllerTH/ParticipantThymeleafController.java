package com.polytec.gestionevents.ControllerTH;

import com.polytec.gestionevents.Entities.Participant;
import com.polytec.gestionevents.Services.IServiceParticipant;
import lombok.AllArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Controller
@AllArgsConstructor
@RequestMapping("/participants")
public class ParticipantThymeleafController {

    private  IServiceParticipant participantService;

    /*@Autowired
    public ParticipantThymeleafController(IServiceParticipant participantService) {
        this.participantService = participantService;
    }*/

    // Afficher la liste des participants
    @GetMapping
    public String listParticipants(Model model) {
        List<Participant> participants = participantService.getAllParticipants();
        model.addAttribute("participants", participants);
        return "listParticipant"; // Nom du template Thymeleaf
    }

    // Formulaire pour ajouter un participant
    @GetMapping("/form")
    public String showAddForm(Model model) {
        model.addAttribute("participant", new Participant());
        return "addParticipant"; // Nom du template Thymeleaf
    }

    // Enregistrer un nouveau participant
    @PostMapping("/save")
    public String saveParticipant(@ModelAttribute Participant participant) {
        participantService.addParticipant(participant);
        return "redirect:/participants";
    }

    // Formulaire pour modifier un participant existant
    @GetMapping("/edit/{id}")
    public String showEditForm(@PathVariable Long id, Model model) {
        Participant participant = participantService.getParticipant(id);
        if (participant != null) {
            model.addAttribute("participant", participant);
            return "addParticipant"; // Utilise le même formulaire pour ajouter et éditer
        }
        return "redirect:/participants";
    }

    // Supprimer un participant
    @GetMapping("/delete/{id}")
    public String deleteParticipant(@PathVariable Long id) {
        participantService.deleteParticipant(id);
        return "redirect:/participants";
    }
}
