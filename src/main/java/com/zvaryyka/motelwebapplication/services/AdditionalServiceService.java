package com.zvaryyka.motelwebapplication.services;

import com.zvaryyka.motelwebapplication.models.AdditionalServices;
import com.zvaryyka.motelwebapplication.models.ServiceStatus;
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

    public void addNewService(AdditionalServices additionalServices) {
        additionalServices.setServiceStatus(ServiceStatus.wait);
        additionalServiceRepository.addNewService(additionalServices);
    }
    public void updateServiceStatus(int serviceId,ServiceStatus serviceStatus) { //TODO Rewrite this method,because need add response message
        additionalServiceRepository.updateServiceStatus(serviceId,serviceStatus);
    }
    public List<AdditionalServices> showAdditionalServicesByUserId(int id) {
       return additionalServiceRepository.showAdditionalServicesByUserId(id);
    }
    public List<AdditionalServices> showAll() {
        return additionalServiceRepository.showAllByStatus();
    }

    public void changeStatusOnSuccess(int id) {
        additionalServiceRepository.changeStatusOnSuccess(id);

    }

    public void changeStatusOnDenied(int id, String responseMessage) {
        additionalServiceRepository.changeStatusOnDenied(responseMessage,id);
    }
}
