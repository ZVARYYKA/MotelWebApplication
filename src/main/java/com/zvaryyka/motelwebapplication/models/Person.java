package com.zvaryyka.motelwebapplication.models;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.Date;
@Getter
@Setter
@NoArgsConstructor
public class Person {
    //TODO конструктор класса с параметрами
    private int id;
    private String name;
    private String surname;
    private Date dateOfBirth;
    private String password;
    private String user_role;
    private int salary;

}
