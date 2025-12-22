package com.ocs.restfulweb.user;

import jakarta.validation.constraints.Past;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDate;

@Getter
@Setter
@AllArgsConstructor
public class User {

    private Integer id;
    @Size(min = 2, message = "Name must be longer than 2 characters")
    private String name;
    @Past(message = "Birth Date must be in the past")
    private LocalDate birthDate;

}
