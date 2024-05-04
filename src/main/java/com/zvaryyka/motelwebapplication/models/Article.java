package com.zvaryyka.motelwebapplication.models;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
public class Article {
    private int articleId;
    private String title;
    private int stuffId;
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
