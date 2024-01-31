package com.zvaryyka.motelwebapplication.repositories;

import com.zvaryyka.motelwebapplication.dto.FeedBackDTO;
import com.zvaryyka.motelwebapplication.models.FeedBack;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public class FeedBackRepository extends JdbcTemplateClass {
    public List<FeedBack> getAllFeedBacks() {
        return jdbcTemplate.query("SELECT * FROM Feedback", new BeanPropertyRowMapper<>());
    }

    public void save(FeedBack feedBack) {
        jdbcTemplate.update("INSERT INTO feedback (mark,user_id,message) VALUES (?,?,?)",
                feedBack.getMark(),
                feedBack.getUserId(),
                feedBack.getMessage());
    }
    public List<FeedBackDTO> getAllFeedBacksDTO() { //TODO Rewrite
        String sql = "SELECT fb.mark, fb.user_id, fb.message, p.name as userName, p.surname as userSurname " +
                "FROM feedback fb " +
                "JOIN person p ON fb.user_id = p.id";

        return jdbcTemplate.query(sql, new BeanPropertyRowMapper<>(FeedBackDTO.class));
    }
}
