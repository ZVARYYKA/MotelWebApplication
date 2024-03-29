package com.zvaryyka.motelwebapplication.dto;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.Date;

@Getter
@Setter
@NoArgsConstructor
public class BookingStatisticDTO {

    private Date date;
    private int value;
}