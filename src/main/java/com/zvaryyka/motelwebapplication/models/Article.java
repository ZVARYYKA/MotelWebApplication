package com.zvaryyka.motelwebapplication.models;

import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@NoArgsConstructor
@ToString
public class Article {
    private int articleId;
    @NotNull(message = "Заголовок не может быть пустым")
    private String title;
    private int stuffId;
    @NotNull(message = "Статья не может быть пустой")
    private String article;


}
