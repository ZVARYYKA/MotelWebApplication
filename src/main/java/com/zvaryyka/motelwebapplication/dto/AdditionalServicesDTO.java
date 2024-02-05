package com.zvaryyka.motelwebapplication.dto;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
public class AdditionalServicesDTO {
    private int addServiceId;
    private int bookingId;
    private int roomId; //Номер комнаты
    private int serviceId;
    private String comment;
    private boolean status;
    private String serviceName; //Имя сервиса
    private String serviceDescription; //Описание сервиса
}
