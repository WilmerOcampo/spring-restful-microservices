package com.ocs.restfulweb.user;

import com.ocs.restfulweb.user.versioning.Name;
import com.ocs.restfulweb.user.versioning.UserV1;
import com.ocs.restfulweb.user.versioning.UserV2;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
public class UserV1RestController {

    @GetMapping("/v1/users/first")
    public UserV1 findFirstUser() {
        return new UserV1("Wilmer Ocampo");
    }

    @GetMapping("/v2/users/second")
    public UserV2 findSecondUser() {
        return new UserV2(new Name("Wilmer", "Ocampo"));
    }
}
