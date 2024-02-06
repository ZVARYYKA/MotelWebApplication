package com.zvaryyka.motelwebapplication.services;

import com.zvaryyka.motelwebapplication.dto.ServicesDTO;
import com.zvaryyka.motelwebapplication.repositories.ServicesRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ServicesService {
    private final ServicesRepository servicesRepository;

    @Autowired
    public ServicesService(ServicesRepository servicesRepository) {
        this.servicesRepository = servicesRepository;
    }

    public List<ServicesDTO> getAllServicesDTO() {
        return servicesRepository.getAllServicesDTO();
    }



}
