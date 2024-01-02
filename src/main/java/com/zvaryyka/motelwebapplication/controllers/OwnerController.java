package com.zvaryyka.motelwebapplication.controllers;

import com.zvaryyka.motelwebapplication.models.Person;
import com.zvaryyka.motelwebapplication.services.PersonDetailsService;
import com.zvaryyka.motelwebapplication.services.RegistrationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;

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
        model.addAttribute("person", person);
        {
            return "owner";
        }
    }
    @PostMapping("/createNewWorker")
    public String createNewWorker(@ModelAttribute("person") Person person, Model model) {
        registrationService.regWorker(person);

        return "owner";
    }
}
