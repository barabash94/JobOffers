package com.bara.JobOffers.domain.login_register;

import com.bara.JobOffers.domain.login_register.dto.RegisterUserDto;
import com.bara.JobOffers.domain.login_register.dto.RegistrationResultDto;
import com.bara.JobOffers.domain.login_register.dto.UserDto;
import lombok.AllArgsConstructor;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.stereotype.Component;

@Component
@AllArgsConstructor
public class LoginAndRegisterFacade {
    private static final String USER_NOT_FOUND = "User not found";

    private final LoginRepository repository;

    public UserDto findByUsername(String username) {
        return repository.findByUsername(username)
                .map(user -> new UserDto(user.id(), user.password(), user.username()))
                .orElseThrow(() -> new BadCredentialsException(USER_NOT_FOUND));
    }

    public RegistrationResultDto register(RegisterUserDto registerUserDto) {
        final User user = User.builder()
                .username(registerUserDto.name())
                .password(registerUserDto.password())
                .build();
        User savedUser = repository.save(user);
        return new RegistrationResultDto(savedUser.id(),savedUser.username(), true );
    }
}
