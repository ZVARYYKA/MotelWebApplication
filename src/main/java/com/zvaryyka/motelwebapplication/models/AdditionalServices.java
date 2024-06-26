package com.zvaryyka.motelwebapplication.models;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
public class AdditionalServices {
    private int addServiceId;
    private int bookingId;
    private int serviceId;
    private String comment;
    private boolean status;

    @Override
    public String toString() {
        return "AdditionalServices{" +
                "addServiceId=" + addServiceId +
                ", bookingId=" + bookingId +
                ", serviceId=" + serviceId +
                ", comment='" + comment + '\'' +
                ", status=" + status +
                '}';
    }
}

