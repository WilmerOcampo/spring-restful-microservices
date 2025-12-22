package com.ocs.restfulweb.user.versioning;

import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
public class UserV1 {

    @Size(min = 2, message = "Name must be longer than 2 characters")
    private String name;

}
