package com.zvaryyka.motelwebapplication.dto;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.format.annotation.DateTimeFormat;

import java.util.Date;


@Getter
@Setter
@NoArgsConstructor
public class PersonDTO { //TODO Rewrite CRUD with person, use PersonDTO


    private String login;

    private String name;

    private String surname;
    @DateTimeFormat(pattern = "yyyy-MM-dd")
    private Date dateOfBirth;
    private String password;
    private String role; // Выбранная роль для пользователя
    private int salary;
}
