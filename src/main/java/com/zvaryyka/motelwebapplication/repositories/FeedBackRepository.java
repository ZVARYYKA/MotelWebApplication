package com.zvaryyka.motelwebapplication.repositories;

import com.zvaryyka.motelwebapplication.models.FeedBack;
import com.zvaryyka.motelwebapplication.models.Person;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.stereotype.Repository;

import java.util.List;
@Repository
public class FeedBackRepository extends JdbcTemplateClass {
    public List<FeedBack> getAllFeedBacks() {
        return jdbcTemplate.query("SELECT * FROM feedback", new BeanPropertyRowMapper<>(FeedBack.class));
    }

    public void save(FeedBack feedBack) {
        jdbcTemplate.update("INSERT INTO feedback (mark,user_id,message) VALUES (?, ?,?)",
                feedBack.getMark(), feedBack.getUserId(), feedBack.getMessage());
    }

    public void delete(int id) {
        jdbcTemplate.update("DELETE FROM feedback WHERE id = ?", id);
    }
}
