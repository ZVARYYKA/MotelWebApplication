package com.zvaryyka.motelwebapplication.dto;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
public class ServicesDTO {
    private String serviceName;
    private String serviceDescription;
    private int cost;
}