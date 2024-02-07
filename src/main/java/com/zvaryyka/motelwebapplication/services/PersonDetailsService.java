package com.zvaryyka.motelwebapplication.services;
import com.zvaryyka.motelwebapplication.models.Person;
import com.zvaryyka.motelwebapplication.repositories.PeopleRepository;
import com.zvaryyka.motelwebapplication.security.PersonDetails;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.security.Principal;
import java.util.List;
import java.util.Optional;

@Service
public class PersonDetailsService implements UserDetailsService {
    private final PeopleRepository userRepository;

    @Autowired
    public PersonDetailsService(PeopleRepository userRepository) {
        this.userRepository = userRepository;
    }

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        Optional<Person> person = userRepository.findByLogin(username);

        if (person.isEmpty())
            throw new UsernameNotFoundException("User not found");

        return new PersonDetails(person.get());
    }

    public Optional<Person> findByLogin(String login) {
        return userRepository.findByLogin(login);
    }
    private Person getCurrentPerson(Principal principal) {
        if (principal == null) {
            return new Person();
        } else {
            return findByLogin(principal.getName())
                    .orElseThrow(() -> new UsernameNotFoundException("User not found"));
        }

    }
    public List<Person> showAllStuffs() {
        return userRepository.showStaff();
    }
    public List<Person> showAllWorkers() {
        return userRepository.showWorkers();
    }


    public void delete(int id) {
        userRepository.delete(id);
    }

    public void updateStuff(int id, Person person) {
        person.setUserRole("ROLE_STUFF");

        userRepository.updateWorker(id,person);
    }
}

