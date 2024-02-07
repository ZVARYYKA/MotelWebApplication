package com.zvaryyka.motelwebapplication.controllers;

import com.zvaryyka.motelwebapplication.dto.BookingStatisticDTO;
import com.zvaryyka.motelwebapplication.dto.DateRangeDTO;
import com.zvaryyka.motelwebapplication.services.BookingService;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Date;
import java.util.List;

@RestController
//@RequestMapping("/admin")
public class StatisticController {
    private final BookingService bookingService;

    public StatisticController(BookingService bookingService) {
        this.bookingService = bookingService;
    }

    @GetMapping("/statistics")
    public ResponseEntity<List<BookingStatisticDTO>> getStatistics(@RequestParam("startDate") @DateTimeFormat(pattern = "yyyy-MM-dd") Date startDate,
                                                                   @RequestParam("endDate") @DateTimeFormat(pattern = "yyyy-MM-dd") Date endDate) {
        // Здесь вызывается метод сервиса, который возвращает статистику по бронированиям за указанный период
        List<BookingStatisticDTO> statistics = bookingService.getBookingStatistics(startDate, endDate);
        System.out.println("Зашел в GET запрос");
        System.out.println(statistics);
        return ResponseEntity.ok(statistics);
    }
    //TODO In future, need to rework.  I must rework class and get all data only.

    // Контроллер для получения чистой прибыли за указанный период
    // Контроллер для получения общей прибыли за определенный период времени
    @GetMapping("/totalRevenue")
    public ResponseEntity<Double> getTotalRevenue(
            @RequestParam("startDate") @DateTimeFormat(pattern = "yyyy-MM-dd") Date startDate,
            @RequestParam("endDate") @DateTimeFormat(pattern = "yyyy-MM-dd") Date endDate) {
        Double totalRevenue = bookingService.getTotalRevenue(startDate, endDate);
        return ResponseEntity.ok(totalRevenue);
    }

    // Контроллер для получения общего количества бронирований за определенный период времени
    @GetMapping("/totalBookings")
    public ResponseEntity<Integer> getTotalBookings(
            @RequestParam("startDate") @DateTimeFormat(pattern = "yyyy-MM-dd") Date startDate,
            @RequestParam("endDate") @DateTimeFormat(pattern = "yyyy-MM-dd") Date endDate) {
        Integer totalBookings = bookingService.getTotalBookings(startDate, endDate);
        return ResponseEntity.ok(totalBookings);
    }
}
