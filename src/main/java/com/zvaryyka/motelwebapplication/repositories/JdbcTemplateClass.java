package com.zvaryyka.motelwebapplication.repositories;

import com.zvaryyka.motelwebapplication.config.JdbcConfig;
import org.springframework.context.annotation.Bean;
import org.springframework.jdbc.core.JdbcTemplate;
public class JdbcTemplateClass {
    protected final JdbcTemplate jdbcTemplate = new JdbcTemplate(JdbcConfig.postgreDataSource());
}
