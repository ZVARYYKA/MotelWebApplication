package com.zvaryyka.motelwebapplication.controllers;

import com.zvaryyka.motelwebapplication.models.Person;
import com.zvaryyka.motelwebapplication.repositories.BookingRepository;
import com.zvaryyka.motelwebapplication.services.PersonDetailsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import java.security.Principal;

@Controller
public class GuestController {
    private final BookingRepository bookingRepository;
    private final PersonDetailsService personDetailsService;

    @Autowired
    public GuestController(BookingRepository bookingRepository, PersonDetailsService personDetailsService) {
        this.bookingRepository = bookingRepository;
        this.personDetailsService = personDetailsService;
    }

    @GetMapping("/guest")
    public String mainGuestPage(Principal principal, Model model) {
        Person person = getCurrentPerson(principal);
        model.addAttribute("activeBooking", bookingRepository.getActivityBookingById(person.getId()));
        return "guest";
    }

    private Person getCurrentPerson(Principal principal) {
        if (principal == null) {
            return new Person();
        } else {
            return personDetailsService.findByLogin(principal.getName())
                    .orElseThrow(() -> new UsernameNotFoundException("User not found"));
        }

    }
}
