package com.zvaryyka.motelwebapplication.services;

import com.zvaryyka.motelwebapplication.dto.ServicesDTO;
import com.zvaryyka.motelwebapplication.models.Services;
import com.zvaryyka.motelwebapplication.repositories.ServicesRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import lombok.extern.slf4j.Slf4j;

import java.util.List;

@Service
@Slf4j
public class ServicesService {
    private final ServicesRepository servicesRepository;

    @Autowired
    public ServicesService(ServicesRepository servicesRepository) {
        this.servicesRepository = servicesRepository;
    }

    public List<ServicesDTO> getAllServicesDTO() {
        return servicesRepository.getAllServicesDTO();
    }

    public void addNewService(Services services) {
        log.info("Adding new service: {}", services.getServiceName());
        servicesRepository.addNewService(services);
        log.info("New service added successfully");
    }
}
