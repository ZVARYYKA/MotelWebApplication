package com.zvaryyka.motelwebapplication.repositories;

import com.zvaryyka.motelwebapplication.models.TypeOfRooms;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public class TypeOfRoomsRepository extends JdbcTemplateClass {
    public List<TypeOfRooms> showAll() {
        return jdbcTemplate.query("SELECT * FROM types_of_rooms",new BeanPropertyRowMapper<>(TypeOfRooms.class));
    }
    public void addNewTypesOfRooms(TypeOfRooms typeOfRooms) {
        jdbcTemplate.update("INSERT INTO types_of_rooms(room_type,one_day_cost) values (?,?)",typeOfRooms.getRoomType(),typeOfRooms.getOneDayCost());
    }
    public void deleteTypesOfRooms(int id ) {
        jdbcTemplate.update("DELETE types_of_id where type_id = ?",id);
    }
    public int getRoomTypeIdByName(String roomType) {
        return jdbcTemplate.queryForObject("SELECT type_id FROM types_of_rooms WHERE room_type = ?", new Object[]{roomType}, Integer.class);
    }
}
