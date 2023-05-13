package ru.job4j.domain;

import lombok.Data;
import lombok.EqualsAndHashCode;
import ru.job4j.util.validation.Operation;

import javax.persistence.*;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

@Entity
@Data
@EqualsAndHashCode(onlyExplicitlyIncluded = true)
@Table(name = "person")
public class Person {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @EqualsAndHashCode.Include
    @NotNull @Min(value = 1, message = "Id must be non null and more then 0",
            groups = {Operation.OnUpdate.class})
    private int id;
    @EqualsAndHashCode.Include
    @NotBlank(message = "Login must be not empty", groups = {Operation.OnCreate.class,
            Operation.OnUpdate.class})
    private String login;
    @NotBlank(message = "Password must be not empty", groups = {Operation.OnCreate.class,
            Operation.OnUpdate.class})
    private String password;
}
