package com.zvaryyka.motelwebapplication.models;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
public class Services {
    private int serviceId;
    private String serviceName;
    private String serviceDescription;
    private int cost; //May Rewrite
}
