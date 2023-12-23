package com.zvaryyka.motelwebapplication.services;

import com.zvaryyka.motelwebapplication.models.Rooms;
import com.zvaryyka.motelwebapplication.repositories.RoomsRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class RoomService {
    private final RoomsRepository roomsRepository;

    @Autowired
    public RoomService(RoomsRepository roomsRepository) {
        this.roomsRepository = roomsRepository;
    }
    public List<Rooms> showAll() {
        return roomsRepository.showAll();
    }
    public List<Rooms> showFreeRooms() {
        return roomsRepository.showFreeRooms();
    }
    public void makeRoomOccupied(int id) {
        roomsRepository.makeRoomOccupied(id);
    }
    public void makeRoomFree(int id) {
        roomsRepository.makeRoomFree(id);
    }
}
