package com.zvaryyka.motelwebapplication.models;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
public class Rooms {


    private int RoomId;
    private int roomTypeId;


    @Override
    public String toString() {
        return "Rooms{" +
                "RoomId=" + RoomId +
                ", roomTypeId=" + roomTypeId +
                '}';
    }
}
