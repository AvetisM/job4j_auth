package ru.job4j.service;

import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import ru.job4j.domain.Person;
import ru.job4j.repository.PersonRepository;

import java.util.List;
import java.util.Optional;

@Service
@AllArgsConstructor
public class PersonService {
    private final PersonRepository persons;

    public List<Person> findAll() {
        return persons.findAll();
    }

    public Optional<Person> findById(int id) {
        return persons.findById(id);
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
