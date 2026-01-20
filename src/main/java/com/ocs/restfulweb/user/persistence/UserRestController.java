package com.ocs.restfulweb.user.persistence;

import com.ocs.restfulweb.contants.OperationConstants;
import com.ocs.restfulweb.exception.ApiResponse;
import com.ocs.restfulweb.post.Post;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springdoc.core.annotations.ParameterObject;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.hateoas.Link;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.methodOn;

@RestController
@RequestMapping("/rest/users")
@RequiredArgsConstructor
public class UserRestController {

    private final UserService userService;

    private static Link linkPosts(int id) {
        return linkTo(methodOn(UserRestController.class).retrieveUserPosts(id)).withRel("posts");
    }

    private static void addLinks(User user, int id) {
        user.add(linkTo(methodOn(UserRestController.class).findById(id)).withSelfRel());
        user.add(linkPosts(id));
    }

    private static void addLinksToPost(Post post, int id) {
        post.add(linkTo(methodOn(UserRestController.class).retrieveUserPost(id, post.getUuid())).withSelfRel());
        post.add(linkPosts(id));
    }

    private <C extends Iterable<User>> void addLinksToUsers(C users) {
        users.forEach(user -> addLinks(user, user.getId()));
    }

    private <C extends Iterable<Post>> void addLinksToPosts(C posts) {
        posts.forEach(post -> addLinksToPost(post, post.getUser().getId()));
    }

    @GetMapping
    public ResponseEntity<ApiResponse<Page<User>>> users(@ParameterObject Pageable pageable) {
        Page<User> users = userService.findAll(pageable);
        addLinksToUsers(users.getContent());
        return ApiResponse.okResponse(users, String.format("Users %s %s", OperationConstants.FETCHED, OperationConstants.SUCCESSFULLY));
    }

    @GetMapping("/list")
    public ResponseEntity<ApiResponse<List<User>>> userList() {
        List<User> users = userService.findAll();
        addLinksToUsers(users);
        return ApiResponse.okResponse(users, String.format("Users %s %s", OperationConstants.FETCHED, OperationConstants.SUCCESSFULLY));
    }

    @GetMapping("/{id}")
    public ResponseEntity<ApiResponse<User>> findById(@PathVariable int id) {

        User user = userService.findById(id);
        addLinks(user, id);
        return ApiResponse.okResponse(user, String.format("User %s %s", OperationConstants.FETCHED, OperationConstants.SUCCESSFULLY));

    }

    @PostMapping
    public ResponseEntity<ApiResponse<User>> createUser(@Valid @RequestBody User user) {
        User createdUser = userService.save(user);
        addLinks(createdUser, createdUser.getId());
        return ApiResponse.createdResponse(createdUser, createdUser.getId(), String.format("User %s %s", OperationConstants.CREATED, OperationConstants.SUCCESSFULLY));
    }

    @PutMapping("/{id}")
    public ResponseEntity<ApiResponse<User>> updateUser(@PathVariable int id, @Valid @RequestBody User user) {
        User updatedUser = userService.update(id, user);
        addLinks(updatedUser, id);
        return ApiResponse.updatedResponse(updatedUser, String.format("User %s %s", OperationConstants.UPDATED, OperationConstants.SUCCESSFULLY));

    }

    @PatchMapping("/{id}")
    public ResponseEntity<ApiResponse<User>> updateUser(@Valid @RequestBody User user, @PathVariable int id) {
        User updatedUser = userService.partialUpdate(user, id);
        addLinks(updatedUser, id);
        return ApiResponse.updatedResponse(updatedUser, String.format("User %s %s", OperationConstants.UPDATED, OperationConstants.SUCCESSFULLY));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<ApiResponse<Void>> deleteUser(@PathVariable int id) {
        userService.deleteById(id);
        return ApiResponse.noContentResponse(String.format("User %s %s", OperationConstants.DELETED, OperationConstants.SUCCESSFULLY));
    }

    @GetMapping("/{id}/posts")
    public ResponseEntity<ApiResponse<List<Post>>> retrieveUserPosts(@PathVariable int id) {
        List<Post> posts = userService.retrieveUserPosts(id);
        addLinksToPosts(posts);
        return ApiResponse.okResponse(posts, String.format("Posts for User %s %s", OperationConstants.FETCHED, OperationConstants.SUCCESSFULLY));
    }

    @PostMapping("/{id}/post")
    public ResponseEntity<ApiResponse<Post>> createPost(@PathVariable int id, @Valid @RequestBody Post request) {
        Post createdPost = userService.createUserPost(id, request);
        addLinksToPost(createdPost, id);
        return ApiResponse.createdResponse(createdPost, createdPost.getUuid(), String.format("Post for User %s %s", OperationConstants.CREATED, OperationConstants.SUCCESSFULLY));
    }

    @GetMapping("/{id}/post/{uuid}")
    public ResponseEntity<ApiResponse<Post>> retrieveUserPost(@PathVariable int id, @PathVariable UUID uuid) {
        Post post = userService.findPostByUuid(id, uuid);
        addLinksToPost(post, id);
        return ApiResponse.okResponse(post, String.format("Post for User %s %s", OperationConstants.FETCHED, OperationConstants.SUCCESSFULLY));
    }

}
