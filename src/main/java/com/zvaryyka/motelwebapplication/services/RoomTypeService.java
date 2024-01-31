package com.zvaryyka.motelwebapplication.services;

import com.zvaryyka.motelwebapplication.repositories.RoomTypeRepository;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;

@Service
public class RoomTypeService {
    private final RoomTypeRepository roomTypeRepository;

    public RoomTypeService(RoomTypeRepository roomTypeRepository) {
        this.roomTypeRepository = roomTypeRepository;
    }
    public BigDecimal findCostByRoomType(String roomType) {
        return roomTypeRepository.findCostByRoomType(roomType);
    }
}
