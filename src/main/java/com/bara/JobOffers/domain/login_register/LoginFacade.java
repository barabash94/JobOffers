package com.bara.JobOffers.domain.login_register;

import com.bara.JobOffers.domain.login_register.dto.UserDataResponseDto;

import java.util.Optional;

public class LoginFacade {
    private UserRepository userRepository;

    public LoginFacade(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    public String register(UserData userData) {
        userRepository.save(userData);
        return "User registered successfully";
    }

    public UserDataResponseDto findByUserName(String name) {
        UserDataResponseDto user = userRepository.findByUserName(name)
                .orElseThrow(() -> new UserNotFoundException("User with name" + name + " not found"));

        return user;
    }
}
