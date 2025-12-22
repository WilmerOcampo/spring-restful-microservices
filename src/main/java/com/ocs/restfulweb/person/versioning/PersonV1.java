package com.ocs.restfulweb.person.versioning;

import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
public class PersonV1 {

    @Size(min = 2, message = "Name must be longer than 2 characters")
    private String name;

}
