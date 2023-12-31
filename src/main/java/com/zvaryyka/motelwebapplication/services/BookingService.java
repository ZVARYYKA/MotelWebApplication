package com.zvaryyka.motelwebapplication.services;

import com.zvaryyka.motelwebapplication.models.Booking;
import com.zvaryyka.motelwebapplication.models.Rooms;
import com.zvaryyka.motelwebapplication.repositories.BookingRepository;
import com.zvaryyka.motelwebapplication.repositories.RoomsRepository;
import com.zvaryyka.motelwebapplication.repositories.TypeOfRoomsRepository;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class BookingService {
    private final BookingRepository bookingRepository;
    private final RoomsRepository roomRepository;

    private final TypeOfRoomsRepository typeOfRoomsRepository;

    //TODO ReWrite logic, use RoomService instead of RoomRepository
    public BookingService(BookingRepository bookingRepository, RoomsRepository roomRepository, TypeOfRoomsRepository typeOfRoomsRepository) {
        this.bookingRepository = bookingRepository;
        this.roomRepository = roomRepository;
        this.typeOfRoomsRepository = typeOfRoomsRepository;
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

    public void addNewBooking(Booking addBooking, int userId) {
        //TODO Maybe ReWrite this code
        //Поиск id типа комнаты по названию
        int roomTypeId = typeOfRoomsRepository.getRoomTypeIdByName(addBooking.getRoomType());
        //Поиск всех комнат по типу комнаты
        List<Rooms> rooms = roomRepository.showAllByTypeId(roomTypeId);
        //Получение всех бронирований
        List<Booking> bookings = bookingRepository.showAll();

        List<Rooms> availableRooms = new ArrayList<>();

        for (Rooms room : rooms) {
            boolean isRoomAvailable = true;

            // Проверка наличия свободной комнаты в указанный период
            for (Booking booking : bookings) {
                if (booking.getRoomId() == room.getRoomId()) {
                    // Проверка пересечения дат
                    if (booking.getCheckOutDate().after(addBooking.getCheckInDate()) &&
                            booking.getCheckInDate().before(addBooking.getCheckOutDate())) {
                        isRoomAvailable = false;
                        break;
                    }
                }
            }

            if (isRoomAvailable) {
                availableRooms.add(room);
            }
        }
        if(availableRooms.isEmpty()) {
            throw new RuntimeException("No available rooms at the moment.");
        } else {
            // Занимаем первую доступную комнату и делаем бронирование
            Rooms roomToBook = availableRooms.get(0);
            // roomRepository.makeRoomOccupied(roomToBook.getRoomId());
            addBooking.setRoomId(roomToBook.getRoomId());
            addBooking.setUserId(userId);
            bookingRepository.addNewBooking(addBooking);
        }
    }
}
