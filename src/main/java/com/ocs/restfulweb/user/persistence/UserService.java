package com.ocs.restfulweb.user.persistence;

import com.ocs.restfulweb.exception.NotFoundException;
import com.ocs.restfulweb.post.Post;
import com.ocs.restfulweb.post.PostJpaRepository;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.jspecify.annotations.NonNull;
import org.springframework.beans.BeanUtils;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.UUID;

@Component
@RequiredArgsConstructor
public class UserService {

    private final UserJpaRepository userJpaRepository;
    private final PostJpaRepository postJpaRepository;

    public List<User> findAll() {
        return userJpaRepository.findAll();
    }

    public Page<User> findAll(Pageable pageable) {
        return userJpaRepository.findAll(pageable);
    }

    public User save(User user) {
        return userJpaRepository.save(user);
    }

    public User findById(int id) {
        return userJpaRepository.findById(id)
                .orElseThrow(UserService::userNotFound);
    }

    private static NotFoundException userNotFound() {
        return new NotFoundException("User not found");
    }

    public void deleteById(int id) {
        if (!userJpaRepository.existsById(id)) {
            throw userNotFound();
        }
        userJpaRepository.deleteById(id);
    }

    public List<Post> retrieveUserPosts(int id) {
        User user = findById(id);
        return user.getPosts();
    }

    public Post createUserPost(int id, Post post) {
        User user = findById(id);
        post.setUser(user);
        return postJpaRepository.save(post);
    }

    public Post findPostByUuid(int userId, UUID postId) {
        return postJpaRepository.findByUuidAndUserId(postId, userId)
                .orElseThrow(() -> new NotFoundException("Post not found for User"));
    }

    public User update(int id, @Valid User user) {
        User existing = findById(id);
        BeanUtils.copyProperties(user, existing, "id");
        return userJpaRepository.save(existing);
    }

    public User partialUpdate(@Valid User user, int id) {
        User existing = findById(id);
        if (user.getId() != null) {
            existing.setId(user.getId());
        }
        if (user.getName() != null) {
            existing.setName(user.getName());
        }
        if (user.getBirthDate() != null) {
            existing.setBirthDate(user.getBirthDate());
        }
        return userJpaRepository.save(existing);
    }
}
