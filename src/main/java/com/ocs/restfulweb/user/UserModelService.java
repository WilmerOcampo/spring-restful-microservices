package com.ocs.restfulweb.user;

import org.springframework.stereotype.Component;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

@Component
public class UserModelService {

    private static final List<UserModel> USERS = new ArrayList<>();
    private static int usersCount = 0;

    static {
        USERS.add(new UserModel(++usersCount, "Wilmer", LocalDate.now().minusYears(26)));
        USERS.add(new UserModel(++usersCount, "Kely Ayde", LocalDate.now().minusYears(27)));
        USERS.add(new UserModel(++usersCount, "John", LocalDate.now().minusYears(20)));
    }

    public List<UserModel> findAll() {
        return USERS;
    }

    public UserModel save(UserModel user) {
        user.setId(++usersCount);
        USERS.add(user);
        return user;
    }

    public UserModel findById(int id) {
        return USERS.stream()
                .filter(u -> u.getId().equals(id))
                .findFirst()
                .orElse(null);
    }

    public void deleteById(int id) {
        USERS.removeIf(u -> u.getId().equals(id));
    }
}
