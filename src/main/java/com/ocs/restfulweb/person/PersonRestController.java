package com.ocs.restfulweb.person;

import com.ocs.restfulweb.person.versioning.Name;
import com.ocs.restfulweb.person.versioning.PersonV1;
import com.ocs.restfulweb.person.versioning.PersonV2;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
public class PersonRestController {

    @GetMapping("/v1/person")
    public PersonV1 findFirstPerson() {
        return new PersonV1("Wilmer Ocampo");
    }

    @GetMapping("/v2/person")
    public PersonV2 findSecondPerson() {
        return new PersonV2(new Name("Wilmer", "Ocampo"));
    }

    @GetMapping(path = "/person", params = "version=1") //http://localhost:8080/person?version=1
    public PersonV1 findFirstPersonReqParam() {
        return new PersonV1("Wilmer Ocampo");
    }

    @GetMapping(path = "/person", params = "version=2") //http://localhost:8080/person?version=2
    public PersonV2 findSecondPersonReqParam() {
        return new PersonV2(new Name("Wilmer", "Ocampo"));
    }

    @GetMapping(path = "/person/header", headers = "X-API-VERSION=1") // Add header X-API-VERSION
    public PersonV1 findFirstPersonReqHeader() {
        return new PersonV1("Wilmer Ocampo");
    }

    @GetMapping(path = "/person/header", headers = "X-API-VERSION=2") // Add header X-API-VERSION
    public PersonV2 findSecondPersonReqHeader() {
        return new PersonV2(new Name("Wilmer", "Ocampo"));
    }

    @GetMapping(path = "/person/accept", produces = "application/api.ocs.app-v1+json") // Add header: Accept : application/api.ocs.app-v1+json
    public PersonV1 findFirstPersonReqAccept() {
        return new PersonV1("Wilmer Ocampo");
    }

    @GetMapping(path = "/person/accept", produces = "application/api.ocs.app-v2+json") // Add header: Accept : application/api.ocs.app-v2+json
    public PersonV2 findSecondPersonReqAccept() {
        return new PersonV2(new Name("Wilmer", "Ocampo"));
    }
}
