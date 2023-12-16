package com.zvaryyka.motelwebapplication.controllers;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@Controller
@RequestMapping()
public class IndexController {
    @GetMapping("/index")
    public String index() {
        return "index";
    }
}
