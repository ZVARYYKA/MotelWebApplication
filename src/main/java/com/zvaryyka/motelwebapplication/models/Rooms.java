package com.zvaryyka.motelwebapplication.models;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
public class Rooms {
    //TODO конструктор класса с параметрами
    private int RoomId;
    private int roomTypeId;
    private boolean occupied_by;

}
