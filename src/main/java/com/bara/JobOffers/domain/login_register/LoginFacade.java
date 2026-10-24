package com.bara.JobOffers.domain.login_register;

import com.bara.JobOffers.domain.login_register.dto.RegisterUserDto;
import com.bara.JobOffers.domain.login_register.dto.RegistrationResultDto;
import com.bara.JobOffers.domain.login_register.dto.UserDto;

public class LoginFacade {
    private final UserRepository userRepository;

    public LoginFacade(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    public RegistrationResultDto register(RegisterUserDto registerUserDto) {
        User user = User.builder()
                .name(registerUserDto.name())
                .password(registerUserDto.password())
                .build();
        User savedUser = userRepository.save(user);
        return new RegistrationResultDto(savedUser.id(), savedUser.name(), true);
    }

    public UserDto findByUserName(String name) {
        return userRepository.findByUserName(name)
                .map(user -> new UserDto(user.id(), user.name(), user.password()))
                .orElseThrow(() -> new UserNotFoundException("User not found"));

    }
}
