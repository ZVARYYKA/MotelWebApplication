package com.zvaryyka.motelwebapplication.repositories;

import com.zvaryyka.motelwebapplication.models.Rooms;
import com.zvaryyka.motelwebapplication.util.exceptions.RoomUnavailableException;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.stereotype.Repository;

import java.math.BigDecimal;
import java.util.List;

@Repository
public class RoomTypeRepository extends JdbcTemplateClass {
    public BigDecimal findCostByRoomType(String roomType) {
        String sql = "SELECT one_day_cost FROM Types_of_rooms WHERE room_type = ?";
        return jdbcTemplate.queryForObject(sql, new Object[]{roomType}, BigDecimal.class);
    }


    public int findRoomIdByRoomName(String roomType) {
        int typeId = findTypeIdByRoomTypeName(roomType);
        List<Rooms> rooms = findRoomsByTypeId(typeId); //TODO переписать код
        if(rooms.isEmpty()) {
            throw  new RoomUnavailableException();
        }
        else {
            return rooms.get(0).getRoomId();
        }

    }

    private List<Rooms> findRoomsByTypeId(int typeId) {
        return jdbcTemplate.query("SELECT * from rooms where type_id = ?",new Object[]{typeId},new BeanPropertyRowMapper<>(Rooms.class));

    }

    private int findTypeIdByRoomTypeName(String roomType) {
        return jdbcTemplate.queryForObject("SELECT type_id from types_of_rooms where room_type = ?",new Object[]{roomType},Integer.class);
    }
}
