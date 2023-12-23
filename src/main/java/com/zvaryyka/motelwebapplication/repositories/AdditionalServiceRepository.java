package com.zvaryyka.motelwebapplication.repositories;

import com.zvaryyka.motelwebapplication.models.AdditionalServices;
import com.zvaryyka.motelwebapplication.models.ServiceStatus;
import org.springframework.boot.autoconfigure.orm.jpa.JpaProperties;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.stereotype.Repository;

import java.util.List;
@Repository
public class AdditionalServiceRepository extends JdbcTemplateClass {
    public List<AdditionalServices> showAll() {
        return jdbcTemplate.query("SELECT * FROM additional_services", new BeanPropertyRowMapper<>(AdditionalServices.class));
    }

    public void addNewService(AdditionalServices additionalServices) {
        jdbcTemplate.update("INSERT INTO additional_services (user_id,service_description,status) VALUES(?,?,?)", additionalServices.getUserId(), additionalServices.getServiceDescription(),
                additionalServices.getServiceStatus());
    }
    public void updateServiceStatus(int serviceId, ServiceStatus serviceStatus) {
        String sql = "UPDATE additional_services SET service_status = ? WHERE service_id = ?";

        jdbcTemplate.update(sql, serviceStatus.name(), serviceId);
    }



}
