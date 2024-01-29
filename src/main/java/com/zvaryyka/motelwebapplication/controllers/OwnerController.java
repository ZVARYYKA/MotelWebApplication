package com.zvaryyka.motelwebapplication.controllers;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class OwnerController {
    @GetMapping("/owner")
    public String owner() {
        return "owner";
    }
}
