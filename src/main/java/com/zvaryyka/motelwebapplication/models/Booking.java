package com.zvaryyka.motelwebapplication.models;

import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.format.annotation.DateTimeFormat;

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
    @DateTimeFormat(pattern = "yyyy-MM-dd")
    @NotNull(message = "Данное поле не должно быть пустым.")
    private Date checkInDate;
    @DateTimeFormat(pattern = "yyyy-MM-dd")
    private Date checkOutDate;
    private boolean status;

    private String roomNumber;
    @NotNull(message = "Данное поле не должно быть пустым.")
    private String roomType;
}
