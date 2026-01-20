package com.ocs.restfulweb.user;

import com.ocs.restfulweb.exception.NotFoundException;
import com.ocs.restfulweb.util.LinkUtils;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.hateoas.EntityModel;
import org.springframework.hateoas.Link;
import org.springframework.hateoas.server.mvc.WebMvcLinkBuilder;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.net.URI;
import java.util.List;

@RestController
@RequestMapping("/model/users")
@RequiredArgsConstructor
public class UserModelRestController {

    private final UserModelService userService;

    @GetMapping
    public List<UserModel> getUsers() {
        return userService.findAll();
    }

    @GetMapping("/{id}")
    public EntityModel<UserModel> findById(@PathVariable int id) throws NoSuchMethodException {
        UserModel user = userService.findById(id);
        if (user == null) {
            throw new NotFoundException(String.format("User %s not found", id));
        }
        //Link linkTo = WebMvcLinkBuilder.linkTo(UserRestController.class).withRel("self");
        Link linkToUsers = WebMvcLinkBuilder.linkTo(WebMvcLinkBuilder.methodOn(UserModelRestController.class).getUsers()).withRel("users");
        //return EntityModel.of(user, linkTo, linkToUsers);
        return EntityModel.of(user, linkToUsers);
    }

    @PostMapping
    public ResponseEntity<UserModel> createUser(@Valid @RequestBody UserModel user) {
        UserModel saveUser = userService.save(user);
        URI location = LinkUtils.buildCreatedLocation(saveUser.getId());
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
