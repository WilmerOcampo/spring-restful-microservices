package com.ocs.restfulweb.user.persistence;

import com.ocs.restfulweb.post.Post;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.jspecify.annotations.NonNull;
import org.springdoc.core.annotations.ParameterObject;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.net.URI;
import java.util.List;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.methodOn;

@RestController
@RequestMapping("/users")
@RequiredArgsConstructor
public class UserRestController {

    private final UserService userService;

    private static void addLinks(User user, Integer id) {
        user.add(linkTo(methodOn(UserRestController.class).findById(id)).withSelfRel());
        user.add(linkTo(methodOn(UserRestController.class).getUsers()).withRel("users"));
    }

    private static void extractedUser(Iterable<User> users) {
        users.forEach(user -> {
            if (user.getId() != null) {
                addLinks(user, user.getId());
            }
        });
    }

    private static @NonNull URI getLocation(Object objectId) {
        return ServletUriComponentsBuilder
                .fromCurrentRequest()
                .path("/{id}")
                .buildAndExpand(objectId)
                .toUri();
    }

    @GetMapping
    public ResponseEntity<List<User>> getUsers() {
        List<User> users = userService.findAll();
        extractedUser(users);
        return ResponseEntity.ok(users);
    }

    @GetMapping(value = "/page")
    public ResponseEntity<Page<User>> getUsersPage(@ParameterObject Pageable pageable) {
        Page<User> users = userService.findAll(pageable);
        extractedUser(users);
        return ResponseEntity.ok(users);
    }

    @GetMapping("/{id}")
    public ResponseEntity<User> findById(@PathVariable int id) {
        User user = userService.findById(id);
        addLinks(user, id);
        return ResponseEntity.ok(user);
    }

    @PostMapping
    public ResponseEntity<User> createUser(@Valid @RequestBody User user) {
        User savedUser = userService.save(user);
        addLinks(savedUser, savedUser.getId());
        URI location = getLocation(savedUser.getId());
        return ResponseEntity.created(location).body(savedUser);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteUser(@PathVariable int id) {
        userService.deleteById(id);
        return ResponseEntity.noContent().build();
    }

    @GetMapping("/{id}/posts")
    public ResponseEntity<List<Post>> retrieveUserPosts(@PathVariable int id) {
        return ResponseEntity.ok(userService.retrieveUserPosts(id));
    }
}
