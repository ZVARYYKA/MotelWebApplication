package com.zvaryyka.motelwebapplication.models;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
public class AdditionalServices {
    //TODO конструктор класса с параметрами
    private int serviceId;
    private int userId;

    private String serviceDescription;
    private ServiceStatus serviceStatus;
}

