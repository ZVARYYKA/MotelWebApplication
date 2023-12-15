package com.zvaryyka.motelwebapplication;

import com.zvaryyka.motelwebapplication.config.JdbcConfig;
import com.zvaryyka.motelwebapplication.models.Person;
import com.zvaryyka.motelwebapplication.repositories.PeopleRepository;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.jdbc.core.JdbcTemplate;

import java.util.Date;

@SpringBootApplication
public class MotelWebApplication {

    public static void main(String[] args) {

        SpringApplication.run(MotelWebApplication.class, args);

    }

}
