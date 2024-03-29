package com.zvaryyka.motelwebapplication.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.jdbc.datasource.DriverManagerDataSource;

import javax.sql.DataSource;

@Configuration

public class JdbcConfig {


    @Bean
    public static DataSource postgreDataSource() {

        //TODO change string to properties config
        DriverManagerDataSource dataSource = new DriverManagerDataSource();
        dataSource.setDriverClassName("org.postgresql.Driver");
        dataSource.setUrl("jdbc:postgresql://**/motel_db");
        dataSource.setUsername("***");
        dataSource.setPassword("****"); //postgres or adm
        return dataSource;
    }
}
