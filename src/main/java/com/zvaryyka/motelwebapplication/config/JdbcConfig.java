package com.zvaryyka.motelwebapplication.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.jdbc.datasource.DriverManagerDataSource;

import javax.sql.DataSource;

@Configuration
public class JdbcConfig {

    private final DatabaseValueConfig dbConfig = new DatabaseValueConfig();

    @Bean
    public DataSource postgreDataSource() {

        DriverManagerDataSource dataSource = new DriverManagerDataSource();
        dataSource.setDriverClassName(dbConfig.getJdbcDriver());
        dataSource.setUrl(dbConfig.getDatabaseUrl());
        dataSource.setUsername(dbConfig.getDatabaseUsername());
        dataSource.setPassword(dbConfig.getDatabasePassword());
        return dataSource;
    }
}
