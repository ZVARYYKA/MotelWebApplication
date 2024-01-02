package com.zvaryyka.motelwebapplication.models;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.format.annotation.DateTimeFormat;

import java.util.Date;

@Getter
@Setter
@NoArgsConstructor
public class Person {
    //TODO конструктор класса с параметрами
    private int id;
    private String login;
    private String name;
    private String surname;
    @DateTimeFormat(pattern = "yyyy-MM-dd")
    private Date dateOfBirth;
    private String password;
    private String userRole;
    private int salary;


    //Поле для овнера, которому нужно выбирать
    private String StuffType;

}
