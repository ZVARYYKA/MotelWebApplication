package com.zvaryyka.motelwebapplication.repositories;

import com.zvaryyka.motelwebapplication.models.Person;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public class PeopleRepository extends JdbcTemplateClass {



    public void save(Person person) {
        jdbcTemplate.update("INSERT INTO person (login,name,surname, password, user_role,date_of_birth) VALUES (?,?,?, ?, ?,?)",
                person.getLogin(), person.getName(), person.getSurname(), person.getPassword(), person.getUserRole(), person.getDateOfBirth());


    }

    public Optional<Person> findByLogin(String username) {
        //Поиск по логину в Person
        return Optional.empty();


    }
}
