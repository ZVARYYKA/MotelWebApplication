package com.zvaryyka.motelwebapplication.controllers;

import com.zvaryyka.motelwebapplication.dto.PersonDTO;
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
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;

import java.security.Principal;

@Controller
public class OwnerController {
    private final RegistrationService registrationService;
    private final PersonValidator personValidator;
    private final PersonDetailsService personDetailsService;
    @Autowired
    public OwnerController(RegistrationService registrationService, PersonValidator personValidator, PersonDetailsService personDetailsService) {
        this.registrationService = registrationService;
        this.personValidator = personValidator;
        this.personDetailsService = personDetailsService;
    }

    @GetMapping("/owner")
    public String owner(Principal principal, Model model) {
        Person person = personDetailsService.findByLogin(principal.getName())
                .orElseThrow(() -> new UsernameNotFoundException("User not found"));
        model.addAttribute("person", person);
        model.addAttribute("personDTO",new PersonDTO());
        model.addAttribute("workers",personDetailsService.showAllWorkers());

        return "owner";
    }
    @PostMapping("/owner/regNewWorker")
    public String createNewWorker(@ModelAttribute("personDTO") @Valid PersonDTO personDTO,
                                  BindingResult bindingResult) {
        personValidator.validate(registrationService.convertToPerson(personDTO), bindingResult);

        if (bindingResult.hasErrors()) {
            return "owner";
        }
        registrationService.regWorker(personDTO);
        return "redirect:/owner";

    }

}
