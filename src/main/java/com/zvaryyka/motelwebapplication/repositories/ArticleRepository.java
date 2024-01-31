package com.zvaryyka.motelwebapplication.repositories;

import com.zvaryyka.motelwebapplication.dto.ArticleDTO;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public class ArticleRepository extends JdbcTemplateClass {
    public List<ArticleDTO> getAllArticleDTO () {
        return jdbcTemplate.query("SELECT ar.article_id, ar.title, ar.stuff_id,ar.article ,p.name as userName, p.surname as userSurname" +
                " FROM article ar" +
                " JOIN person p ON ar.stuff_id = p.id",new BeanPropertyRowMapper<>(ArticleDTO.class));
    }


    public ArticleDTO getOneArticleDTO(int id) {
        return jdbcTemplate.queryForObject("SELECT ar.article_id, ar.title, ar.stuff_id,ar.article ,p.name as userName, p.surname as userSurname" +
                " FROM article ar" +
                "         JOIN person p ON ar.stuff_id = p.id where article_id = ?",new Object[]{id},new BeanPropertyRowMapper<>(ArticleDTO.class));
    }
}
