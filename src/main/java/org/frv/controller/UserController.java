package org.frv.controller;

import jakarta.validation.ConstraintViolation;
import jakarta.validation.Validator;
import lombok.extern.slf4j.Slf4j;
import org.frv.dto.MessageDto;
import org.frv.dto.UserForAdminDto;
import org.frv.dto.UserToSaveDto;
import org.frv.exceptions.NoContentException;
import org.frv.exceptions.ValidationError;
import org.frv.model.User;
import org.frv.service.IUserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.annotation.Secured;
import org.springframework.web.bind.annotation.*;

import java.util.*;

/**
 * @author Roman V.F.
 * Date: 13.08.2021
 */
@Slf4j
@RestController
@RequestMapping("/user")
public class UserController {

    @Autowired
    private IUserService userService;

    @Autowired
    private Validator validator;

    @Secured("ROLE_ADMIN")
    @GetMapping("/all")
    public ResponseEntity<List<UserForAdminDto>> getAllUsers() {
        List<User> usersInDB = userService.findAll();

        if (usersInDB.isEmpty()) {
            ResourceBundle bundle = ResourceBundle.getBundle("messages");
            String message = bundle.getString("user_no_content");
            log.warn(ResourceBundle.getBundle("messages", Locale.ENGLISH).getString("user_no_content"));
            throw new NoContentException(message);
        }

        List<UserForAdminDto> responseUsers = new ArrayList<>();
        for (User user : usersInDB) {
            responseUsers.add(new UserForAdminDto(user));
        }

        return ResponseEntity.ok(responseUsers);
    }

    @Secured("ROLE_ADMIN")
    @GetMapping("/getByLogin")
    public ResponseEntity<UserForAdminDto> getUserByLogin(@RequestParam String login) {
        UserForAdminDto responseUser = new UserForAdminDto(userService.findUserByLogin(login));
        return ResponseEntity.ok(responseUser);
    }

    @Secured("ROLE_ADMIN")
    @PostMapping("/createByAdmin")
    public ResponseEntity<UserToSaveDto> createUserWithAdmin(@RequestBody UserToSaveDto userToSave) {
        validateUser(userToSave);
        if (userToSave.getRoles() == null) {
            userToSave.setRoles("user");
        }
        userService.saveUser(userToSave);
        log.info("Saved user: " + userToSave);
        return ResponseEntity.ok(userToSave);
    }

    @PostMapping("/register")
    public ResponseEntity<UserToSaveDto> createUser(@RequestBody UserToSaveDto userToSave) {
        validateUser(userToSave);
        userToSave.setRoles("user");
        userService.saveUser(userToSave);
        log.info("Saved user: " + userToSave);
        return ResponseEntity.ok(userToSave);
    }

    @PostMapping("/change")
    public ResponseEntity<UserToSaveDto> changeUser(@RequestBody UserToSaveDto userToChange) {
        validateUser(userToChange);
        userToChange.setRoles("user");
        userService.updateUser(userToChange);
        log.info("Changed user: " + userToChange);
        return ResponseEntity.ok(userToChange);
    }

    @Secured("ROLE_ADMIN")
    @PostMapping("/changeByAdmin")
    public ResponseEntity<UserToSaveDto> changeUserByAdmin(@RequestBody UserToSaveDto userToChange) {
        validateUser(userToChange);
        userService.updateUser(userToChange);
        log.info("Changed user: " + userToChange);
        return ResponseEntity.ok(userToChange);
    }

    @Secured("ROLE_ADMIN")
    @PostMapping("/delete")
    public ResponseEntity<MessageDto> deleteUser(@RequestBody UserToSaveDto userToDelete) {
        userService.deleteUser(userToDelete);
        String messageToResponse = "Deleted a user with login: " + userToDelete.getLogin();
        log.info(messageToResponse);
        return ResponseEntity.ok(new MessageDto(messageToResponse));
    }

    private void validateUser(UserToSaveDto userToValidate) {
        Set<ConstraintViolation<UserToSaveDto>> constraintViolationSet = validator.validate(userToValidate);
        if (constraintViolationSet.size() > 0) {
            StringBuilder errorMessageBuilder = new StringBuilder();
            for (ConstraintViolation<UserToSaveDto> userToSaveDtoConstraintViolation : constraintViolationSet) {
                errorMessageBuilder.append(userToSaveDtoConstraintViolation.getMessage()).append("; ");
            }
            String errorMessage = errorMessageBuilder.toString();
            log.warn(errorMessage);
            throw new ValidationError(errorMessage);
        }
    }

}
