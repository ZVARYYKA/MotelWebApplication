package com.zvaryyka.motelwebapplication.controllers;

import com.zvaryyka.motelwebapplication.models.Person;
import com.zvaryyka.motelwebapplication.services.AdditionalServiceService;
import com.zvaryyka.motelwebapplication.services.PersonDetailsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;

import java.security.Principal;

@Controller
public class StuffController {
    private final AdditionalServiceService additionalServiceService;
    private final PersonDetailsService personDetailsService;

    @Autowired
    public StuffController(AdditionalServiceService additionalServiceService, PersonDetailsService personDetailsService) {
        this.additionalServiceService = additionalServiceService;
        this.personDetailsService = personDetailsService;
    }

    @GetMapping("/stuff")
    public String stuff(Principal principal, Model model) {
        Person person = personDetailsService.findByLogin(principal.getName())
                .orElseThrow(() -> new UsernameNotFoundException("User not found"));
        model.addAttribute("person", person);
        model.addAttribute("additionalServicesWhereStatusFalse",
                additionalServiceService.getAllAdditionalServicesWhereStatusFalse());


        return "stuff";
    }
    @PostMapping("/stuff/executeService/{id}")
    public String executeService(@PathVariable("id") int id) {
        additionalServiceService.changeStatusForAdditionalService(id);
        return "redirect:/stuff";
    }

}
