package com.zvaryyka.motelwebapplication.util.exceptions;

public class RoomUnavailableException extends RuntimeException {
    public RoomUnavailableException() {
        super("На данный момент нет доступных комнат");
    }
}