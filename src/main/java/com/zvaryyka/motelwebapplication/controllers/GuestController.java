package com.zvaryyka.motelwebapplication.controllers;

import com.zvaryyka.motelwebapplication.models.Person;
import com.zvaryyka.motelwebapplication.repositories.BookingRepository;
import com.zvaryyka.motelwebapplication.services.BookingService;
import com.zvaryyka.motelwebapplication.services.PersonDetailsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import java.security.Principal;

@Controller
public class GuestController {

    private final BookingService bookingService;
    private final PersonDetailsService personDetailsService;

    @Autowired
    public GuestController(BookingService bookingService, PersonDetailsService personDetailsService) {
        this.bookingService = bookingService;
        this.personDetailsService = personDetailsService;
    }

    @GetMapping("/guest")
    public String mainGuestPage(Principal principal, Model model) {
        Person person = personDetailsService.findByLogin(principal.getName())
                .orElseThrow(() -> new UsernameNotFoundException("User not found"));
        model.addAttribute("activeBooking", bookingService.getActivityBookingById(person.getId()));
        model.addAttribute("historyBooking", bookingService.getHistoryBookingById(person.getId()));
        model.addAttribute("futureBooking", bookingService.getFutureBookingById(person.getId()));
        model.addAttribute("person", person);
        return "guest";
    }


}
