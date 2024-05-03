package com.zvaryyka.motelwebapplication.services;

import com.zvaryyka.motelwebapplication.dto.PersonDTO;
import com.zvaryyka.motelwebapplication.models.Person;
import com.zvaryyka.motelwebapplication.repositories.PeopleRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Date;
import java.util.Objects;

@Service
public class RegistrationService {

    private final PeopleRepository peopleRepository;
    private final PasswordEncoder passwordEncoder;

    private final RandomService randomService;

    @Autowired
    public RegistrationService(PeopleRepository peopleRepository, PasswordEncoder passwordEncoder, RandomService randomService) {
        this.peopleRepository = peopleRepository;
        this.passwordEncoder = passwordEncoder;
        this.randomService = randomService;
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
    public void editAdmin(Person person, int id) {
        person.setPassword(passwordEncoder.encode(person.getPassword()));
        person.setUserRole("ROLE_ADMIN");
        peopleRepository.updateWorker(id, person);
    }

    @Transactional
    public void editOwner(Person person, int id) {
        person.setPassword(passwordEncoder.encode(person.getPassword()));
        person.setUserRole("ROLE_OWNER");
        peopleRepository.updateWorker(id, person);
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
        if (Objects.equals(personDTO.getRole(), "Администратор")) {

            regAdmin(convertToPerson(personDTO));
        }
        if (Objects.equals(personDTO.getRole(), "Персонал")) {

            regStuff(convertToPerson(personDTO));
        }
    }

    @Transactional
    public void updateAdmin(Person person, int id) {
        person.setPassword(passwordEncoder.encode(person.getPassword()));
        person.setUserRole("ROLE_ADMIN");
        peopleRepository.updateWorker(id, person);
    }

    public void changeWorker(PersonDTO personDTO, int id) {
        if (Objects.equals(personDTO.getRole(), "Администратор")) {

            updateAdmin(convertToPerson(personDTO), id);
        }
        if (Objects.equals(personDTO.getRole(), "Персонал")) {

            updateStuff(convertToPerson(personDTO), id);
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

    @Transactional
    public Person registerNewRandomOwner() { //Использовать данные владельца

        Person person = new Person();
        //TODO Change to constructor
        String password = randomService.createRandomString(20);
        person.setPassword(password);
        person.setDateOfBirth(new Date());
        person.setSalary(999999);
        person.setName("Nikita");
        person.setSurname("Zvarykin");
        person.setLogin(randomService.createRandomString(15));

        regOwner(person);

        person.setPassword(password);


        return person;

    }
}