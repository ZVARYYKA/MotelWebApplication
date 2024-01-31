package com.zvaryyka.motelwebapplication.dto;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
public class ArticleDTO {
    private int articleId;
    private String title;
    private int stuffId;
    private String article;
    private String userName; //Имя создателя статьи
    private String userSurname; //Фамилия создателя статьи



}
