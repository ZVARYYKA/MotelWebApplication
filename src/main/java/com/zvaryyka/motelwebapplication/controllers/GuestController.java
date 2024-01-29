package com.zvaryyka.motelwebapplication.controllers;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class GuestController {
    @GetMapping("/guest")
    public String guest() {
        return "guest";
    }
}
