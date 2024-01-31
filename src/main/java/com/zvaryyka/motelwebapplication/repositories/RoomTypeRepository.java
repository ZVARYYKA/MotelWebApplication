package com.zvaryyka.motelwebapplication.repositories;

import org.springframework.stereotype.Repository;

import java.math.BigDecimal;

@Repository
public class RoomTypeRepository extends JdbcTemplateClass {
    public BigDecimal findCostByRoomType(String roomType) {
        String sql = "SELECT one_day_cost FROM Types_of_rooms WHERE room_type = ?";
        return jdbcTemplate.queryForObject(sql, new Object[]{roomType}, BigDecimal.class);
    }


}
