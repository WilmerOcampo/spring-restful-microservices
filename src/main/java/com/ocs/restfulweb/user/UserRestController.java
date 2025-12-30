package com.ocs.restfulweb.user;

import com.ocs.restfulweb.exception.NotFoundException;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.hateoas.EntityModel;
import org.springframework.hateoas.Link;
import org.springframework.hateoas.server.mvc.WebMvcLinkBuilder;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.net.URI;
import java.util.List;

@RestController
@RequestMapping("/users")
@RequiredArgsConstructor
public class UserRestController {

    private final UserService userService;

    @GetMapping
    public List<User> getUsers() {
        return userService.findAll();
    }

    @GetMapping("/{id}")
    public EntityModel<User> findById(@PathVariable int id) throws NoSuchMethodException {
        User user = userService.findById(id);
        if (user == null) {
            throw new NotFoundException(String.format("User %s not found", id));
        }
        //Link linkTo = WebMvcLinkBuilder.linkTo(UserRestController.class).withRel("self");
        Link linkToUsers = WebMvcLinkBuilder.linkTo(WebMvcLinkBuilder.methodOn(UserRestController.class).getUsers()).withRel("users");
        //return EntityModel.of(user, linkTo, linkToUsers);
        return EntityModel.of(user, linkToUsers);
    }

    @PostMapping
    public ResponseEntity<User> createUser(@Valid @RequestBody User user) {
        User saveUser = userService.save(user);
        URI location = ServletUriComponentsBuilder
                .fromCurrentRequest()
                .path("/{id}")
                .buildAndExpand(saveUser.getId())
                .toUri();
        //return ResponseEntity.created(location).build();
        return ResponseEntity.created(location).body(saveUser);
    }

    @DeleteMapping("/{id}")
    public void deleteUser(@PathVariable int id) {
        if (userService.findById(id) == null) {
            throw new NotFoundException(String.format("User %s not found", id));
        }
        userService.deleteById(id);
    }
}
