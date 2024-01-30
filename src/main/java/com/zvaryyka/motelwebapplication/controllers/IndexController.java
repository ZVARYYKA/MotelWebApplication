package com.zvaryyka.motelwebapplication.controllers;

import com.zvaryyka.motelwebapplication.dto.FeedBackDTO;
import com.zvaryyka.motelwebapplication.models.FeedBack;
import com.zvaryyka.motelwebapplication.models.Person;
import com.zvaryyka.motelwebapplication.services.FeedBackService;
import com.zvaryyka.motelwebapplication.services.PersonDetailsService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.naming.Binding;
import java.security.Principal;

@Controller
@RequestMapping()
public class IndexController {
    private final PersonDetailsService personDetailsService;
    private final FeedBackService feedBackService;


    @Autowired
    public IndexController(PersonDetailsService personDetailsService, FeedBackService feedBackService) {
        this.personDetailsService = personDetailsService;

        this.feedBackService = feedBackService;
    }

    @GetMapping("/index")
    public String index(Principal principal, Model model) {
        model.addAttribute("feedBacksDTO", feedBackService.getAllFeedBacksDTO());
        model.addAttribute("feedBackDTO", new FeedBackDTO());

        if (principal == null)
            model.addAttribute("person", new Person());
        else {
            Person person = personDetailsService.findByLogin(principal.getName())
                    .orElseThrow(() -> new UsernameNotFoundException("User not found"));
            model.addAttribute("person", person);

        }


        return "main/index";
    }

    @PostMapping("/index/createNewFeedBack")
    public String index(@ModelAttribute("feedbackDTO") @Valid FeedBackDTO feedBackDTO, Principal principal, BindingResult bindingResult) {
        if(bindingResult.hasErrors())
            return "main/index";
        Person person = personDetailsService.findByLogin(principal.getName()).orElseThrow(() -> new UsernameNotFoundException("User not found"));
        feedBackDTO.setUserId(person.getId());
        feedBackService.save(FeedBack.convertToFeedBack(feedBackDTO));
        return "redirect:/index";
    }


}
