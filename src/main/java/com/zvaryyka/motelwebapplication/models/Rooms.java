package com.zvaryyka.motelwebapplication.models;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
public class Rooms {

    public Rooms(int roomId, int roomTypeId, boolean occupied_by) {
        this.RoomId = roomId;
        this.roomTypeId = roomTypeId;
        this.occupied_by = occupied_by;
    }
    private int RoomId;
    private int roomTypeId;
    private boolean occupied_by;

}
