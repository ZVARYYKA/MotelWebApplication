package com.zvaryyka.motelwebapplication.controllers;

import com.zvaryyka.motelwebapplication.models.Person;
import com.zvaryyka.motelwebapplication.services.PersonDetailsService;
import com.zvaryyka.motelwebapplication.services.RegistrationService;
import com.zvaryyka.motelwebapplication.util.validation.PersonValidator;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import java.security.Principal;

@Controller
public class AdminController {
    private final PersonDetailsService personDetailsService;
    private final PersonValidator personValidator;
    private final RegistrationService registrationService;

    @Autowired
    public AdminController(PersonDetailsService personDetailsService, PersonValidator personValidator, RegistrationService registrationService) {
        this.personDetailsService = personDetailsService;
        this.personValidator = personValidator;
        this.registrationService = registrationService;
    }

    @GetMapping("/admin")
    public String getAdminPanel(Principal principal,Model model) {
        Person person = personDetailsService.findByLogin(principal.getName())
                .orElseThrow(() -> new UsernameNotFoundException("User not found"));
        model.addAttribute("person", person);

        return "adminPanel";
    }
        @GetMapping("/admin/stuff")
    public String admin(Principal principal, Model model) {
        Person person = personDetailsService.findByLogin(principal.getName())
                .orElseThrow(() -> new UsernameNotFoundException("User not found"));
        model.addAttribute("person", person);
        model.addAttribute("updatePerson", new Person());
        model.addAttribute("stuffs", personDetailsService.showAllStuffs());
        return "admin-stuff";
    }
    @PostMapping("/admin/stuff/regNewStuff")
    public String regNewStuff(@ModelAttribute("person") @Valid Person person,
                              BindingResult bindingResult) {
        personValidator.validate(person, bindingResult);

        if (bindingResult.hasErrors()) {
            return "admin-stuff";
        }

        registrationService.regStuff(person);

        return "redirect:/admin/stuff";
    }

    @PostMapping("/admin/stuff/deleteStuff/{id}")
    public String deleteStuff(@PathVariable("id") int id) {
        personDetailsService.delete(id);
        return "redirect:/admin/stuff";
    }

    @PostMapping("/admin/stuff/updateStuff/{id}")
    public String updateStuff(@PathVariable("id") int id, @ModelAttribute("updatePerson") @Valid Person person) {
        personDetailsService.updateStuff(id, person);
        return "redirect:/admin/stuff";
    }

    @GetMapping("/admin/statistics")
    public String viewGraphic() {
        return "graphic";
    }
}
