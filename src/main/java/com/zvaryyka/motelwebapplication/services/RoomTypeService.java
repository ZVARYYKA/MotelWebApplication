package com.zvaryyka.motelwebapplication.services;

import com.zvaryyka.motelwebapplication.dto.BookingDTO;
import com.zvaryyka.motelwebapplication.dto.RoomDTO;
import com.zvaryyka.motelwebapplication.dto.TypeOfRoomsDTO;
import com.zvaryyka.motelwebapplication.models.Rooms;
import com.zvaryyka.motelwebapplication.models.TypeOfRooms;
import com.zvaryyka.motelwebapplication.repositories.RoomTypeRepository;
import org.springframework.stereotype.Service;
import lombok.extern.slf4j.Slf4j;

import java.math.BigDecimal;
import java.util.List;

@Service
@Slf4j
public class RoomTypeService {
    private final RoomTypeRepository roomTypeRepository;

    public RoomTypeService(RoomTypeRepository roomTypeRepository) {
        this.roomTypeRepository = roomTypeRepository;
    }

    public BigDecimal findCostByRoomType(String roomType) {
        return roomTypeRepository.findCostByRoomType(roomType);
    }

    public List<TypeOfRoomsDTO> allTypeOfRooms() {
        return roomTypeRepository.allTypeOfRooms();
    }

    public int findTypeIdByRoomTypeName(BookingDTO bookingDTO) {
        return roomTypeRepository.findTypeIdByRoomName(bookingDTO);
    }

    public int findTypeIdByRoomTypeName(String typeName) {
        return roomTypeRepository.findTypeIdByRoomName(typeName);
    }

    public List<Rooms> findRoomsByTypeIdAndDate(int typeId, BookingDTO bookingDTO) {
        return roomTypeRepository.findRoomsByTypeIdAndDate(typeId, bookingDTO);
    }

    public List<RoomDTO> getAllRoomDTO() {
        return roomTypeRepository.getAllRoomDTO();
    }

    public void addNewRoom(RoomDTO roomDTO) {
        int typeId = findTypeIdByRoomTypeName(roomDTO.getRoom_type());
        log.info("Adding new room of type {} (typeId: {})", roomDTO.getRoom_type(), typeId);
        roomTypeRepository.addNewRoom(typeId);
        log.info("New room added successfully");
    }

    public void addNewTypeOfRoom(TypeOfRooms typeOfRooms) {
        log.info("Adding new type of room: {}", typeOfRooms.getRoomType());
        roomTypeRepository.addNewTypeOfRoom(typeOfRooms);
        log.info("New type of room added successfully");
    }

    public List<TypeOfRooms> getAllTypeOfRooms() {
        return roomTypeRepository.getAllTypeOfRooms();
    }
}
