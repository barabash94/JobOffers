package com.bara.JobOffers.domain.login_register;

import com.bara.JobOffers.domain.login_register.dto.UserDataResponseDto;

import java.util.HashMap;
import java.util.Optional;
import java.util.UUID;

class UserRepository {

     private HashMap<UUID,UserData> users=new HashMap<>();

    public void save(UserData userData) {
        users.put(UUID.randomUUID(),userData);
    }

    public Optional<UserDataResponseDto> findByUserName(String name) {
        return users.values()
                .stream()
                .filter(user->user.name().equals(name))
                .map(user->new UserDataResponseDto(user.name(),user.email()))
                .findFirst();

    }
}
