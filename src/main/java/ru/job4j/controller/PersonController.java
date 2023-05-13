package ru.job4j.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.AllArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import ru.job4j.domain.Person;
import ru.job4j.dto.PersonDTO;
import ru.job4j.service.PersonService;
import ru.job4j.util.validation.Operation;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.HashMap;
import javax.naming.AuthenticationException;
import javax.validation.Valid;

@RestController
@RequestMapping("/person")
@AllArgsConstructor
public class PersonController {

    private static final Logger LOGGER =
            LoggerFactory.getLogger(PersonController.class.getSimpleName());
    private final PersonService persons;

    private BCryptPasswordEncoder encoder;
    private final ObjectMapper objectMapper;

    @GetMapping("/all")
    public ResponseEntity<String> findAll() {
        var body = new HashMap<>() {{
            put("users", persons.findAll());
        }}.toString();
        return ResponseEntity.status(HttpStatus.OK)
                .header("Job4jAuthHeader", "job4j_auth")
                .contentType(MediaType.APPLICATION_JSON)
                .contentLength(body.length())
                .body(body);
    }

    @GetMapping("/{id}")
    public ResponseEntity<Person> findById(@PathVariable int id) {
        var person = this.persons.findById(id);
        return new ResponseEntity<>(
                person.orElse(new Person()),
                person.isPresent() ? HttpStatus.OK : HttpStatus.NOT_FOUND
        );
    }

    @PostMapping("/sign-up")
    @Validated(Operation.OnCreate.class)
    public ResponseEntity<Person> create(@Valid @RequestBody Person person)
            throws AuthenticationException {
        Person foundPerson = persons.findByLogin(person.getLogin());
        if (foundPerson != null) {
            throw new AuthenticationException("User with this name already exists");
        }
        person.setPassword(encoder.encode(person.getPassword()));
        return new ResponseEntity<>(
                this.persons.save(person),
                HttpStatus.CREATED
        );
    }

    @PutMapping("/")
    @Validated(Operation.OnUpdate.class)
    public ResponseEntity<Void> update(@Valid @RequestBody Person person) {
        person.setPassword(encoder.encode(person.getPassword()));
        if (!this.persons.update(person)) {
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }
        return ResponseEntity.ok().build();
    }

    @PatchMapping("/")
    public ResponseEntity<Void> partialUpdate(@Valid @RequestBody PersonDTO personDTO) {
        personDTO.setPassword(encoder.encode(personDTO.getPassword()));
        if (!this.persons.partialUpdate(personDTO)) {
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }
        return ResponseEntity.ok().build();
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable int id) {
        Person person = new Person();
        person.setId(id);
        if (!this.persons.delete(person)) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
        return ResponseEntity.ok().build();
    }

    @ExceptionHandler(value = { AuthenticationException.class })
    public void exceptionHandler(Exception e, HttpServletRequest request,
                                 HttpServletResponse response) throws IOException {
        response.setStatus(HttpStatus.BAD_REQUEST.value());
        response.setContentType("application/json");
        response.getWriter().write(objectMapper.writeValueAsString(new HashMap<>() { {
            put("message", e.getMessage());
        }}));
        LOGGER.error(e.getLocalizedMessage());
    }
}
