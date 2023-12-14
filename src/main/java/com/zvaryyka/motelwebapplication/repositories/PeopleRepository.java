package com.zvaryyka.motelwebapplication.repositories;

import com.zvaryyka.motelwebapplication.models.Person;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public class PeopleRepository {
    public void save(Person person) {
        //TODO Create logic with JDBC

    }

    public Optional<Person> findByLogin(String username) {
        //TODO Create logic with JDBC
        return Optional.empty();
    }
}
