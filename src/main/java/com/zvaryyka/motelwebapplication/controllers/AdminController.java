package com.zvaryyka.motelwebapplication.controllers;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class AdminController {
    @GetMapping("/admin")
    public String mainAdminPage() {
        return "admin";
    }
}
