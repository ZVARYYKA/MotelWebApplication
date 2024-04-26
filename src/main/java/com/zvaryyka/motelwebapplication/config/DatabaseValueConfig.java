package com.zvaryyka.motelwebapplication.config;

import lombok.Getter;
import lombok.Setter;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

@Setter
@Getter
@Component
@ConfigurationProperties(prefix = "data")
public class DatabaseValueConfig {
    private String jdbcDriver = "org.postgresql.Driver";
    private String databaseUrl = "jdbc:postgresql://***";
    private String databaseUsername = "***";
    private String databasePassword = "***";

}
