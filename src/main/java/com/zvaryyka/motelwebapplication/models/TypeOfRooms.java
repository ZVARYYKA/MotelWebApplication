package com.zvaryyka.motelwebapplication.models;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
public class TypeOfRooms {
    //TODO конструктор класса с параметрами
    private int typeId;
    private String roomType;
    private int oneDayCost;
}
