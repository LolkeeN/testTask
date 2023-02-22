package com.vasyl.testTask.service.impl;

import com.vasyl.testTask.dto.GetUserDto;
import com.vasyl.testTask.dto.CreateUserDto;
import com.vasyl.testTask.entity.User;
import com.vasyl.testTask.entity.UserRole;
import com.vasyl.testTask.entity.enums.Role;
import com.vasyl.testTask.repository.UserRepository;
import com.vasyl.testTask.service.UserService;
import lombok.AllArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.core.convert.ConversionService;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class UserServiceImpl implements UserService {
    @Autowired
    private UserRepository userRepository;

    @Autowired
    private ConversionService conversionService;

    @Override
    public List<GetUserDto> findAllUsers() {
        List<User> users = userRepository.findAll();
        return users.stream()
                .map(x -> conversionService.convert(x, GetUserDto.class))
                .collect(Collectors.toList());
    }

    @Override
    public User createUser(CreateUserDto userDto) {
        User user = conversionService.convert(userDto, User.class);
        UserRole userRole = new UserRole();
        userRole.setRole(Role.USER);
        user.setRoles(List.of(userRole));
        return userRepository.saveAndFlush(user);
    }

    @Override
    public void deleteUser(int id) {
        userRepository.delete(userRepository.findById(id));
    }

    @Override
    public GetUserDto findById(int id) {

        User user = userRepository.findById(id);
        return conversionService.convert(user, GetUserDto.class);
    }

    @Override
    public void updateUser(int id, CreateUserDto userDto) {
        User user = userRepository.findById(id);
        user.setUsername(userDto.getUsername());
        user.setEmail(userDto.getEmail());
        if (userDto.getPassword() != null) {
            user.setPassword(userDto.getPassword());
        }

        userRepository.save(user);
    }

    @Override
    public List<GetUserDto> findAllUsersPageable(Integer pageNo, Integer pageSize, String sortBy) {
        Pageable paging = PageRequest.of(pageNo, pageSize, Sort.by(sortBy));

        Page<User> pagedResult = userRepository.findAll(paging);

        if (pagedResult.hasContent()) {
            return pagedResult.getContent().stream()
                    .map(x -> conversionService.convert(x, GetUserDto.class))
                    .collect(Collectors.toList());
        } else {
            return new ArrayList<>();
        }
    }


}
