package com.zvaryyka.motelwebapplication.services;

import com.zvaryyka.motelwebapplication.dto.PersonDTO;
import com.zvaryyka.motelwebapplication.models.Person;
import com.zvaryyka.motelwebapplication.repositories.PeopleRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Objects;

@Service

public class RegistrationService {

    private final PeopleRepository peopleRepository;
    private final PasswordEncoder passwordEncoder;

    @Autowired
    public RegistrationService(PeopleRepository peopleRepository, PasswordEncoder passwordEncoder) {
        this.peopleRepository = peopleRepository;
        this.passwordEncoder = passwordEncoder;
    }

    @Transactional
    public void regGuest(Person person) {
        person.setPassword(passwordEncoder.encode(person.getPassword()));
        person.setUserRole("ROLE_USER");
        peopleRepository.save(person);
    }

    @Transactional
    public void regStuff(Person person) {
        person.setPassword(passwordEncoder.encode(person.getPassword()));
        person.setUserRole("ROLE_STUFF");
        peopleRepository.saveWithSalary(person);
    }

    @Transactional
    public void regAdmin(Person person) {
        person.setPassword(passwordEncoder.encode(person.getPassword()));
        person.setUserRole("ROLE_ADMIN");
        peopleRepository.saveWithSalary(person);
    }

    @Transactional
    public void regOwner(Person person) {
        person.setPassword(passwordEncoder.encode(person.getPassword()));
        person.setUserRole("ROLE_OWNER");
        peopleRepository.saveWithSalary(person);
    }
    @Transactional
    public void editAdmin(Person person,int id) {
        person.setPassword(passwordEncoder.encode(person.getPassword()));
        person.setUserRole("ROLE_ADMIN");
        peopleRepository.updateWorker(id,person);
    }

    @Transactional
    public void editOwner(Person person, int id) {
        person.setPassword(passwordEncoder.encode(person.getPassword()));
        person.setUserRole("ROLE_OWNER");
        peopleRepository.updateWorker(id,person);
    }




    @Transactional
    public void updateStuff(Person updatePerson, int id) {
        updatePerson.setPassword(passwordEncoder.encode(updatePerson.getPassword()));
        updatePerson.setUserRole("ROLE_STUFF");
        peopleRepository.updateWorker(id, updatePerson);
    }

    @Transactional
    public void delete(int id) {
        peopleRepository.delete(id);
    }


    public void regWorker(PersonDTO personDTO) {
        if(Objects.equals(personDTO.getRole(), "Администратор")) {

            regAdmin(convertToPerson(personDTO));
        }
        if(Objects.equals(personDTO.getRole(), "Персонал")) {

            regStuff(convertToPerson(personDTO));
        }
    }
    public Person convertToPerson(PersonDTO personDTO) {
        Person person = new Person();
        person.setPassword(personDTO.getPassword());
        person.setLogin(personDTO.getLogin());
        person.setName(personDTO.getName());
        person.setSurname(personDTO.getSurname());
        person.setDateOfBirth(personDTO.getDateOfBirth());
        person.setSalary(personDTO.getSalary());

        return person;

    }
}