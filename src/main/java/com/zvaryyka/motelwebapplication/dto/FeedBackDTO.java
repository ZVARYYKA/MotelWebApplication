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
    @NotNull(message = "Данное поле не должно быть пустым.")
    @Min(value = 0, message = "Оценка должна быть больше 0")
    @Max(value = 6,message = "Оценка должна быть меньше 6")
    private int mark;
    private int userId;
    @NotNull(message = "Данное поле не должно быть пустым.")
    @Size(min = 10, max = 300, message = "Ваш отзыв должен состоять от 10 до 300 символов")
    private String message;
    private String userName; //Имя пользователя
    private String userSurname; //Фамилия пользователя

}
