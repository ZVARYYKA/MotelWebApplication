package com.zvaryyka.motelwebapplication.repositories;

import com.zvaryyka.motelwebapplication.dto.BookingDTO;
import com.zvaryyka.motelwebapplication.dto.RoomDTO;
import com.zvaryyka.motelwebapplication.dto.TypeOfRoomsDTO;
import com.zvaryyka.motelwebapplication.models.Rooms;
import com.zvaryyka.motelwebapplication.models.TypeOfRooms;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.stereotype.Repository;

import java.math.BigDecimal;
import java.util.Date;
import java.util.List;

@Repository
public class RoomTypeRepository extends JdbcTemplateClass {
    public BigDecimal findCostByRoomType(String roomType) {
        String sql = "SELECT one_day_cost FROM Types_of_rooms WHERE room_type = ?";
        return jdbcTemplate.queryForObject(sql, new Object[]{roomType}, BigDecimal.class);
    }


    public int findTypeIdByRoomName(BookingDTO bookingDTO) {

        return findTypeIdByRoomTypeName(bookingDTO.getRoomType());

    }
    public int findTypeIdByRoomName(String typeName) {

        return findTypeIdByRoomTypeName(typeName);

    }

    public List<Rooms> findRoomsByTypeIdAndDate(int typeId, BookingDTO bookingDTO) {
        String sql = "SELECT r.* " +
                "FROM Rooms r " +
                "WHERE r.room_type_id = ? " +
                "AND r.room_id NOT IN (" +
                "   SELECT b.room_id " +
                "   FROM Booking b " +
                "   WHERE (b.check_out_date IS NULL OR b.check_out_date >= ?) " +
                "   AND (b.check_in_date IS NULL OR b.check_in_date <= ?)" +
                ")";

        return jdbcTemplate.query(sql, new Object[]{typeId, bookingDTO.getCheckInDate(), bookingDTO.getCheckOutDate()},
                new BeanPropertyRowMapper<>(Rooms.class));
    }

    private List<Rooms> findRoomsByTypeId(int typeId) {
        return jdbcTemplate.query("SELECT * from rooms where room_type_id = ?",new Object[]{typeId},new BeanPropertyRowMapper<>(Rooms.class));

    }
    public List<TypeOfRoomsDTO> allTypeOfRooms() {
        return jdbcTemplate.query("SELECT room_type from types_of_rooms",new BeanPropertyRowMapper<>(TypeOfRoomsDTO.class));
    }

    public int findTypeIdByRoomTypeName(String roomType) {
        return jdbcTemplate.queryForObject("SELECT type_id from types_of_rooms where room_type = ?",new Object[]{roomType},Integer.class);
    }


    public List<RoomDTO> getAllRoomDTO() {
        return jdbcTemplate.query("SELECT rooms.room_id,types_of_rooms.room_type " +
                "FROM rooms join types_of_rooms on rooms.room_type_id = types_of_rooms.type_id",
                new BeanPropertyRowMapper<>(RoomDTO.class));
    }
    public void addNewRoom(int roomTypeId) {
        jdbcTemplate.update("INSERT into rooms (room_type_id) VALUES (?)",roomTypeId);
    }

    public void addNewTypeOfRoom(TypeOfRooms typeOfRooms) {
        jdbcTemplate.update("INSERT INTO types_of_rooms (room_type,one_day_cost) VALUES (?,?)",
                typeOfRooms.getRoomType(),
                typeOfRooms.getOneDayCost()
        );
    }

    public List<TypeOfRooms> getAllTypeOfRooms() {
        return jdbcTemplate.query("SELECT * FROM types_of_rooms",new BeanPropertyRowMapper<>(TypeOfRooms.class));
    }
}
