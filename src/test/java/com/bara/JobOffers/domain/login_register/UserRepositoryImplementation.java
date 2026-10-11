package com.bara.JobOffers.domain.login_register;

import java.util.HashMap;
import java.util.Optional;
import java.util.UUID;

public class UserRepositoryImplementation implements UserRepository {

    private HashMap<UUID,User> users = new HashMap<>();


    @Override
    public User save(User user) {
        users.put(UUID.randomUUID(),user);
        return user;
    }

    @Override
    public Optional<User> findByUserName(String name) {
        return users.values().stream()
                .filter(user -> user.name().equals(name))
                .findFirst();

    }
}
