package com.zvaryyka.motelwebapplication.models;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.Date;

@Getter
@Setter
@NoArgsConstructor
public class Booking {
    public Booking(int bookingId, int userId, int roomId, Date checkInDate, Date checkOutDate, boolean status) {
        this.bookingId = bookingId;
        this.userId = userId;
        this.roomId = roomId;
        this.checkInDate = checkInDate;
        this.checkOutDate = checkOutDate;
        this.status = status;
    }

    private int bookingId;
    private int userId;
    private int roomId;
    private Date checkInDate;
    private Date checkOutDate;
    private boolean status;
}
