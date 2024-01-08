package com.zvaryyka.motelwebapplication.controllers;

import com.zvaryyka.motelwebapplication.models.AdditionalServices;
import com.zvaryyka.motelwebapplication.models.Booking;
import com.zvaryyka.motelwebapplication.models.Person;
import com.zvaryyka.motelwebapplication.services.AdditionalServiceService;
import com.zvaryyka.motelwebapplication.services.BookingService;
import com.zvaryyka.motelwebapplication.services.PersonDetailsService;
import com.zvaryyka.motelwebapplication.util.exceptions.RoomUnavailableException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.Banner;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;

import java.security.Principal;

@Controller
public class GuestController {

    private final BookingService bookingService;
    private final PersonDetailsService personDetailsService;
    private final AdditionalServiceService additionalServiceService;

    @Autowired
    public GuestController(BookingService bookingService, PersonDetailsService personDetailsService, AdditionalServiceService additionalServiceService) {
        this.bookingService = bookingService;
        this.personDetailsService = personDetailsService;
        this.additionalServiceService = additionalServiceService;
    }

    @GetMapping("/guest")
    public String mainGuestPage(Principal principal, @ModelAttribute("booking") Booking booking, Model model) {
        Person person = personDetailsService.findByLogin(principal.getName())
                .orElseThrow(() -> new UsernameNotFoundException("User not found"));
        model.addAttribute("activeBooking", bookingService.getActivityBookingById(person.getId()));
        model.addAttribute("historyBooking", bookingService.getHistoryBookingById(person.getId()));
        model.addAttribute("futureBooking", bookingService.getFutureBookingById(person.getId()));
        model.addAttribute("person", person);
        model.addAttribute("additionalServicesByUserId",
                additionalServiceService.showAdditionalServicesByUserId(person.getId()));
        model.addAttribute("addService",new AdditionalServices());
        return "cabinets/guest";
    }

    @PostMapping("/createNewBooking")
    public String createBooking(Principal principal, @ModelAttribute("booking") Booking booking, Model model, BindingResult bindingResult) {
        Person person = personDetailsService.findByLogin(principal.getName())
                .orElseThrow(() -> new UsernameNotFoundException("User not found"));
        booking.setUserId(person.getId());

        try {
            bookingService.addNewBooking(booking, person.getId());
            return "redirect:/guest";
        } catch (RoomUnavailableException ex) {
            model.addAttribute("error", ex.getMessage());

            model.addAttribute("person", person);
            model.addAttribute("activeBooking", bookingService.getActivityBookingById(person.getId()));
            model.addAttribute("historyBooking", bookingService.getHistoryBookingById(person.getId()));
            model.addAttribute("futureBooking", bookingService.getFutureBookingById(person.getId()));

            return "cabinets/guest";
        }
    }

    @PostMapping("/createNewAdditionalService")
    public String createAddService(Principal principal, @ModelAttribute("addService") AdditionalServices additionalServices, Model model, BindingResult bindingResult) {
        Person person = personDetailsService.findByLogin(principal.getName())
                .orElseThrow(() -> new UsernameNotFoundException("User not found"));
        additionalServices.setUserId(person.getId());
        additionalServiceService.addNewService(additionalServices);

        return "redirect:/guest";
    }


}
