package com.zvaryyka.motelwebapplication.controllers;

import com.zvaryyka.motelwebapplication.dto.EmailDTO;
import com.zvaryyka.motelwebapplication.dto.FeedBackDTO;
import com.zvaryyka.motelwebapplication.models.FeedBack;
import com.zvaryyka.motelwebapplication.models.Person;
import com.zvaryyka.motelwebapplication.services.ArticleService;
import com.zvaryyka.motelwebapplication.services.EmailSenderService;
import com.zvaryyka.motelwebapplication.services.FeedBackService;
import com.zvaryyka.motelwebapplication.services.PersonDetailsService;
import jakarta.validation.Path;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import javax.naming.Binding;
import java.security.Principal;
import java.util.Objects;

@Controller
@RequestMapping()
public class IndexController {
    private final PersonDetailsService personDetailsService;
    private final FeedBackService feedBackService;
    private final ArticleService articleService;

    private final EmailSenderService emailSenderService;

    @Autowired
    public IndexController(PersonDetailsService personDetailsService, FeedBackService feedBackService, ArticleService articleService, EmailSenderService emailSenderService) {
        this.personDetailsService = personDetailsService;

        this.feedBackService = feedBackService;
        this.articleService = articleService;

        this.emailSenderService = emailSenderService;
    }

    @GetMapping("/index")
    public String index(Principal principal, Model model) {

        String mainBannerImageUrl = "/img/main/main_pic.png";
        String aboutBannerImageUrl = "/img/main/main_pic.png";
        String contactsBannerImageUrl = "/img/main/main_contacts.png";

        model.addAttribute("mainBannerImageUrl", mainBannerImageUrl);
        model.addAttribute("aboutBannerImageUrl", aboutBannerImageUrl);
        model.addAttribute("contactsBannerImageUrl", contactsBannerImageUrl);

        model.addAttribute("feedBacksDTO", feedBackService.getAllFeedBacksDTO());
        model.addAttribute("feedBackDTO", new FeedBackDTO());
        model.addAttribute("articlesDTO", articleService.getAllArticleDTO());
        model.addAttribute("emailForConsultation", new EmailDTO());

        if (principal != null) {

            Person person = personDetailsService.findByLogin(principal.getName())
                    .orElseThrow(() -> new UsernameNotFoundException("User not found"));
            if (Objects.equals(person.getUserRole(), "ROLE_USER")) {
                model.addAttribute("perAccount", "/guest");
            } else if (Objects.equals(person.getUserRole(), "ROLE_STUFF")) {
                model.addAttribute("perAccount", "/stuff");
            } else if (Objects.equals(person.getUserRole(), "ROLE_ADMIN")) {
                model.addAttribute("perAccount", "/admin");
            } else if (Objects.equals(person.getUserRole(), "ROLE_OWNER")) {
                model.addAttribute("perAccount", "/owner");
            }

            model.addAttribute("person", person);


        } else {

            model.addAttribute("person", new Person());
            model.addAttribute("perAccount", "/login");

        }


        return "main/index";
    }

    @GetMapping("/index/article/{id}")
    public String viewArticle(@PathVariable int id, Model model) {
        model.addAttribute("articleDTO", articleService.getOneArticleDTO(id));
        return "main/article";
    }

    @PostMapping("/index/createNewFeedBack")
    public String index(@ModelAttribute("feedbackDTO") @Valid FeedBackDTO feedBackDTO, Principal principal, BindingResult bindingResult) {
        if (bindingResult.hasErrors())
            return "main/index";
        Person person = personDetailsService.findByLogin(principal.getName()).orElseThrow(() -> new UsernameNotFoundException("User not found"));
        feedBackDTO.setUserId(person.getId());
        feedBackService.save(FeedBack.convertToFeedBack(feedBackDTO));
        return "redirect:/index";
    }

    @GetMapping("index/sendEmail")
    public String sendEmail(@ModelAttribute("emailForConsultation") EmailDTO emailDTO) {
        emailSenderService.sendEmailsAboutConsultation(emailDTO.getMail());
        return "message-about-consultation";
    }



}
