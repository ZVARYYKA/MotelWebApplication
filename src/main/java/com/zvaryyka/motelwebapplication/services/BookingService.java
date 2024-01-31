package com.zvaryyka.motelwebapplication.services;

import com.zvaryyka.motelwebapplication.dto.BookingDTO;
import com.zvaryyka.motelwebapplication.models.Booking;
import com.zvaryyka.motelwebapplication.repositories.BookingRepository;
import com.zvaryyka.motelwebapplication.repositories.RoomTypeRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.List;

@Service
public class BookingService {
    private final BookingRepository bookingRepository;
    private final RoomTypeRepository roomTypeRepository;//TODO Rewrite this code

    @Autowired
    public BookingService(BookingRepository bookingRepository, RoomTypeRepository roomTypeRepository) {
        this.bookingRepository = bookingRepository;
        this.roomTypeRepository = roomTypeRepository;
    }
    public List<BookingDTO> getFutureBookings(int id) {
        return bookingRepository.getFutureBookings(id);
    }
    public List<BookingDTO> getHistoryBookings(int id) {
        return bookingRepository.getHistoryBookings(id);

    }
    public List<BookingDTO> getActualBookings(int id) {
        return bookingRepository.getActualBookings(id);

    }
    public   Booking convertToBooking(BookingDTO bookingDTO, BigDecimal bigDecimal) {
        Booking booking = new Booking();
        booking.setRoomId(roomTypeRepository.findRoomIdByRoomName(bookingDTO.getRoomType()));
    }
}
