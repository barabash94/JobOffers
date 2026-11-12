package com.bara.JobOffers.domain.login_register;

import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;
@Repository
public interface LoginRepository  extends MongoRepository<User, String> {

    User save(User user);


    Optional<User> findByUsername(String username);
}
