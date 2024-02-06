package com.zvaryyka.motelwebapplication.repositories;

import com.zvaryyka.motelwebapplication.dto.ServicesDTO;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public class ServicesRepository extends JdbcTemplateClass {
    public List<ServicesDTO> getAllServicesDTO() {
        return jdbcTemplate.query("SELECT service_name,service_description,cost from services", new BeanPropertyRowMapper<>(ServicesDTO.class));

    }

    public int findServiceIdByServiceName(String serviceName) {
        return jdbcTemplate.queryForObject("SELECT service_id from services where service_name = ?",
                Integer.class,
                serviceName);
    }

    public int getCostByServiceId(int serviceId) {
       return jdbcTemplate.queryForObject("SELECT cost from services where service_id =?", Integer.class,serviceId);
    }
}
