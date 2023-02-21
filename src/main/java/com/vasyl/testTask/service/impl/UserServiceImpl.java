package com.vasyl.testTask.service.impl;

import com.vasyl.testTask.converters.UserMapper;
import com.vasyl.testTask.dto.UserDto;
import com.vasyl.testTask.dto.UserDtoWithPassword;
import com.vasyl.testTask.entity.User;
import com.vasyl.testTask.repository.UserRepository;
import com.vasyl.testTask.service.UserService;
import lombok.AllArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

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
        userRepository.delete(userRepository.findById(id));
    }

    @Override
    public User findById(int id) {
        return userRepository.findById(id);
    }

    @Override
    public void updateUser(int id, UserDtoWithPassword userDto) {
        User user = userRepository.findById(id);
        user.setName(userDto.getName());
        user.setEmail(userDto.getEmail());
        if (userDto.getPassword() != null) {
            user.setPassword(userDto.getPassword());
        }

        userRepository.save(user);
    }

    @Override
    public List<UserDto> findAllUsersPageable(Integer pageNo, Integer pageSize, String sortBy) {
        Pageable paging = PageRequest.of(pageNo, pageSize, Sort.by(sortBy));

        Page<User> pagedResult = userRepository.findAll(paging);

        if(pagedResult.hasContent()) {
            return pagedResult.getContent().stream().map(x -> userMapper.toDto(x)).collect(Collectors.toList());
        } else {
            return new ArrayList<>();
        }
    }


}
