package com.zvaryyka.motelwebapplication.repositories;

import com.zvaryyka.motelwebapplication.models.Rooms;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public class RoomsRepository extends JdbcTemplateClass {
    public List<Rooms> showAll() {
        return jdbcTemplate.query("SELECT * FROM rooms", new BeanPropertyRowMapper<>(Rooms.class));

    }

    public List<Rooms> showFreeRooms() {
        //occupatied by is boolean
        return jdbcTemplate.query("SELECT * FROM rooms WHERE occupatied_by IS false", new BeanPropertyRowMapper<>(Rooms.class));

    }

    public void makeRoomOccupied(int id) {
        jdbcTemplate.update("UPDATE rooms SET occupatied_by = true, WHERE id = ?", id);
    }
    public void makeRoomFree(int id) {
        jdbcTemplate.update("UPDATE rooms SET occupatied_by = false, WHERE id = ?", id);
    }
    //TODO add add method

}
