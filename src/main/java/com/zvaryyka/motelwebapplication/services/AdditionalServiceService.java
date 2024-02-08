package com.zvaryyka.motelwebapplication.services;

import com.zvaryyka.motelwebapplication.dto.AdditionalServicesDTO;
import com.zvaryyka.motelwebapplication.models.AdditionalServices;
import com.zvaryyka.motelwebapplication.models.Person;
import com.zvaryyka.motelwebapplication.repositories.AdditionalServiceRepository;
import com.zvaryyka.motelwebapplication.repositories.BookingRepository;
import com.zvaryyka.motelwebapplication.repositories.ServicesRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Collections;
import java.util.List;

@Service
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
        additionalServiceRepository.changeStatusForAdditionalService(id);
    }

    public void save(AdditionalServicesDTO additionalServicesDTO, Person person) {
        additionalServicesDTO.setServiceId(servicesRepository.findServiceIdByServiceName(
                additionalServicesDTO.getServiceName()));
        additionalServicesDTO.setBookingId(bookingRepository.getActualBookingId(person.getId()));
        additionalServiceRepository.save(convertToAdditionalService(additionalServicesDTO));

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
        additionalServiceRepository.delete(id);
    }
}
