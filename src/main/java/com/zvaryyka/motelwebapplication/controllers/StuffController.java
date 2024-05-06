package com.zvaryyka.motelwebapplication.controllers;

import com.zvaryyka.motelwebapplication.models.Article;
import com.zvaryyka.motelwebapplication.models.Person;
import com.zvaryyka.motelwebapplication.services.AdditionalServiceService;
import com.zvaryyka.motelwebapplication.services.ArticleService;
import com.zvaryyka.motelwebapplication.services.PersonDetailsService;
import com.zvaryyka.motelwebapplication.util.validation.ArticleValidator;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
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

    private final ArticleValidator articleValidator;

    @Autowired
    public StuffController(AdditionalServiceService additionalServiceService, PersonDetailsService personDetailsService, ArticleService articleService, ArticleValidator articleValidator) {
        this.additionalServiceService = additionalServiceService;
        this.personDetailsService = personDetailsService;
        this.articleService = articleService;
        this.articleValidator = articleValidator;
    }

    @GetMapping("/stuff")
    public String getStuffPanel(Principal principal, Model model) {
        Person person = personDetailsService.findByLogin(principal.getName())
                .orElseThrow(() -> new UsernameNotFoundException("User not found"));
        model.addAttribute("person", person);

        return "stuffPanel";
    }

    @GetMapping("/stuff/articles")
    public String getStuffArticles(Principal principal, Model model) {
        Person person = personDetailsService.findByLogin(principal.getName())
                .orElseThrow(() -> new UsernameNotFoundException("User not found"));
        model.addAttribute("person", person);
        model.addAttribute("createdArticle", new Article());
        model.addAttribute("articles", articleService.getAllArticleDTO());

        return "stuff-articles";
    }

    @GetMapping("/stuff/service")
    public String getStuffAddService(Principal principal, Model model) {
        Person person = personDetailsService.findByLogin(principal.getName())
                .orElseThrow(() -> new UsernameNotFoundException("User not found"));
        model.addAttribute("person", person);
        model.addAttribute("additionalServicesWhereStatusFalse",
                additionalServiceService.getAllAdditionalServicesWhereStatusFalse());


        return "stuff-service";
    }

    @PostMapping("/stuff/service/executeService/{id}")
    public String executeService(@PathVariable("id") int id) {
        additionalServiceService.changeStatusForAdditionalService(id);
        return "redirect:/stuff/service";
    }

    @PostMapping("/stuff/articles/createArticle")
    public String createArticle(@ModelAttribute("createdArticle")  Article article,
                                BindingResult bindingResult, Principal principal,Model model) {

        articleValidator.validate(article,bindingResult);
        if (bindingResult.hasErrors()) {

            Person person = personDetailsService.findByLogin(principal.getName())
                    .orElseThrow(() -> new UsernameNotFoundException("User not found"));
            model.addAttribute("person", person);

            model.addAttribute("articles", articleService.getAllArticleDTO());

            return "stuff-articles";
        }
        Person person = personDetailsService.findByLogin(principal.getName())
                .orElseThrow(() -> new UsernameNotFoundException("User not found"));
        article.setStuffId(person.getId());
        articleService.createNewArticle(article);

        return "redirect:/stuff/articles";
    }

}
