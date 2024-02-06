package com.zvaryyka.motelwebapplication.services;

import com.zvaryyka.motelwebapplication.dto.BookingDTO;
import com.zvaryyka.motelwebapplication.models.Booking;
import com.zvaryyka.motelwebapplication.models.Rooms;
import com.zvaryyka.motelwebapplication.repositories.BookingRepository;
import com.zvaryyka.motelwebapplication.repositories.RoomTypeRepository;
import com.zvaryyka.motelwebapplication.repositories.ServicesRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.List;

@Service
public class BookingService {
    private final BookingRepository bookingRepository;
    private final RoomTypeRepository roomTypeRepository;
    private final ServicesRepository servicesRepository;
    @Autowired
    public BookingService(BookingRepository bookingRepository,
                          RoomTypeRepository roomTypeRepository,
                          ServicesService servicesService, ServicesRepository servicesRepository) {
        this.bookingRepository = bookingRepository;
        this.roomTypeRepository = roomTypeRepository;

        this.servicesRepository = servicesRepository;
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

    public Booking convertToBooking(BookingDTO bookingDTO, BigDecimal bigDecimal) {
        Booking booking = new Booking();
        List<Rooms> rooms = roomTypeRepository.findRoomsByTypeIdAndDate(roomTypeRepository.findTypeIdByRoomName(bookingDTO),
                bookingDTO);
        booking.setRoomId(rooms.get(0).getRoomId());
        booking.setCheckInDate(bookingDTO.getCheckInDate());
        booking.setCheckOutDate(bookingDTO.getCheckOutDate());
        booking.setUserId(bookingDTO.getUserId());
        booking.setSummary_cost(bigDecimal.intValue()); //TODO REWRITE SHIT CODE
        return booking;

    }

    public void save(Booking booking) {
        bookingRepository.save(booking);

    }


    public void serviceCostPlusSummaryCostInBooking(int bookingId, int serviceId) {
        int cost = servicesRepository.getCostByServiceId(serviceId);
        bookingRepository.costPlusSummaryCost(bookingId,cost);
    }
}
