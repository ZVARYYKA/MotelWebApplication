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
public class OwnerController {
    private final PersonDetailsService personDetailsService;
    private final RegistrationService registrationService;

    @Autowired
    public OwnerController(PersonDetailsService personDetailsService, RegistrationService registrationService) {
        this.personDetailsService = personDetailsService;
        this.registrationService = registrationService;
    }


    @GetMapping("/owner")
    public String mainOwnerPage(Principal principal, Model model) {
        Person person = personDetailsService.findByLogin(principal.getName())
                .orElseThrow(() -> new UsernameNotFoundException("User not found"));
        model.addAttribute("allWorker",personDetailsService.showAllWorkers());
        model.addAttribute("person", person);
        model.addAttribute("worker",new Person());
        model.addAttribute("editWorker",new Person());
        {
            return "owner";
        }
    }
    @PostMapping("/createNewWorker")
    public String createNewWorker(@ModelAttribute("person") Person person, Model model) {
        registrationService.regWorker(person);

        return "redirect:/owner";
    }
    @PostMapping("/editWorker/{id}")
    public String editWorker(@ModelAttribute("editWorker") Person person, Model model, @PathVariable("id") int id) {
        registrationService.editWorker(person,id);
        return "redirect:/owner";
    }
    @PostMapping("/deleteWorker/{id}")
    public String deleteWorker( @PathVariable("id") int id) {
        //TODO Add binding result


        registrationService.delete(id);
        return "redirect:/owner";
    }
}
