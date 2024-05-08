package com.zvaryyka.motelwebapplication.dto;

import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
public class FeedBackDTO {

    private int mark;
    private int userId;

    private String message;
    private String userName; //Имя пользователя
    private String userSurname; //Фамилия пользователя

}
