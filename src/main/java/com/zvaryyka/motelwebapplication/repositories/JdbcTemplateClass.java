package com.zvaryyka.motelwebapplication.repositories;

import com.zvaryyka.motelwebapplication.config.JdbcConfig;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.jdbc.core.JdbcTemplate;
public class JdbcTemplateClass {


    protected final JdbcTemplate jdbcTemplate = new JdbcTemplate(new JdbcConfig().postgreDataSource());


}
