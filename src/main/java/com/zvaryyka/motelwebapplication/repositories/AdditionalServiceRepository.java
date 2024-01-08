package com.zvaryyka.motelwebapplication.repositories;

import com.zvaryyka.motelwebapplication.models.AdditionalServices;
import com.zvaryyka.motelwebapplication.models.ServiceStatus;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Repository;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;
@Repository
public class AdditionalServiceRepository extends JdbcTemplateClass {
    public List<AdditionalServices> showAllByStatus() {
        return jdbcTemplate.query("SELECT * FROM additional_services where status = 'wait'",new AdditionalServicesRowMapper());
    }

    public void addNewService(AdditionalServices additionalServices) {
        jdbcTemplate.update("INSERT INTO additional_services (user_id, service_description, status) VALUES (?, ?, CAST(? AS service_status))",
                additionalServices.getUserId(), additionalServices.getServiceDescription(), additionalServices.getServiceStatus().name());
    }
    public void updateServiceStatus(int serviceId, ServiceStatus serviceStatus) {

        jdbcTemplate.update("UPDATE additional_services SET service_status = ? WHERE service_id = ?", serviceStatus.name(), serviceId);
    }


    public List<AdditionalServices> showAdditionalServicesByUserId(int id) {
        return jdbcTemplate.query(
                "SELECT * FROM additional_services WHERE user_id = ?",
                new Object[]{id},
                new AdditionalServicesRowMapper()
        );
    }

    public void changeStatusOnSuccess(int id) {
        jdbcTemplate.update("UPDATE additional_services set status = 'success' where service_id = ?",id);
    }
    public void changeStatusOnDenied(String message, int id) {
        jdbcTemplate.update(
                "UPDATE additional_services SET status = 'denied', response_messages = ? WHERE service_id = ?",
                message,
                id
        );
    }
}
class AdditionalServicesRowMapper implements RowMapper<AdditionalServices> {

    @Override
    public AdditionalServices mapRow(ResultSet rs, int rowNum) throws SQLException {
        AdditionalServices additionalService = new AdditionalServices();
        additionalService.setServiceId(rs.getInt("service_id"));
        additionalService.setUserId(rs.getInt("user_id"));
        additionalService.setServiceDescription(rs.getString("service_description"));
        additionalService.setResponseMessage(rs.getString("response_messages"));
        String status = rs.getString("status");

        if (status != null) {
            additionalService.setServiceStatus(ServiceStatus.valueOf(status));
        }
        return additionalService;
    }
}
