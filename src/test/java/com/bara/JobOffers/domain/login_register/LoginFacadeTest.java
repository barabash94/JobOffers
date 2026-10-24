package com.bara.JobOffers.domain.login_register;

import com.bara.JobOffers.domain.login_register.dto.RegisterUserDto;
import com.bara.JobOffers.domain.login_register.dto.RegistrationResultDto;
import com.bara.JobOffers.domain.login_register.dto.UserDto;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertThrows;

class LoginFacadeTest {
    LoginFacade loginFacade = new LoginFacade(new UserRepositoryInMemoryImplementation());

    @Test
    public void it_should_be_able_to_register() {
        //given
        RegisterUserDto user = new RegisterUserDto("Bara", "password");
        //when
        RegistrationResultDto register = loginFacade.register(user);
        //then
        assertThat(register.created()).isEqualTo(true);

    }

    @Test
    public void it_should_be_able_to_find_user_by_userName() {
        //given
        RegisterUserDto user = new RegisterUserDto("Bara", "password");
        RegistrationResultDto registeredUser = loginFacade.register(user);
        //when
        UserDto userToFind = loginFacade.findByUserName(user.name());
        //then
        assertThat(userToFind).isEqualTo(new UserDto(registeredUser.id(),"Bara","password"));


    }

    @Test
    public void it_should_be_able_to_throw_user_not_found_exception_when_user_not_found() {
        //given
       RegisterUserDto user = new RegisterUserDto("Bara",  "password");
        loginFacade.register(user);
        //when
        //then
        assertThrows(
                UserNotFoundException.class,
                () -> loginFacade.findByUserName("Unknown")
        );


    }

}