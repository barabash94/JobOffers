package com.bara.JobOffers.domain.login_register;

import java.util.Optional;

public interface UserRepository {

    User save(User user);


    Optional<User> findByUserName(String name);
}
