package com.zvaryyka.motelwebapplication.services;

import com.zvaryyka.motelwebapplication.dto.AdditionalServicesDTO;
import com.zvaryyka.motelwebapplication.repositories.AdditionalServiceRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class AdditionalServiceService {
    private final AdditionalServiceRepository additionalServiceRepository;

    @Autowired
    public AdditionalServiceService(AdditionalServiceRepository additionalServiceRepository) {
        this.additionalServiceRepository = additionalServiceRepository;
    }

    public List<AdditionalServicesDTO> getAllAdditionalServicesWhereStatusFalse() {
        return additionalServiceRepository.getAllAdditionalServicesWhereStatusFalse();
    }

    public void changeStatusForAdditionalService(int id) {
        additionalServiceRepository.changeStatusForAdditionalService(id);
    }
}
