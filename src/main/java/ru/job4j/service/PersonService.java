package ru.job4j.service;

import lombok.AllArgsConstructor;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import ru.job4j.domain.Person;
import ru.job4j.repository.PersonRepository;

import java.util.List;
import java.util.Optional;

import static java.util.Collections.emptyList;

@Service
@AllArgsConstructor
public class PersonService implements UserDetailsService {
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
        try {
            persons.save(person);
            return true;
        } catch (Exception e) {
            return false;
        }
    }

    public boolean delete(Person person) {
        try {
            persons.delete(person);
            return true;
        } catch (Exception e) {
            return false;
        }
    }
}
