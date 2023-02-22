package com.vasyl.testTask.service.impl;

import com.vasyl.testTask.dto.ChangePasswordDto;
import com.vasyl.testTask.dto.GetUserDto;
import com.vasyl.testTask.dto.CreateUserDto;
import com.vasyl.testTask.entity.User;
import com.vasyl.testTask.entity.UserRole;
import com.vasyl.testTask.entity.enums.Role;
import com.vasyl.testTask.exceptions.PasswordsNotEqualException;
import com.vasyl.testTask.exceptions.UserNotFoundException;
import com.vasyl.testTask.repository.UserRepository;
import com.vasyl.testTask.repository.UserRoleRepository;
import com.vasyl.testTask.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.convert.ConversionService;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class UserServiceImpl implements UserService {
    @Autowired
    private UserRepository userRepository;

    @Autowired
    private ConversionService conversionService;

    @Autowired
    private UserRoleRepository userRoleRepository;

    @Autowired
    private PasswordEncoder encoder;

    @Override
    public List<GetUserDto> findAllUsers() {
        List<User> users = userRepository.findAll();
        return users.stream()
                .map(x -> conversionService.convert(x, GetUserDto.class))
                .collect(Collectors.toList());
    }

    @Override
    @Transactional
    public void createUser(CreateUserDto userDto) {
        User user = conversionService.convert(userDto, User.class);
        encryptPassword(user);
        user = userRepository.save(user);
        UserRole userRole = new UserRole();
        userRole.setRole(Role.USER);
        userRole.setUser(user);
        userRoleRepository.save(userRole);
    }

    private void encryptPassword(User user) {
        user.setPassword(encoder.encode(user.getPassword()));
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

    @Override
    public void updateEmail(Integer userId, String email) {
        User byId = userRepository.findById(userId).orElseThrow(
                () -> new UserNotFoundException("No user found")
        );

        byId.setEmail(email);
        userRepository.save(byId);
    }

    @Override
    public void changePassword(Integer userId, ChangePasswordDto dto) {
        User user = userRepository.findById(userId).orElseThrow(
                () -> new UserNotFoundException("No user found")
        );

        if (!Objects.equals(user.getPassword(), encoder.encode(dto.getOldPassword()))) {
            throw new PasswordsNotEqualException("You've entered wrong old password");
        }
        if (!Objects.equals(dto.getNewPassword(), dto.getNewPasswordConfirmation())) {
            throw new PasswordsNotEqualException("Confirm password and new password are different");
        }
        user.setPassword(encoder.encode(dto.getNewPassword()));
        userRepository.save(user);
    }


}
