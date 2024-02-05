package com.zvaryyka.motelwebapplication.controllers;

import com.zvaryyka.motelwebapplication.dto.BookingDTO;
import com.zvaryyka.motelwebapplication.models.Booking;
import com.zvaryyka.motelwebapplication.models.Person;
import com.zvaryyka.motelwebapplication.services.BookingService;
import com.zvaryyka.motelwebapplication.services.PersonDetailsService;
import com.zvaryyka.motelwebapplication.services.RoomTypeService;
import com.zvaryyka.motelwebapplication.util.validation.BookingValidator;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.InitBinder;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;

import java.math.BigDecimal;
import java.security.Principal;
import java.time.Instant;
import java.time.LocalDate;
import java.time.ZoneId;
import java.time.temporal.ChronoUnit;
import java.util.Date;

@Controller
public class GuestController {
    private final PersonDetailsService personDetailsService;
    private final BookingService bookingService;
    private final RoomTypeService roomTypeService;
    private final BookingValidator bookingValidator;

    @Autowired
    public GuestController(PersonDetailsService personDetailsService, BookingService bookingService, RoomTypeService roomTypeService, BookingValidator bookingValidator) {
        this.personDetailsService = personDetailsService;
        this.bookingService = bookingService;
        this.roomTypeService = roomTypeService;
        this.bookingValidator = bookingValidator;
    }

    @GetMapping("/guest")
    public String guest(Principal principal, Model model) {
        Person person = personDetailsService.findByLogin(principal.getName())
                .orElseThrow(() -> new UsernameNotFoundException("User not found"));
        model.addAttribute("person", person);
        model.addAttribute("bookingDTO",new BookingDTO());
        model.addAttribute("actualBookings", bookingService.getActualBookings(person.getId()));
        model.addAttribute("futureBookings",bookingService.getFutureBookings(person.getId()));
        model.addAttribute("historyBookings",bookingService.getHistoryBookings(person.getId()));
        model.addAttribute("typeOfRooms",roomTypeService.allTypeOfRooms());
        return "guest";
    }
    @InitBinder("bookingDTO")
    protected void initBinder(WebDataBinder binder) {
        binder.addValidators(bookingValidator);
    }
    @PostMapping("/guest")
    public String proceedNewBooking(@ModelAttribute("bookingDTO") @Valid BookingDTO bookingDTO, BindingResult bindingResult,Principal principal,Model model) {
        Person person = personDetailsService.findByLogin(principal.getName())
                .orElseThrow(() -> new UsernameNotFoundException("User not found"));
        model.addAttribute("person", person);
        if (bindingResult.hasErrors()) {
            model.addAttribute("actualBookings", bookingService.getActualBookings(person.getId()));
            model.addAttribute("futureBookings",bookingService.getFutureBookings(person.getId()));
            model.addAttribute("historyBookings",bookingService.getHistoryBookings(person.getId()));
            model.addAttribute("typeOfRooms",roomTypeService.allTypeOfRooms());
            return "guest";
        }
        // Рассчитайте стоимость проживания
        BigDecimal totalCost = calculateTotalCost(bookingDTO);

        // Передайте информацию о стоимости на новую страницу
        model.addAttribute("totalCost", totalCost);
        model.addAttribute("acceptBookingDTO", bookingDTO);

        return "bookingConfirmation";
    }

    @PostMapping("/guest/saveBooking")
    public String saveBooking(@ModelAttribute("acceptBookingDTO")  BookingDTO bookingDTO, Model model, Principal principal) {
        Person person = personDetailsService.findByLogin(principal.getName())
                .orElseThrow(() -> new UsernameNotFoundException("User not found"));
        BigDecimal totalCost = calculateTotalCost(bookingDTO);
        bookingDTO.setUserId(person.getId());


        bookingService.save(bookingService.convertToBooking(bookingDTO, totalCost));;
        return "redirect:/guest";
    }

    private BigDecimal calculateTotalCost(BookingDTO bookingDTO) {

        BigDecimal roomTypeCost = roomTypeService.findCostByRoomType(bookingDTO.getRoomType());

        // Рассчитываем количество дней проживания
        long numberOfDays = calculateNumberOfDays(bookingDTO.getCheckInDate(), bookingDTO.getCheckOutDate());

        // Рассчитываем общую стоимость
        return roomTypeCost.multiply(BigDecimal.valueOf(numberOfDays));
    }

    private long calculateNumberOfDays(Date checkInDate, Date checkOutDate) {
        Instant checkInInstant = checkInDate.toInstant();
        Instant checkOutInstant = checkOutDate.toInstant();

        LocalDate checkInLocalDate = checkInInstant.atZone(ZoneId.systemDefault()).toLocalDate();
        LocalDate checkOutLocalDate = checkOutInstant.atZone(ZoneId.systemDefault()).toLocalDate();

        return ChronoUnit.DAYS.between(checkInLocalDate, checkOutLocalDate) + 1;
    }
}
