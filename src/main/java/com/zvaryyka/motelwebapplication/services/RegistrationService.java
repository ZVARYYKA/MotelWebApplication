package com.zvaryyka.motelwebapplication.services;

import com.zvaryyka.motelwebapplication.models.Person;
import com.zvaryyka.motelwebapplication.repositories.PeopleRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

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
        person.setUserRole("ROLE_STAFF");
        peopleRepository.save(person);
    }
    @Transactional
    public void regAdmin(Person person) {
        person.setPassword(passwordEncoder.encode(person.getPassword()));
        person.setUserRole("ROLE_ADMIN");
        peopleRepository.save(person);
    }
    @Transactional
    public void regOwner(Person person) {
        person.setPassword(passwordEncoder.encode(person.getPassword()));
        person.setUserRole("ROLE_OWNER");
        peopleRepository.save(person);
    }

}