package com.vasyl.testTask.service.impl;

import com.vasyl.testTask.converters.UserMapper;
import com.vasyl.testTask.dto.UserDto;
import com.vasyl.testTask.dto.UserDtoWithPassword;
import com.vasyl.testTask.entity.User;
import com.vasyl.testTask.repository.UserRepository;
import com.vasyl.testTask.service.UserService;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@AllArgsConstructor
public class UserServiceImpl implements UserService {
    private UserRepository userRepository;
    private UserMapper userMapper;

    @Override
    public List<User> findAllUsers() {
        return userRepository.findAll();
    }

    @Override
    public void createUser(User user) {
        userRepository.save(user);
    }

    @Override
    public User createUser(UserDtoWithPassword userDto) {
        User user = userMapper.toUser(userDto);
        return userRepository.saveAndFlush(user);
    }

    @Override
    public void deleteUser(int id) {
        userRepository.delete(userRepository.findById(id).orElseThrow(
                () -> new IllegalArgumentException("No user found with such id")));
    }

    @Override
    public User findById(int id) {
        return userRepository.findById(id).orElseThrow(
                () -> new IllegalArgumentException("No user found with such id"));
    }

    @Override
    public void updateUser(int id, UserDtoWithPassword userDto) {
        User user = userRepository.findById(id).orElseThrow(
                () -> new IllegalArgumentException("No user found with such id")
        );
        user.setName(userDto.getName());
        user.setEmail(userDto.getEmail());
        if (userDto.getPassword() != null) {
            user.setPassword(userDto.getPassword());
        }

        userRepository.save(user);
    }
}
