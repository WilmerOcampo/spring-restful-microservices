package com.ocs.restfulweb.user.persistence;

import com.ocs.restfulweb.exception.NotFoundException;
import lombok.RequiredArgsConstructor;
import org.jspecify.annotations.NonNull;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
@RequiredArgsConstructor
public class UserService {

    private final JpaUserRepository jpaUserRepository;

    public List<User> findAll() {
        return jpaUserRepository.findAll();
    }

    public Page<User> findAll(Pageable pageable) {
        return jpaUserRepository.findAll(pageable);
    }

    public User save(User user) {
        return jpaUserRepository.save(user);
    }

    public User findById(int id) {
        return jpaUserRepository.findById(id)
                .orElseThrow(() -> notFoundException(id));
    }

    public void deleteById(int id) {
        if (!jpaUserRepository.existsById(id)) {
            throw notFoundException(id);
        }
        jpaUserRepository.deleteById(id);
    }

    private static @NonNull NotFoundException notFoundException(int id) {
        return new NotFoundException(String.format("User %s not found", id));
    }
}
