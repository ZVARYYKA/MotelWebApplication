package com.zvaryyka.motelwebapplication.models;

import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;
import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@ToString
public class TypeOfRooms {

    private int typeId;
    @NotNull(message = "Название типа комнаты не должно быть пустым")
    private String roomType;
    @Min(value = 0, message = "Стоимость за сутки не может быть меньше 0")
    private int oneDayCost;
}
