package com.zvaryyka.motelwebapplication.util.validation;

import com.zvaryyka.motelwebapplication.models.Article;
import org.springframework.stereotype.Component;
import org.springframework.validation.Errors;
import org.springframework.validation.ValidationUtils;
import org.springframework.validation.Validator;

@Component
public class ArticleValidator implements Validator {

    @Override
    public boolean supports(Class<?> clazz) {
        return Article.class.equals(clazz);
    }

    @Override
    public void validate(Object target, Errors errors) {
        Article article = (Article) target;

        ValidationUtils.rejectIfEmptyOrWhitespace(errors, "title", "NotEmpty.article.title", "Заголовок не может быть пустым");
        ValidationUtils.rejectIfEmptyOrWhitespace(errors, "article", "NotEmpty.article.article", "Статья не может быть пустой");

        // Дополнительные проверки, если необходимо
    }
}