package com.zvaryyka.motelwebapplication.repositories;

import com.zvaryyka.motelwebapplication.models.Booking;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Repository;

import java.awt.print.Book;
import java.sql.ResultSet;
import java.sql.SQLException;
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
        String sql = "SELECT b.*, r.room_number AS roomNumber, tr.room_type AS roomType " +
                "FROM booking b " +
                "INNER JOIN rooms r ON b.room_id = r.room_id " +
                "INNER JOIN types_of_rooms tr ON r.room_type_id = tr.type_id " +
                "WHERE b.user_id = ? AND b.status = true";

        return jdbcTemplate.query(sql, new Object[]{id}, new BookingWithRoomRowMapper());
    }

    public List<Booking> getHistoryBookingById(int id) {
        String sql = "SELECT b.*, r.room_number AS roomNumber, tr.room_type AS roomType " +
                "FROM booking b " +
                "INNER JOIN rooms r ON b.room_id = r.room_id " +
                "INNER JOIN types_of_rooms tr ON r.room_type_id = tr.type_id " +
                "WHERE b.user_id = ? AND b.status = false AND b.check_out_date < now()";

        return jdbcTemplate.query(sql, new Object[]{id}, new BookingWithRoomRowMapper());
    }

    public List<Booking> getFutureBookingById(int id) {
        String sql = "SELECT b.*, r.room_number AS roomNumber, tr.room_type AS roomType " +
                "FROM booking b " +
                "INNER JOIN rooms r ON b.room_id = r.room_id " +
                "INNER JOIN types_of_rooms tr ON r.room_type_id = tr.type_id " +
                "WHERE b.user_id = ? AND b.status = false AND b.check_in_date > now()";

        return jdbcTemplate.query(sql, new Object[]{id}, new BookingWithRoomRowMapper());
    }
    private static class BookingWithRoomRowMapper implements RowMapper<Booking> {
        @Override
        public Booking mapRow(ResultSet rs, int rowNum) throws SQLException {
            Booking booking = new Booking();
            booking.setBookingId(rs.getInt("booking_id"));
            booking.setUserId(rs.getInt("user_id"));
            booking.setRoomId(rs.getInt("room_id"));
            booking.setCheckInDate(rs.getDate("check_in_date"));
            booking.setCheckOutDate(rs.getDate("check_out_date"));
            booking.setStatus(rs.getBoolean("status"));

            booking.setRoomNumber(rs.getString("roomNumber"));
            booking.setRoomType(rs.getString("roomType"));

            return booking;
        }
    }
    //TODO Maybe i must rewrite RowMapper and SQL requests

}
