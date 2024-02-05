package com.zvaryyka.motelwebapplication.util.validation;

import com.zvaryyka.motelwebapplication.dto.BookingDTO;
import com.zvaryyka.motelwebapplication.models.Person;
import com.zvaryyka.motelwebapplication.models.Rooms;
import com.zvaryyka.motelwebapplication.services.BookingService;
import com.zvaryyka.motelwebapplication.services.RoomTypeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.validation.Errors;
import org.springframework.validation.Validator;

import java.util.Calendar;
import java.util.Date;
import java.util.List;

@Component
public class BookingValidator implements Validator { //TODO ReWrite method in RoomTypeService and change validator

    private final RoomTypeService roomTypeService;
    private static final int MIN_ADVANCE_BOOKING_HOURS = 24;

    @Autowired
    public BookingValidator(RoomTypeService roomTypeService) {
        this.roomTypeService = roomTypeService;
    }

    @Override
    public boolean supports(Class<?> aClass) {
        return BookingDTO.class.equals(aClass);
    }

    @Override
    public void validate(Object o, Errors errors) {
        BookingDTO bookingDTO = (BookingDTO) o;

        validateCheckInDate(bookingDTO, errors);
        validateCheckOutDate(bookingDTO, errors);
        validateAdvanceBooking(bookingDTO, errors);
        validateRoomAvailability(bookingDTO, errors);
    }

    private void validateCheckInDate(BookingDTO bookingDTO, Errors errors) {
        if (bookingDTO.getCheckInDate() != null && bookingDTO.getCheckInDate().before(new Date())) {
            errors.rejectValue("checkInDate", "DateInThePast", "Дата не может быть в прошлом");
        }
    }

    private void validateCheckOutDate(BookingDTO bookingDTO, Errors errors) {
        if (bookingDTO.getCheckOutDate() != null && bookingDTO.getCheckInDate() != null
                && bookingDTO.getCheckOutDate().before(bookingDTO.getCheckInDate())) {
            errors.rejectValue("checkOutDate", "InvalidDates", "Дата окончания не может быть раньше чем дата начала");
        }
    }

    private void validateAdvanceBooking(BookingDTO bookingDTO, Errors errors) {
        Calendar calendar = Calendar.getInstance();
        calendar.add(Calendar.HOUR_OF_DAY, MIN_ADVANCE_BOOKING_HOURS);
        Date minAdvanceBookingDate = calendar.getTime();

        if (bookingDTO.getCheckInDate() != null && bookingDTO.getCheckInDate().before(minAdvanceBookingDate)) {
            errors.rejectValue("checkInDate", "AdvanceBookingRequired",
                    "Бронирование можно совершать за 24 часа");
        }
    }

    private void validateRoomAvailability(BookingDTO bookingDTO, Errors errors) {
            int typeId = roomTypeService.findTypeIdByRoomTypeName(bookingDTO);

            List<Rooms> availableRooms = roomTypeService.findRoomsByTypeIdAndDate(typeId, bookingDTO);



        if (availableRooms.isEmpty()) {
            errors.rejectValue("roomType", "RoomNotAvailable", "На данную дату нет свободных комнат");
        }
    }
}
