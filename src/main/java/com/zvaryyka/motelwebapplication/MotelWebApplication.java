package com.zvaryyka.motelwebapplication;

import com.zvaryyka.motelwebapplication.config.JdbcConfig;
import com.zvaryyka.motelwebapplication.models.Person;
import com.zvaryyka.motelwebapplication.repositories.PeopleRepository;
import com.zvaryyka.motelwebapplication.services.EmailSenderService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.context.event.ApplicationReadyEvent;
import org.springframework.context.event.EventListener;
import org.springframework.jdbc.core.JdbcTemplate;

import java.util.Date;

@SpringBootApplication
public class MotelWebApplication {
    private final EmailSenderService senderService;

    @Autowired
    public MotelWebApplication(EmailSenderService senderService) {
        this.senderService = senderService;
    }

    public static void main(String[] args) {

        SpringApplication.run(MotelWebApplication.class, args);

    }
//    @EventListener(ApplicationReadyEvent.class)
//    public void sendMail() {
//        senderService.sendEmail("nzvarykin@gmail.com",
//                "This is subject",
//                "This is body");
//    }

}
