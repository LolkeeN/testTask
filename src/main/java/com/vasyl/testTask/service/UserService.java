package com.vasyl.testTask.service;

import com.vasyl.testTask.dto.ChangePasswordDto;
import com.vasyl.testTask.dto.GetUserDto;
import com.vasyl.testTask.dto.CreateUserDto;
import com.vasyl.testTask.entity.User;

import java.util.List;

public interface UserService {
    List<GetUserDto> findAllUsers();

    void createUser(CreateUserDto userDto);

    void deleteUser(int id);

    GetUserDto findById(int id);

    void updateUser(int id, CreateUserDto userDto);

    List<GetUserDto> findAllUsersPageable(Integer pageNo, Integer pageSize, String sortBy);

    void updateEmail(Integer userId, String email);

    void changePassword(Integer userId, ChangePasswordDto dto);
}
