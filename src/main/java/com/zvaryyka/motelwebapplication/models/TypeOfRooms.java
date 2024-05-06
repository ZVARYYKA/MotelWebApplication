package com.zvaryyka.motelwebapplication.models;

import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class TypeOfRooms {

    private int typeId;
    @NotNull(message = "Название типа комнаты не должно быть пустым")
    private String roomType;
    @Min(value = 0, message = "Стоимость за сутки не может быть меньше 0")
    private int oneDayCost;
}
