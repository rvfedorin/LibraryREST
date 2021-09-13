package org.frv.service.impl;

import org.frv.config.DataConfigForTest;
import org.frv.config.SecurityConfig;
import org.frv.dto.AddressDto;
import org.frv.dto.UserToSaveDto;
import org.frv.model.User;
import org.frv.service.IUserService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit.jupiter.web.SpringJUnitWebConfig;

import javax.transaction.Transactional;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.hasSize;
import static org.hamcrest.Matchers.is;

/**
 * @author Roman V. Fedorin
 */
@ActiveProfiles("test")
@SpringJUnitWebConfig({DataConfigForTest.class, SecurityConfig.class})
@Transactional
class UserServiceTest {

    @Autowired
    IUserService userService;

    @Test
    void findUserByLogin() {
        User user = userService.findUserByLogin("rf");
        assertThat(user.getLogin(), is("rf"));
        assertThat(user.getFirstName(), is("Admin"));
    }

    @Test
    void saveUser() {
        assertThat(userService.findAll(), hasSize(2));
        User userFromDb = userService.findUserByLogin("rf");
        UserToSaveDto userToSaveDto = new UserToSaveDto(
                "FirstName",
                "LastName",
                "login123",
                "pass",
                "",
                "user",
                new AddressDto(userFromDb.getAddress())
        );
        userService.saveUser(userToSaveDto);
        assertThat(userService.findAll(), hasSize(3));
    }

    @Test
    void updateUser() {
        User userFromDb = userService.findUserByLogin("rf");
        UserToSaveDto userToSaveDto = new UserToSaveDto(
                "FirstName",
                "LastName",
                "login123",
                "pass",
                "",
                "user",
                new AddressDto(userFromDb.getAddress())
        );
        userService.saveUser(userToSaveDto);

        userFromDb = userService.findUserByLogin("login123");
        assertThat(userFromDb.getLogin(), is("login123"));
        assertThat(userFromDb.getFirstName(), is("FirstName"));
        assertThat(userFromDb.getLastName(), is("LastName"));
        userToSaveDto = new UserToSaveDto(
                "FirstNameNew",
                "LastNameNew",
                "login123",
                "pass",
                "",
                "user",
                new AddressDto(userFromDb.getAddress())
        );

        userService.updateUser(userToSaveDto);
        assertThat(userFromDb.getLogin(), is("login123"));
        assertThat(userFromDb.getFirstName(), is("FirstNameNew"));
        assertThat(userFromDb.getLastName(), is("LastNameNew"));
    }

    @Test
    void deleteUser() {
        assertThat(userService.findAll(), hasSize(2));
        User userFromDb = userService.findUserByLogin("rf");
        UserToSaveDto userToSaveDto = new UserToSaveDto();
        userToSaveDto.setLogin(userFromDb.getLogin());
        userService.deleteUser(userToSaveDto);
        assertThat(userService.findAll(), hasSize(1));
    }
}