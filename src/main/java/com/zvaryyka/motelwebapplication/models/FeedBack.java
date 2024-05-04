package com.zvaryyka.motelwebapplication.models;

import com.zvaryyka.motelwebapplication.dto.FeedBackDTO;
import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
public class FeedBack {

    private int feedbackId;
    @NotNull(message = "Данное поле не должно быть пустым.")
    @Min(value = 0, message = "Оценка должна быть больше 0")
    @Max(value = 6,message = "Оценка должна быть меньше 6")
    private int mark;
    private int userId;
    @NotNull(message = "Данное поле не должно быть пустым.")
    @Size(min = 10, max = 300, message = "Ваш отзыв должен состоять от 10 до 300 символов")
    private String message;

    public static FeedBack convertToFeedBack(FeedBackDTO feedBackDTO) { //TODO Rewrite this code
        FeedBack feedBack = new FeedBack();
        feedBack.setUserId(feedBackDTO.getUserId());
        feedBack.setMark(feedBackDTO.getMark());
        feedBack.setMessage(feedBackDTO.getMessage());
        return feedBack;
    }

    @Override
    public String toString() {
        return "FeedBack{" +
                "feedbackId=" + feedbackId +
                ", mark=" + mark +
                ", userId=" + userId +
                ", message='" + message + '\'' +
                '}';
    }
}
