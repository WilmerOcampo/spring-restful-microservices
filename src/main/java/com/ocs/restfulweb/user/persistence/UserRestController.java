package com.ocs.restfulweb.user.persistence;

import com.ocs.restfulweb.post.Post;
import com.ocs.restfulweb.util.LinkUtils;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springdoc.core.annotations.ParameterObject;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.net.URI;
import java.util.List;
import java.util.UUID;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.methodOn;

@RestController
@RequestMapping("/rest/users")
@RequiredArgsConstructor
public class UserRestController {

    private final UserService userService;

    private static void addLinks(User user, int id) {
        user.add(linkTo(methodOn(UserRestController.class).findById(id)).withSelfRel());
        user.add(linkTo(methodOn(UserRestController.class).retrieveUserPosts(id)).withRel("posts"));
    }

    private static void addLinksForPost(Post post, int id) {
        post.add(linkTo(methodOn(UserRestController.class).retrieveUserPost(id, post.getUuid())).withSelfRel());
        post.add(linkTo(methodOn(UserRestController.class).retrieveUserPosts(id)).withRel("posts"));
    }

    private static void extractedUser(Iterable<User> users) {
        users.forEach(user -> {
            if (user != null && user.getId() != null) {
                addLinks(user, user.getId());
            }
        });
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
        URI location = LinkUtils.buildLocation(savedUser.getId());
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

    @PostMapping("/{id}/post")
    public ResponseEntity<Post> createPost(@PathVariable int id, @Valid @RequestBody Post request) {
        Post post = userService.createUserPost(id, request);
        addLinksForPost(post, id);
        URI location = LinkUtils.buildLocation(post.getUuid());
        return ResponseEntity.created(location).body(post);
    }

    @GetMapping("/{id}/post/{uuid}")
    public ResponseEntity<Post> retrieveUserPost(@PathVariable int id, @PathVariable UUID uuid) {
        Post post = userService.findPostByUuid(id, uuid);
        addLinksForPost(post, id);
        return ResponseEntity.ok(post);
    }

}
