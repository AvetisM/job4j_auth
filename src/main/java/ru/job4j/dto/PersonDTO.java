package ru.job4j.dto;

import lombok.Data;
import lombok.EqualsAndHashCode;

import javax.validation.constraints.Min;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

@Data
@EqualsAndHashCode(onlyExplicitlyIncluded = true)
public class PersonDTO {
    @EqualsAndHashCode.Include
    @NotNull  @Min(value = 1, message = "Id must be non null and more then 0")
    private int id;
    @NotBlank(message = "Password must be not empty")
    private String password;
}
