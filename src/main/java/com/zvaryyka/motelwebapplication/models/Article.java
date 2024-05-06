package com.zvaryyka.motelwebapplication.models;

import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
public class Article {
    private int articleId;
    @NotNull(message = "Заголовок не может быть пустым")
    private String title;
    private int stuffId;
    @NotNull(message = "Статья не может быть пустой")
    private String article;

    @Override
    public String toString() {
        return "Article{" +
                "articleId=" + articleId +
                ", title='" + title + '\'' +
                ", stuffId=" + stuffId +
                ", article='" + article + '\'' +
                '}';
    }
}
