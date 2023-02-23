package com.vasyl.testTask.controller;

import com.vasyl.testTask.converters.UserToGetUserDtoConverter;
import com.vasyl.testTask.dto.ChangePasswordDto;
import com.vasyl.testTask.dto.GetUserDto;
import com.vasyl.testTask.dto.CreateUserDto;
import com.vasyl.testTask.service.UserService;
import lombok.AllArgsConstructor;
import org.springframework.security.access.annotation.Secured;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;
import java.util.List;

@RestController
@RequestMapping("/admin/users")
@AllArgsConstructor
public class AdminController {
    private final UserService userService;
    private final UserToGetUserDtoConverter userMapper;

    @GetMapping
    @PreAuthorize("hasRole('ROLE_ADMIN')")
    public List<GetUserDto> getAllUsers() {
        return userService.findAllUsers();
    }

    @DeleteMapping("/{id}")
    @PreAuthorize("hasRole('ROLE_ADMIN')")
    public void deleteUser(@PathVariable int id) {
        userService.deleteUser(id);
    }

    @PutMapping("/{id}")
    @PreAuthorize("hasRole('ROLE_ADMIN')")
    public void changeUser(@PathVariable int id, @Valid @RequestBody CreateUserDto userDto) {
        userService.updateUser(id, userDto);
    }

    @GetMapping("/{id}")
    @PreAuthorize("hasRole('ROLE_ADMIN')")

    public GetUserDto getUserById(@PathVariable int id) {
        return userService.findById(id);
    }

    @GetMapping("/all/pagination")
    @PreAuthorize("hasRole('ROLE_ADMIN')")
    public List<GetUserDto> getAllUsersPageable(@RequestParam(defaultValue = "0") Integer pageNo,
                                                @RequestParam(defaultValue = "10") Integer pageSize,
                                                @RequestParam(defaultValue = "id") String sortBy) {
        return userService.findAllUsersPageable(pageNo, pageSize, sortBy);
    }

    @PutMapping("/{id}/password")
    @PreAuthorize("hasRole('ROLE_ADMIN')")
    public void changePassword(@PathVariable Integer id, @RequestBody ChangePasswordDto userDto){

    }
}
