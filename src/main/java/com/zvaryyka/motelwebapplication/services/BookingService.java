package com.zvaryyka.motelwebapplication.services;

import com.zvaryyka.motelwebapplication.models.Booking;
import com.zvaryyka.motelwebapplication.models.Rooms;
import com.zvaryyka.motelwebapplication.repositories.BookingRepository;
import com.zvaryyka.motelwebapplication.repositories.RoomsRepository;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class BookingService {
    private final BookingRepository bookingRepository;
    private final RoomsRepository roomRepository;

    //TODO ReWrite logic, use RoomService instead of RoomRepository
    public BookingService(BookingRepository bookingRepository, RoomsRepository roomRepository) {
        this.bookingRepository = bookingRepository;
        this.roomRepository = roomRepository;
    }

    // Добавление метода для проверки и занятия свободной комнаты
    public void bookAvailableRoom(Booking booking) {
        List<Rooms> freeRooms = roomRepository.showFreeRooms();

        if (freeRooms.isEmpty()) {
            throw new RuntimeException("No available rooms at the moment.");
        } else {
            // Занимаем первую доступную комнату и делаем бронирование
            Rooms roomToBook = freeRooms.get(0);
            roomRepository.makeRoomOccupied(roomToBook.getRoomId());
            booking.setRoomId(roomToBook.getRoomId());
            bookingRepository.addNewBooking(booking);
        }
    }

    // Метод для освобождения комнаты после окончания бронирования
    public void releaseRoom(int bookingId) {
        Booking booking = bookingRepository.getBookingById(bookingId);

        if (booking != null) {
            roomRepository.makeRoomFree(booking.getRoomId());
            bookingRepository.endBooking(bookingId);
        } else {
            throw new RuntimeException("Booking not found.");
        }
    }

    // Остальные методы остаются без изменений
    public List<Booking> showAllActive() {
        return bookingRepository.showAllActive();
    }

    public List<Booking> showAllHistory() {
        return bookingRepository.showAllHistory();
    }

    public List<Booking> getActivityBookingById(int id) {
        return bookingRepository.getActivityBookingById(id);


    }

    public List<Booking> getHistoryBookingById(int id) {
        return bookingRepository.getHistoryBookingById(id);


    }

    public List<Booking> getFutureBookingById(int id) {
        return bookingRepository.getFutureBookingById(id);

    }
}
