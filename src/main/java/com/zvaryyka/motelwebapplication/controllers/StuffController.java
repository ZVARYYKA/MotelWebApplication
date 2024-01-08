package com.zvaryyka.motelwebapplication.controllers;

import com.zvaryyka.motelwebapplication.models.AdditionalServices;
import com.zvaryyka.motelwebapplication.models.Person;
import com.zvaryyka.motelwebapplication.repositories.PeopleRepository;
import com.zvaryyka.motelwebapplication.services.AdditionalServiceService;
import com.zvaryyka.motelwebapplication.services.PersonDetailsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;

import java.security.Principal;

@Controller
public class StuffController {
    private final PersonDetailsService personDetailsService;
    private final AdditionalServiceService additionalServiceService;


    @Autowired
    public StuffController(PersonDetailsService personDetailsService, PeopleRepository peopleRepository, AdditionalServiceService additionalServiceService) {
        this.personDetailsService = personDetailsService;
        this.additionalServiceService = additionalServiceService;
    }

    @GetMapping("/stuff")
    public String mainStuffPage(Principal principal, Model model) {
        Person person = personDetailsService.findByLogin(principal.getName())
                .orElseThrow(() -> new UsernameNotFoundException("User not found"));
        model.addAttribute("person", person);
        model.addAttribute("allAddServices",additionalServiceService.showAll());
        model.addAttribute("deniedService",new AdditionalServices());
        model.addAttribute("applyService",new AdditionalServices());

                return "cabinets/stuff";
    }
    @PostMapping("/applyService/{id}")
    public String applyService(@PathVariable("id") int id) {
        additionalServiceService.changeStatusOnSuccess(id);
        return "redirect:/stuff";

    }
    @PostMapping("/deniedService/{id}")
    public String deniedService(@PathVariable("id") int id, @ModelAttribute("deniedService") AdditionalServices additionalServices) {
        additionalServiceService.changeStatusOnDenied(id,additionalServices.getResponseMessage());
        return "redirect:/stuff";

    }

}
