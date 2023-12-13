package com.zvaryyka.motelwebapplication.models;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
public class FeedBack {
    //TODO конструктор класса с параметрами
    private int feedbackId;
    private int mark;
    private int userId;
    private String message;
}
