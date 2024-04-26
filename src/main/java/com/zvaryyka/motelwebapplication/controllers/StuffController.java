package com.zvaryyka.motelwebapplication.controllers;

import com.zvaryyka.motelwebapplication.models.Article;
import com.zvaryyka.motelwebapplication.models.Person;
import com.zvaryyka.motelwebapplication.services.AdditionalServiceService;
import com.zvaryyka.motelwebapplication.services.ArticleService;
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
    private final AdditionalServiceService additionalServiceService;
    private final PersonDetailsService personDetailsService;

    private final ArticleService articleService;
    @Autowired
    public StuffController(AdditionalServiceService additionalServiceService, PersonDetailsService personDetailsService, ArticleService articleService) {
        this.additionalServiceService = additionalServiceService;
        this.personDetailsService = personDetailsService;
        this.articleService = articleService;
    }

    @GetMapping("/stuff")
    public String stuff(Principal principal, Model model) {
        Person person = personDetailsService.findByLogin(principal.getName())
                .orElseThrow(() -> new UsernameNotFoundException("User not found"));
        model.addAttribute("person", person);
        model.addAttribute("additionalServicesWhereStatusFalse",
                additionalServiceService.getAllAdditionalServicesWhereStatusFalse());
        model.addAttribute("createdArticle",new Article());


        return "stuff";
    }
    @PostMapping("/stuff/executeService/{id}")
    public String executeService(@PathVariable("id") int id) {
        additionalServiceService.changeStatusForAdditionalService(id);
        return "redirect:/stuff";
    }
    @PostMapping("/stuff/createArticle")
    public String createArticle(@ModelAttribute("createdArticle") Article article,Principal principal) {
        Person person = personDetailsService.findByLogin(principal.getName())
                .orElseThrow(() -> new UsernameNotFoundException("User not found"));
        article.setStuffId(person.getId());
        articleService.createNewArticle(article);

        return "redirect:/stuff";
    }

}
