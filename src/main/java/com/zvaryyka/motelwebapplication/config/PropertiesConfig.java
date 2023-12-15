package com.zvaryyka.motelwebapplication.config;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;


@Getter
@Setter
@NoArgsConstructor
@Configuration
public class PropertiesConfig {


    @Value("${spring.datasource.driver-class-name}")
    private String sqlDriver;
    @Value("${spring.datasource.url}")
    private String dataBaseUrl;

    @Value("${spring.datasource.name}")
    private String dataBaseName;


    @Value("${spring.datasource.username}")
    private String dataBaseUserName;
    @Value("${spring.datasource.password}")
    private String dataBaseUserPassword;

}