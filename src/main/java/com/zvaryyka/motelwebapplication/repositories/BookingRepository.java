package com.zvaryyka.motelwebapplication.repositories;

import com.zvaryyka.motelwebapplication.dto.BookingDTO;
import com.zvaryyka.motelwebapplication.models.Booking;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public class BookingRepository extends JdbcTemplateClass { //TODO Maybe rewrite this code

    public List<BookingDTO> getFutureBookings(int userId) {
        String sql = "SELECT b.user_id, b.room_id, b.check_in_date, b.check_out_date, t.room_type " +
                "FROM Booking b " +
                "INNER JOIN Rooms r ON b.room_id = r.room_id " +
                "INNER JOIN Types_of_rooms t ON r.room_type_id = t.type_id " +
                "WHERE b.user_id = ? AND b.check_in_date > CURRENT_DATE";
        return jdbcTemplate.query(sql, new Object[]{userId}, BeanPropertyRowMapper.newInstance(BookingDTO.class));
    }

    public List<BookingDTO> getHistoryBookings(int userId) {
        String sql = "SELECT b.user_id, b.room_id, b.check_in_date, b.check_out_date, t.room_type " +
                "FROM Booking b " +
                "INNER JOIN Rooms r ON b.room_id = r.room_id " +
                "INNER JOIN Types_of_rooms t ON r.room_type_id = t.type_id " +
                "WHERE b.user_id = ? AND b.check_out_date < CURRENT_DATE";
        return jdbcTemplate.query(sql, new Object[]{userId}, BeanPropertyRowMapper.newInstance(BookingDTO.class));
    }

    public List<BookingDTO> getActualBookings(int userId) {
        String sql = "SELECT b.user_id, b.room_id, b.check_in_date, b.check_out_date, t.room_type " +
                "FROM Booking b " +
                "INNER JOIN Rooms r ON b.room_id = r.room_id " +
                "INNER JOIN Types_of_rooms t ON r.room_type_id = t.type_id " +
                "WHERE b.user_id = ? AND b.check_in_date <= CURRENT_DATE AND b.check_out_date >= CURRENT_DATE";
        return jdbcTemplate.query(sql, new Object[]{userId}, BeanPropertyRowMapper.newInstance(BookingDTO.class));
    }

    public void save(Booking booking) {
         jdbcTemplate.update("INSERT INTO booking(user_id,room_id,check_in_date,check_out_date,summary_cost) VALUES(?,?,?,?,?)",
                 booking.getUserId(),
                 booking.getRoomId(),
                 booking.getCheckInDate(),
                 booking.getCheckOutDate(),booking.getSummary_cost());
    }
}
