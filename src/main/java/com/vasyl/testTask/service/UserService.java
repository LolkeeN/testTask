package com.vasyl.testTask.service;

import com.vasyl.testTask.dto.UserDto;
import com.vasyl.testTask.dto.UserDtoWithPassword;
import com.vasyl.testTask.entity.User;

import java.util.List;

public interface UserService {
    List<User> findAllUsers();

    void createUser(User user);

    User createUser(UserDtoWithPassword userDto);

    void deleteUser(int id);

    User findById(int id);

    void updateUser(int id, UserDtoWithPassword userDto);

    List<UserDto> findAllUsersPageable(Integer pageNo, Integer pageSize, String sortBy);

}
