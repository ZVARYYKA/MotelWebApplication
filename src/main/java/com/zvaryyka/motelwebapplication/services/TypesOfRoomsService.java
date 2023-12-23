package com.zvaryyka.motelwebapplication.services;

import com.zvaryyka.motelwebapplication.models.TypeOfRooms;
import com.zvaryyka.motelwebapplication.repositories.TypeOfRoomsRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class TypesOfRoomsService {
    private final TypeOfRoomsRepository typeOfRoomsRepository;

    @Autowired
    public TypesOfRoomsService(TypeOfRoomsRepository typeOfRoomsRepository) {
        this.typeOfRoomsRepository = typeOfRoomsRepository;
    }
    public List<TypeOfRooms> showAll() {
        return typeOfRoomsRepository.showAll();
    }
    public void addNewTypesOfRooms(TypeOfRooms typeOfRooms) {
        typeOfRoomsRepository.addNewTypesOfRooms(typeOfRooms);
    }

    public void deleteTypesOfRooms(int id ) {
        typeOfRoomsRepository.deleteTypesOfRooms(id);
    }
}
