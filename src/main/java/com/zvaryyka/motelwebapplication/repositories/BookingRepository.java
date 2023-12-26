package com.zvaryyka.motelwebapplication.repositories;

import com.zvaryyka.motelwebapplication.models.Booking;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.stereotype.Repository;

import java.awt.print.Book;
import java.util.List;

@Repository
public class BookingRepository extends JdbcTemplateClass {
    public List<Booking> showAllActive() {
        return jdbcTemplate.query("SELECT * FROM Booking where status = true", new BeanPropertyRowMapper<>(Booking.class));
    }

    public List<Booking> showAllHistory() {
        return jdbcTemplate.query("SELECT * FROM Booking where status = false", new BeanPropertyRowMapper<>(Booking.class));
    }

    public void addNewBooking(Booking booking) {

        jdbcTemplate.update("INSERT INTO bookings(userId, roomId, checkInDate, checkOutDate, status) VALUES(?,?,?,?,?)", booking.getUserId(), booking.getRoomId(), booking.getCheckInDate(), booking.getCheckOutDate(), booking.isStatus());
    }


    public void endBooking(int id) {


        jdbcTemplate.update("UPDATE bookings SET status = false WHERE bookingId = ?", id);
    }
    public Booking getBookingById(int bookingId) {
        List<Booking> bookings = jdbcTemplate.query("SELECT * FROM Booking WHERE bookingId = ?", new Object[]{bookingId}, new BeanPropertyRowMapper<>(Booking.class));

        if (bookings != null && !bookings.isEmpty()) {
            return bookings.get(0); // Возвращаем первую найденную бронь
        } else {
            return null; // Возвращаем null, если бронь не найдена
        }
    }
    public List<Booking> getActivityBookingById(int id) {
        return jdbcTemplate.query("SELECT * FROM Booking WHERE user_id = ? and status = true",new Object[]{id},new BeanPropertyRowMapper<>(Booking.class));



    }
}
