package com.bara.JobOffers.domain.login_register;

import com.bara.JobOffers.domain.login_register.dto.UserDataResponseDto;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertThrows;

class LoginFacadeTest {
    LoginFacade loginFacade = new LoginFacade(new UserRepository());

    @Test
    public void it_should_be_able_to_register() {
        //given
        UserData user = new UserData("Bara", "simple@email.com", "password");
        //when
        String register = loginFacade.register(user);
        //then
        assertThat(register).isEqualTo("User registered successfully");

    }
    @Test
    public void it_should_be_able_to_find_user_by_userName() {
        //given
        UserData user = new UserData("Bara", "simple@email.com", "password");
         loginFacade.register(user);
        UserDataResponseDto userFromDataBase = new UserDataResponseDto(user.name(),user.email());
        //when
        UserDataResponseDto userToFind= loginFacade.findByUserName(user.name());
        //then
        assertThat(userToFind).isEqualTo(userFromDataBase);


    }
    @Test
    public void it_should_be_able_to_throw_user_not_found_exception_when_user_not_found() {
        //given
        UserData user = new UserData("Bara", "simple@email.com", "password");
        loginFacade.register(user);
        UserDataResponseDto userFromDataBase = new UserDataResponseDto(user.name(),user.email());
        //then
        assertThrows(
                UserNotFoundException.class,
                () -> loginFacade.findByUserName("Unknown")
        );


    }

}