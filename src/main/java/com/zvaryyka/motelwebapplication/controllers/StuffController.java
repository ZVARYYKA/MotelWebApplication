package com.zvaryyka.motelwebapplication.controllers;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class StuffController {
    @GetMapping("/stuff")
    public String stuff() {
        return "stuff";
    }

}
