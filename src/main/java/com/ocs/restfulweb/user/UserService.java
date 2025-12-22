package com.ocs.restfulweb.user;

import org.springframework.stereotype.Component;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

@Component
public class UserService {

    private static final List<User> users = new ArrayList<>();
    private static int usersCount = 0;

    static {
        users.add(new User(++usersCount, "Wilmer", LocalDate.now().minusYears(26)));
        users.add(new User(++usersCount, "Kely Ayde", LocalDate.now().minusYears(27)));
        users.add(new User(++usersCount, "John", LocalDate.now().minusYears(20)));
    }

    public List<User> findAll() {
        return users;
    }

    public User save(User user) {
        user.setId(++usersCount);
        users.add(user);
        return user;
    }

    public User findById(int id) {
        return users.stream()
                .filter(u -> u.getId().equals(id))
                .findFirst()
                .orElse(null);
    }

    public void deleteById(int id) {
        users.removeIf(u -> u.getId().equals(id));
    }
}
