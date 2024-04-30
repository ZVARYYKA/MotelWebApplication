package com.zvaryyka.motelwebapplication.models;

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
    private String roomType;
    private int oneDayCost;
}
