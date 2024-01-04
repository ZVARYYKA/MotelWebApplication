package com.zvaryyka.motelwebapplication.controllers;

import com.zvaryyka.motelwebapplication.models.Person;
import com.zvaryyka.motelwebapplication.services.PersonDetailsService;
import com.zvaryyka.motelwebapplication.services.RegistrationService;
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
    private final RegistrationService registrationService;

    @Autowired
    public AdminController(PersonDetailsService personDetailsService, RegistrationService registrationService) {
        this.personDetailsService = personDetailsService;
        this.registrationService = registrationService;
    }

    @GetMapping("/admin")
    public String mainAdminPage(Principal principal, Model model) {
        Person person = personDetailsService.findByLogin(principal.getName())
                .orElseThrow(() -> new UsernameNotFoundException("User not found"));
        model.addAttribute("person", person);
        model.addAttribute("allStuff", personDetailsService.showAllStuffs());
        model.addAttribute("editPerson", new Person());
        return "admin";
    }

    @PostMapping("/createNewStuff")
    public String createNewStuff(@ModelAttribute("person") Person person, Model model) {
        registrationService.regStaff(person);

        return "redirect:/admin";
    }

    @PostMapping("/editPerson/{id}")
    public String updatePerson(@ModelAttribute("editPerson") Person editPerson, BindingResult bindingResult, @PathVariable("id") int id) {
        if(bindingResult.hasErrors()){
            return "admin";
        }
        registrationService.updateStuff(editPerson,id);
        return "redirect:/admin";

    }

    @PostMapping("/deletePerson/{id}")
    public String deletePerson( @PathVariable("id") int id) {
        //TODO Add binding result
        registrationService.delete(id);
        return "redirect:/admin";
    }

}
