package com.zvaryyka.motelwebapplication.services;

import com.zvaryyka.motelwebapplication.dto.ArticleDTO;
import com.zvaryyka.motelwebapplication.models.Article;
import com.zvaryyka.motelwebapplication.repositories.ArticleRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import lombok.extern.slf4j.Slf4j;

import java.util.List;

@Service
@Slf4j
public class ArticleService {
    private final ArticleRepository articleRepository;

    @Autowired
    public ArticleService(ArticleRepository articleRepository) {
        this.articleRepository = articleRepository;
    }

    public List<ArticleDTO> getAllArticleDTO() {
        return articleRepository.getAllArticleDTO();
    }

    public ArticleDTO getOneArticleDTO(int id) {
        return articleRepository.getOneArticleDTO(id);
    }

    public void createNewArticle(Article article) {
        log.info("Creating a new article: {}", article);
        articleRepository.saveArticle(article);
        log.info("New article created successfully");
    }
}
