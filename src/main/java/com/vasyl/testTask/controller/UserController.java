package com.vasyl.testTask.controller;

import com.vasyl.testTask.aspects.annotations.CurrentUserId;
import com.vasyl.testTask.dto.ChangePasswordDto;
import com.vasyl.testTask.dto.GetUserDto;
import com.vasyl.testTask.exceptions.PasswordsNotEqualException;
import com.vasyl.testTask.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.annotation.Secured;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;
import javax.validation.constraints.Email;
import java.util.Objects;

@RestController
@RequestMapping("/users")
public class UserController {

    @Autowired
    private UserService userService;

    @PutMapping("/email")
    @PreAuthorize("hasRole('ROLE_ADMIN') or hasRole('ROLE_USER')")
    public void updateEmail(@CurrentUserId Integer userId, @Email @RequestBody String newEmail) {
        userService.updateEmail(userId, newEmail);
    }

    @PutMapping("/password")
    @PreAuthorize("hasRole('ROLE_ADMIN') or hasRole('ROLE_USER')")
    public void updatePassword(@CurrentUserId Integer userId, @RequestBody ChangePasswordDto dto) {
        userService.changePassword(userId, dto);
    }

}
