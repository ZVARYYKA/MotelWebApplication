package com.zvaryyka.motelwebapplication.services;

import com.zvaryyka.motelwebapplication.dto.BookingDTO;
import com.zvaryyka.motelwebapplication.dto.TypeOfRoomsDTO;
import com.zvaryyka.motelwebapplication.models.Rooms;
import com.zvaryyka.motelwebapplication.repositories.RoomTypeRepository;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.List;

@Service
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
    public List<Rooms> findRoomsByTypeIdAndDate(int typeId,BookingDTO bookingDTO) {
        return roomTypeRepository.findRoomsByTypeIdAndDate(typeId,bookingDTO);
    }
}
