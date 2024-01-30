package com.zvaryyka.motelwebapplication.repositories;

import com.zvaryyka.motelwebapplication.models.FeedBack;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public class FeedBackRepository extends JdbcTemplateClass {
    public List<FeedBack> getAllFeedBacks () {
        return jdbcTemplate.query("SELECT * FROM Feedback",new BeanPropertyRowMapper<>());
    }
}
