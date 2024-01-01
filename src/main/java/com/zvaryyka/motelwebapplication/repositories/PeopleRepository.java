package com.zvaryyka.motelwebapplication.repositories;

import com.zvaryyka.motelwebapplication.models.Person;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Repository;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.List;
import java.util.Optional;

@Repository
public class PeopleRepository extends JdbcTemplateClass {

    private final RowMapper<Person> personRowMapper = (ResultSet row, int rowNumber) -> {
        Person person = new Person();
        person.setId(row.getInt("id"));
        person.setLogin(row.getString("login"));
        person.setName(row.getString("name"));
        person.setSurname(row.getString("surname"));

        // Getting date object and converting it to java.util.Date
        //TODO rewrite this code
        java.sql.Date date = row.getObject("date_of_birth", java.sql.Date.class);
        if (date != null) {
            person.setDateOfBirth(new java.util.Date(date.getTime()));
        } else {
            person.setDateOfBirth(null);
        }

        person.setPassword(row.getString("password"));
        person.setUserRole(row.getString("user_role"));
        person.setSalary(row.getInt("salary"));
        return person;
    };

    public void save(Person person) {
        jdbcTemplate.update("INSERT INTO person (login, name, surname, password, user_role, date_of_birth) VALUES (?, ?, ?, ?, ?, ?)",
                person.getLogin(), person.getName(), person.getSurname(), person.getPassword(), person.getUserRole(), person.getDateOfBirth());
    }
    public void saveWithSalary(Person person) {
        jdbcTemplate.update("INSERT INTO person (login, name, surname, password, user_role, date_of_birth,salary) VALUES (?, ?, ?, ?, ?, ?,?)",
                person.getLogin(), person.getName(), person.getSurname(), person.getPassword(), person.getUserRole(), person.getDateOfBirth(),person.getSalary());
    }

    public void update(int id, Person updatedPerson) {

        jdbcTemplate.update("UPDATE person SET login = ?, name = ?, surname = ?, password = ?, user_role = ?, date_of_birth = ? WHERE id = ?",
                updatedPerson.getLogin(), updatedPerson.getName(), updatedPerson.getSurname(), updatedPerson.getPassword(), updatedPerson.getUserRole(), updatedPerson.getDateOfBirth(), id);

    }

    public void delete(int id) {
        jdbcTemplate.update("DELETE FROM person WHERE id = ?", id);
    }

    public List<Person> showStaff() {
        return jdbcTemplate.query("SELECT * FROM person WHERE user_role = 'ROLE_STAFF'", personRowMapper);
    }

    public List<Person> showAdmins() {
        return jdbcTemplate.query("SELECT * FROM person WHERE user_role = 'ROLE_ADMIN'", personRowMapper);
    }

    public List<Person> showAll() {
        return jdbcTemplate.query("SELECT * FROM person", personRowMapper);
    }


    public Optional<Person> findByLogin(String login) {
        return jdbcTemplate.query("SELECT * FROM person WHERE login = ?", personRowMapper, login).stream().findAny();
    }

}
