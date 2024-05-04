package com.zvaryyka.motelwebapplication.services;

import com.zvaryyka.motelwebapplication.dto.AdditionalServicesDTO;
import com.zvaryyka.motelwebapplication.models.AdditionalServices;
import com.zvaryyka.motelwebapplication.models.Person;
import com.zvaryyka.motelwebapplication.repositories.AdditionalServiceRepository;
import com.zvaryyka.motelwebapplication.repositories.BookingRepository;
import com.zvaryyka.motelwebapplication.repositories.ServicesRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Collections;
import java.util.List;

@Service
@Slf4j
public class AdditionalServiceService {
    private final AdditionalServiceRepository additionalServiceRepository;
    private final BookingRepository bookingRepository;
    private final ServicesRepository servicesRepository;

    @Autowired
    public AdditionalServiceService(AdditionalServiceRepository additionalServiceRepository, BookingService bookingService, ServicesService servicesService, BookingRepository bookingRepository, ServicesRepository servicesRepository) {
        this.additionalServiceRepository = additionalServiceRepository;
        this.bookingRepository = bookingRepository;
        this.servicesRepository = servicesRepository;
    }

    public List<AdditionalServicesDTO> getAllAdditionalServicesWhereStatusFalse() {
        return additionalServiceRepository.getAllAdditionalServicesWhereStatusFalse();
    }

    public void changeStatusForAdditionalService(int id) {
        log.info("Changing status for additional service with id {}", id);
        additionalServiceRepository.changeStatusForAdditionalService(id);
        log.info("Status changed successfully for additional service with id {}", id);
    }

    public void save(AdditionalServicesDTO additionalServicesDTO, Person person) {
        log.info("Saving additional service for person with id {}", person.getId());
        additionalServicesDTO.setServiceId(servicesRepository.findServiceIdByServiceName(
                additionalServicesDTO.getServiceName()));
        additionalServicesDTO.setBookingId(bookingRepository.getActualBookingId(person.getId()));
        additionalServiceRepository.save(convertToAdditionalService(additionalServicesDTO));
        log.info("Additional service saved successfully for person with id {}", person.getId());
    }

    private AdditionalServices convertToAdditionalService(AdditionalServicesDTO additionalServicesDTO) {
        AdditionalServices additionalServices = new AdditionalServices();
        additionalServices.setServiceId(additionalServicesDTO.getServiceId());
        additionalServices.setBookingId(additionalServicesDTO.getBookingId());
        additionalServices.setComment(additionalServicesDTO.getComment());
        return additionalServices;
    }

    public List<AdditionalServicesDTO> getUserAdditionalServicesWhereStatusFalse(int userId) {
        Integer bookingId = bookingRepository.getActualBookingId(userId);
        if (bookingId == null) {
            return Collections.emptyList(); // Возвращаем пустой список, если бронирование не найдено
        }
        return additionalServiceRepository.getUserAdditionalServicesWhereStatusFalse(bookingId);
    }

    public List<AdditionalServicesDTO> getUserAdditionalServicesWhereStatusTrue(int userId) {
        Integer bookingId = bookingRepository.getActualBookingId(userId);
        if (bookingId == null) {
            return Collections.emptyList(); // Возвращаем пустой список, если бронирование не найдено
        }
        return additionalServiceRepository.getUserAdditionalServicesWhereStatusTrue(bookingId);
    }

    public void delete(int id) {
        log.info("Deleting additional service with id {}", id);
        additionalServiceRepository.delete(id);
        log.info("Additional service deleted successfully with id {}", id);
    }
}
