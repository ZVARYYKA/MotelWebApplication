package com.zvaryyka.motelwebapplication;

import com.zvaryyka.motelwebapplication.models.Person;
import com.zvaryyka.motelwebapplication.repositories.PeopleRepository;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.jdbc.core.JdbcTemplate;

import java.util.Date;
import java.util.Optional;

@SpringBootTest
class MotelWebApplicationTests {

    @Test
    void contextLoads() {
    }
    @Test
    void regNewUser() {
        Person person = new Person();
        person.setName("test");
        person.setSurname("test");
        person.setLogin("test");
        person.setPassword("test");
        person.setUserRole("test");
        person.setDateOfBirth(new Date());
        PeopleRepository peopleRepository = new PeopleRepository();
        peopleRepository.save(person);

    }
    @Test
    void findByLogin() {
        PeopleRepository peopleRepository = new PeopleRepository();
       Optional<Person> person =  peopleRepository.findByLogin("test");
        System.out.println(person.get().getName());

    }


}
