package com.zvaryyka.motelwebapplication.services;

import com.zvaryyka.motelwebapplication.dto.PersonDTO;
import com.zvaryyka.motelwebapplication.models.Person;
import com.zvaryyka.motelwebapplication.repositories.PeopleRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import lombok.extern.slf4j.Slf4j;

import java.util.Date;
import java.util.Objects;

@Service
@Transactional
@Slf4j
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

    public void regGuest(Person person) {
        log.info("Registering guest: {}", person);
        person.setPassword(passwordEncoder.encode(person.getPassword()));
        person.setUserRole("ROLE_USER");
        peopleRepository.save(person);
        log.info("Guest registered successfully");
    }

    public void regStuff(Person person) {
        log.info("Registering staff: {}", person);
        person.setPassword(passwordEncoder.encode(person.getPassword()));
        person.setUserRole("ROLE_STUFF");
        peopleRepository.saveWithSalary(person);
        log.info("Staff registered successfully");
    }

    public void regAdmin(Person person) {
        log.info("Registering admin: {}", person);
        person.setPassword(passwordEncoder.encode(person.getPassword()));
        person.setUserRole("ROLE_ADMIN");
        peopleRepository.saveWithSalary(person);
        log.info("Admin registered successfully");
    }

    public void regOwner(Person person) {
        log.info("Registering owner: {}", person);
        person.setPassword(passwordEncoder.encode(person.getPassword()));
        person.setUserRole("ROLE_OWNER");
        peopleRepository.saveWithSalary(person);
        log.info("Owner registered successfully");
    }

    public void editAdmin(Person person, int id) {
        log.info("Editing admin with ID {}: {}", id, person);
        person.setPassword(passwordEncoder.encode(person.getPassword()));
        person.setUserRole("ROLE_ADMIN");
        peopleRepository.updateWorker(id, person);
        log.info("Admin edited successfully");
    }

    public void editOwner(Person person, int id) {
        log.info("Editing owner with ID {}: {}", id, person);
        person.setPassword(passwordEncoder.encode(person.getPassword()));
        person.setUserRole("ROLE_OWNER");
        peopleRepository.updateWorker(id, person);
        log.info("Owner edited successfully");
    }

    public void updateStuff(Person updatePerson, int id) {
        log.info("Updating staff with ID {}: {}", id, updatePerson);
        updatePerson.setPassword(passwordEncoder.encode(updatePerson.getPassword()));
        updatePerson.setUserRole("ROLE_STUFF");
        peopleRepository.updateWorker(id, updatePerson);
        log.info("Staff updated successfully");
    }

    public void delete(int id) {
        log.info("Deleting user with ID {}", id);
        peopleRepository.delete(id);
        log.info("User deleted successfully");
    }

    public void regWorker(PersonDTO personDTO) {
        if (Objects.equals(personDTO.getRole(), "Администратор")) {
            regAdmin(convertToPerson(personDTO));
        }
        if (Objects.equals(personDTO.getRole(), "Персонал")) {
            regStuff(convertToPerson(personDTO));
        }
    }

    public void updateAdmin(Person person, int id) {
        log.info("Updating admin with ID {}: {}", id, person);
        person.setPassword(passwordEncoder.encode(person.getPassword()));
        person.setUserRole("ROLE_ADMIN");
        peopleRepository.updateWorker(id, person);
        log.info("Admin updated successfully");
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

    public Person registerNewRandomOwner() {
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
