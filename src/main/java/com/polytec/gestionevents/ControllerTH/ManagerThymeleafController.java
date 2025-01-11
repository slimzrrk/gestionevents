package com.polytec.gestionevents.ControllerTH;

import com.polytec.gestionevents.Entities.Manager;
import com.polytec.gestionevents.Services.IServiceManager;
import lombok.AllArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Controller
@AllArgsConstructor
@RequestMapping("/managers")
public class ManagerThymeleafController {

    private IServiceManager managerService;

    /*@Autowired
    public ManagerThymeleafController(IServiceManager managerService) {
        this.managerService = managerService;
    }*/

    // Afficher la liste des managers
    @GetMapping
    public String listManagers(Model model) {
        List<Manager> managers = managerService.getAllManagers();
        model.addAttribute("managers", managers);
        return "listManager"; // Nom du template Thymeleaf
    }

    // Formulaire pour ajouter un manager
    @GetMapping("/form")
    public String showAddForm(Model model) {
        model.addAttribute("manager", new Manager());
        return "addManager"; // Nom du template Thymeleaf
    }

    // Enregistrer un nouveau manager
    @PostMapping("/save")
    public String saveManager(@ModelAttribute Manager manager) {
        managerService.addManager(manager);
        return "redirect:/managers";
    }

    // Formulaire pour modifier un manager existant
    @GetMapping("/edit/{id}")
    public String showEditForm(@PathVariable Long id, Model model) {
        Manager manager = managerService.getManager(id);
        if (manager != null) {
            model.addAttribute("manager", manager);
            return "addManager"; // Utilise le même formulaire pour ajouter et éditer
        }
        return "redirect:/managers";
    }

    // Supprimer un manager
    @GetMapping("/delete/{id}")
    public String deleteManager(@PathVariable Long id) {
        managerService.deleteManager(id);
        return "redirect:/managers";
    }
}
