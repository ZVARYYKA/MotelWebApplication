package com.zvaryyka.motelwebapplication.models;

import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
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
    @NotNull(message = "Данное поле не должно быть пустым.")
    @Size(min = 2, max = 30, message = "Логин должен состоять от 2 до 30 символов")
    private String login;
    @NotNull(message = "Данное поле не должно быть пустым.")
    @Size(min = 2, max = 30, message = "Имя должен состоять от 2 до 30 символов")
    private String name;
    @NotNull(message = "Данное поле не должно быть пустым.")
    @Size(min = 2, max = 30, message = "Фамилия должен состоять от 2 до 30 символов")
    private String surname;
    @DateTimeFormat(pattern = "yyyy-MM-dd")
    @NotNull(message = "Данное поле не должно быть пустым.")
    private Date dateOfBirth;
    @NotNull(message = "Данное поле не должно быть пустым.")
    private String password;
    private String userRole;

    private int salary;


    //Поле для овнера, которому нужно выбирать
    @NotNull(message = "Поле не должно быть пустым")
    private String StuffType;

}
