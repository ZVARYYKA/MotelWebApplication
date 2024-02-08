package com.zvaryyka.motelwebapplication.repositories;

import com.zvaryyka.motelwebapplication.dto.BookingDTO;
import com.zvaryyka.motelwebapplication.dto.BookingStatisticDTO;
import com.zvaryyka.motelwebapplication.models.Booking;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.stereotype.Repository;

import java.util.Date;
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
                booking.getCheckOutDate(), booking.getSummary_cost());
    }

    public Integer getActualBookingId(int userId) {
        String sql = "SELECT b.booking_id " +
                "FROM Booking b " +
                "WHERE b.user_id = ? " +
                "AND b.check_in_date <= CURRENT_DATE " +
                "AND b.check_out_date >= CURRENT_DATE";

        List<Integer> bookingIds = jdbcTemplate.queryForList(sql, Integer.class, userId);
        return bookingIds.isEmpty() ? null : bookingIds.get(0);
    }

    public void costPlusSummaryCost(int bookingId, int cost) {
        jdbcTemplate.update(
                "UPDATE Booking SET summary_cost = summary_cost + ? WHERE booking_id = ?",
                cost, bookingId);
    }

    public List<BookingStatisticDTO> getBookingStatistics(Date startDate, Date endDate) {
        String sql = "SELECT check_in_date AS booking_date, COUNT(*) AS count FROM Booking WHERE check_in_date BETWEEN ? AND ? GROUP BY check_in_date";

        // Выполняем запрос к базе данных и маппим результаты в DTO объекты
        return jdbcTemplate.query(sql, new Object[]{startDate, endDate}, (rs, rowNum) -> {
            BookingStatisticDTO statisticDTO = new BookingStatisticDTO();
            statisticDTO.setDate(rs.getDate("booking_date"));
            statisticDTO.setValue(rs.getInt("count"));
            return statisticDTO;
        });
    }

    public Double getTotalRevenue(Date startDate, Date endDate) {
        String sql = "SELECT SUM(summary_cost) FROM booking WHERE check_in_date BETWEEN ? AND ?";
        return jdbcTemplate.queryForObject(sql, Double.class, startDate, endDate);
    }

    public Integer getTotalBookingsCount(Date startDate, Date endDate) {
        String sql = "SELECT COUNT(*) FROM booking WHERE check_in_date BETWEEN ? AND ?";
        return jdbcTemplate.queryForObject(sql, Integer.class, startDate, endDate);
    }
}
