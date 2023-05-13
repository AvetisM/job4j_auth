package ru.job4j.service;

import lombok.AllArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import ru.job4j.controller.PersonController;
import ru.job4j.domain.Person;
import ru.job4j.dto.PersonDTO;
import ru.job4j.repository.PersonRepository;

import java.util.List;
import java.util.Optional;

import static java.util.Collections.emptyList;

@Service
@AllArgsConstructor
public class PersonService implements UserDetailsService {

    private static final Logger LOGGER =
            LoggerFactory.getLogger(PersonController.class.getSimpleName());
    private final PersonRepository persons;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        Person user = findByLogin(username);
        if (user == null) {
            throw new UsernameNotFoundException(username);
        }
        return new User(user.getLogin(), user.getPassword(), emptyList());
    }

    public List<Person> findAll() {
        return persons.findAll();
    }

    public Optional<Person> findById(int id) {
        return persons.findById(id);
    }

    public Person findByLogin(String login) {
        return persons.findByLogin(login);
    }

    public Person save(Person person) {
          return persons.save(person);
    }

    public boolean update(Person person) {
        boolean result = false;
        try {
            persons.save(person);
            result =  true;
        } catch (Exception e) {
            LOGGER.error(e.getLocalizedMessage());
        }
        return result;
    }

    public boolean partialUpdate(PersonDTO personDTO) {
        boolean result = false;
        Optional<Person> personOptional = findById(personDTO.getId());
        if (personOptional.isEmpty()) {
            return false;
        }
        Person person = personOptional.get();
        person.setPassword(personDTO.getPassword());
        try {
            persons.save(person);
            result = true;
        } catch (Exception e) {
            LOGGER.error(e.getLocalizedMessage());
        }
        return result;
    }

    public boolean delete(Person person) {
        boolean result = false;
        try {
            persons.delete(person);
            result = true;
        } catch (Exception e) {
            LOGGER.error(e.getLocalizedMessage());
        }
        return result;
    }
}
