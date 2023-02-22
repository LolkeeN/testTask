package com.vasyl.testTask.service.impl;

import com.vasyl.testTask.dto.CreateUserDto;
import com.vasyl.testTask.dto.GetUserDto;
import com.vasyl.testTask.entity.User;
import com.vasyl.testTask.repository.UserRepository;
import com.vasyl.testTask.repository.UserRoleRepository;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.core.convert.ConversionService;
import org.springframework.data.domain.Pageable;
import org.springframework.security.crypto.password.PasswordEncoder;

import java.util.ArrayList;
import java.util.List;

@ExtendWith(MockitoExtension.class)
public class UserServiceImplTest {
    @Mock
    private UserRepository userRepository;

    @Mock
    private ConversionService conversionService;

    @Mock
    private UserRoleRepository userRoleRepository;

    @Mock
    private PasswordEncoder encoder;

    @InjectMocks
    private UserServiceImpl userService;

    @Test
    void userServiceFindAllShouldFindAllUsers() {
        //GIVEN
        //WHEN
        userService.findAllUsers();
        //THEN
        Mockito.verify(userRepository).findAll();
    }

    @Test
    void userServiceDeleteUserShouldDeleteUser(){
        //GIVEN
        User user = new User();
        Mockito.when(userRepository.findById(Mockito.anyInt())).thenReturn(user);
        //WHEN
        userService.deleteUser(Mockito.anyInt());
        //THEN
        Mockito.verify(userRepository).delete(user);
    }

    @Test
    void userServiceFindByIdShouldFindById(){
        //GIVEN
        User user = new User();
        Mockito.when(userRepository.findById(Mockito.anyInt())).thenReturn(user);
        Mockito.when(conversionService.convert(user, GetUserDto.class)).thenReturn(new GetUserDto());
        //WHEN
        userService.findById(Mockito.anyInt());
        //THEN
        Mockito.verify(userRepository).findById(Mockito.anyInt());
        Mockito.verify(conversionService).convert(user, GetUserDto.class);
    }

    @Test
    void userServiceUpdateUserShouldUpdateUser(){
        //GIVEN
        User user = new User();
        Mockito.when(userRepository.findById(Mockito.anyInt())).thenReturn(user);
        //WHEN
        userService.updateUser(Mockito.anyInt(), new CreateUserDto());
        //THEN
        Mockito.verify(userRepository).save(user);
    }

}

