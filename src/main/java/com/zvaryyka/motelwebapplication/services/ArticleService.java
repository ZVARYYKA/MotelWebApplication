package com.zvaryyka.motelwebapplication.services;

import com.zvaryyka.motelwebapplication.dto.ArticleDTO;
import com.zvaryyka.motelwebapplication.repositories.ArticleRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
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
}
