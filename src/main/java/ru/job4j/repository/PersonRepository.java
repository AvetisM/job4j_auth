package ru.job4j.repository;

import org.springframework.data.repository.CrudRepository;
import ru.job4j.domain.Person;

import java.util.List;

public interface PersonRepository extends CrudRepository<Person, Integer> {
    @Override
    List<Person> findAll();

    Person findByLogin(String login);
}
