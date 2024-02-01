package com.zvaryyka.motelwebapplication.controllers;

import com.zvaryyka.motelwebapplication.dto.BookingDTO;
import com.zvaryyka.motelwebapplication.models.Booking;
import com.zvaryyka.motelwebapplication.models.Person;
import com.zvaryyka.motelwebapplication.services.BookingService;
import com.zvaryyka.motelwebapplication.services.PersonDetailsService;
import com.zvaryyka.motelwebapplication.services.RoomTypeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
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
    @Autowired
    public GuestController(PersonDetailsService personDetailsService, BookingService bookingService, RoomTypeService roomTypeService) {
        this.personDetailsService = personDetailsService;
        this.bookingService = bookingService;
        this.roomTypeService = roomTypeService;
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
        return "guest";
    }
    @PostMapping("/guest/proceedNewBooking")
    public String proceedNewBooking(@ModelAttribute("bookingDTO") BookingDTO bookingDTO, Model model) {
        // Рассчитайте стоимость проживания
        BigDecimal totalCost = calculateTotalCost(bookingDTO);

        // Передайте информацию о стоимости на новую страницу
        model.addAttribute("totalCost", totalCost);
        model.addAttribute("bookingDTO", bookingDTO);

        return "bookingConfirmation";
    }
    //TODO I must rewrite Booking logic int future
    @PostMapping("/guest/saveBooking") //TODO NOT WORK,NEED FIX
    public String saveBooking(@ModelAttribute("bookingDTO") BookingDTO bookingDTO, Model model,Principal principal) { //TODO add BR
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
