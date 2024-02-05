package com.zvaryyka.motelwebapplication.repositories;

import com.zvaryyka.motelwebapplication.dto.AdditionalServicesDTO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public class AdditionalServiceRepository extends JdbcTemplateClass {


    public List<AdditionalServicesDTO> getAllAdditionalServicesDTO() {
        return jdbcTemplate.query("SELECT additional_services.add_service_id," +
                "additional_services.booking_id," +
                "additional_services.service_id," +
                "additional_services.comment," +
                "additional_services.status," +
                "service_name,service_description " +
                "From additional_services join services s on additional_services.service_id = s.service_id", new BeanPropertyRowMapper<>(AdditionalServicesDTO.class));
    }

    public List<AdditionalServicesDTO> getAllAdditionalServicesWhereStatusFalse() {
        return jdbcTemplate.query(
                "SELECT additional_services.add_service_id, " +
                        "additional_services.booking_id, " +
                        "booking.room_id, " + // Добавляем room_id из таблицы Booking
                        "additional_services.service_id, " +
                        "additional_services.comment, " +
                        "additional_services.status, " +
                        "service_name, " +
                        "service_description " +
                        "FROM additional_services " +
                        "JOIN services s ON additional_services.service_id = s.service_id " +
                        "JOIN booking ON additional_services.booking_id = booking.booking_id " +
                        "WHERE additional_services.status = ?",
                new Object[]{false},
                new BeanPropertyRowMapper<>(AdditionalServicesDTO.class)
        );
    }

    public void changeStatusForAdditionalService(int id) {
        jdbcTemplate.update("UPDATE additional_services SET status = true WHERE add_service_id = ?;", id);
    }
}
