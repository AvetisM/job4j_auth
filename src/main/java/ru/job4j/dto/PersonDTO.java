package ru.job4j.dto;

import lombok.Data;
import lombok.EqualsAndHashCode;

@Data
@EqualsAndHashCode(onlyExplicitlyIncluded = true)
public class PersonDTO {
    @EqualsAndHashCode.Include
    private int id;

    private String password;
}
