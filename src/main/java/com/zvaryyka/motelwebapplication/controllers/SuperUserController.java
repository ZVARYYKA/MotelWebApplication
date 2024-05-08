package com.zvaryyka.motelwebapplication.controllers;

import com.zvaryyka.motelwebapplication.models.Person;
import com.zvaryyka.motelwebapplication.services.EmailSenderService;
import com.zvaryyka.motelwebapplication.services.PersonDetailsService;
import com.zvaryyka.motelwebapplication.services.RegistrationService;
import com.zvaryyka.motelwebapplication.util.validation.PersonValidator;
import lombok.Getter;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import java.security.Principal;
import java.util.List;

@Controller
@Slf4j
public class SuperUserController {

    private final RegistrationService registrationService;
    private final PersonValidator personValidator;
    private final PersonDetailsService personDetailsService;
    private final EmailSenderService emailSenderService;


    @Autowired
    public SuperUserController(RegistrationService registrationService, PersonValidator personValidator, PersonDetailsService personDetailsService, EmailSenderService emailSenderService) {

        this.registrationService = registrationService;
        this.personValidator = personValidator;
        this.personDetailsService = personDetailsService;
        this.emailSenderService = emailSenderService;
    }

    @GetMapping("/super-user")
    public String getSuperUserPanel(Principal principal, Model model) {
        Person person = personDetailsService.findByLogin(principal.getName())
                .orElseThrow(() -> new UsernameNotFoundException("User not found"));
        model.addAttribute("person", person);


        return "super-user-panel";
    }

    @GetMapping("/super-user/createNewRandomOwner")
    public String createNewRandomOwner() {

        Person person = registrationService.registerNewRandomOwner();
        log.info("Created new random owner in super user panel");
        emailSenderService.sendEmailAboutCreatedNewOwner(person);



        return "message-about-created-new-owner";


    }

}
