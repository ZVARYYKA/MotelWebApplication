package com.zvaryyka.motelwebapplication.models;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.Date;

@Getter
@Setter
@NoArgsConstructor
public class Booking {
    //TODO конструктор класса с параметрами
    private int bookingId;
    private int userId;
    private int roomId;
    private Date checkInDate;
    private Date checkOutDate;
    private boolean status;
}
