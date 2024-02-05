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
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;

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
    public String admin(Principal principal, Model model) {
        Person person = personDetailsService.findByLogin(principal.getName())
                .orElseThrow(() -> new UsernameNotFoundException("User not found"));
        model.addAttribute("person", person);
        model.addAttribute("stuffs",personDetailsService.showAllStuffs());
        return "admin";
    }
    @PostMapping("/admin/regNewStuff")
    public String regNewStuff(@ModelAttribute("person") @Valid Person person,
                              BindingResult bindingResult) {
        personValidator.validate(person, bindingResult);

        if (bindingResult.hasErrors()) {
            return "admin";
        }

        registrationService.regStuff(person);

        return "redirect:/admin";
    }
    @GetMapping("/admin/graphic")
    public String viewGraphic() {
        return "graphic";
    }
}
