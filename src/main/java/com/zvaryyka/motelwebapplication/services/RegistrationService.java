package com.zvaryyka.motelwebapplication.services;

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
    public void regStaff(Person person) {
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
    public void regWorker(Person person) {
        if (Objects.equals(person.getStuffType(), "Администратор"))
            regAdmin(person);
        else if (Objects.equals(person.getStuffType(), "Персонал"))
            regOwner(person);

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

    @Transactional
    public void editWorker(Person person, int id) {
        if (Objects.equals(person.getStuffType(), "Администратор"))
            editAdmin(person,id);
        else if (Objects.equals(person.getStuffType(), "Персонал"))
            editOwner(person,id);

    }
}