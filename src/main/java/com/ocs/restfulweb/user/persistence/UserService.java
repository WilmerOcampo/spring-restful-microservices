package com.ocs.restfulweb.user.persistence;

import com.ocs.restfulweb.exception.NotFoundException;
import com.ocs.restfulweb.post.PostJpaRepository;
import com.ocs.restfulweb.post.Post;
import lombok.RequiredArgsConstructor;
import org.jspecify.annotations.NonNull;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
@RequiredArgsConstructor
public class UserService {

    private final UserPostRepository userPostRepository;
    private final PostJpaRepository postJpaRepository;

    public List<User> findAll() {
        return userPostRepository.findAll();
    }

    public Page<User> findAll(Pageable pageable) {
        return userPostRepository.findAll(pageable);
    }

    public User save(User user) {
        return userPostRepository.save(user);
    }

    public User findById(int id) {
        return userPostRepository.findById(id)
                .orElseThrow(() -> notFoundException(id));
    }

    public void deleteById(int id) {
        if (!userPostRepository.existsById(id)) {
            throw notFoundException(id);
        }
        userPostRepository.deleteById(id);
    }

    private static @NonNull NotFoundException notFoundException(int id) {
        return new NotFoundException(String.format("User %s not found", id));
    }

    public List<Post> retrieveUserPosts(int id) {
        User user = findById(id);
        return user.getPosts();
    }

    /*public Post createUserPosts(int id, Post post) {
        User user = findById(id);
        //List<Post> posts = user.getPosts();
        post.setUser(user);
        return jpaPostRepository.save(post);
    }*/
    public Post createUserPost(int id, Post post) {
        User user = findById(id);
        post.setUser(user);
        return postJpaRepository.save(post);
    }
}
