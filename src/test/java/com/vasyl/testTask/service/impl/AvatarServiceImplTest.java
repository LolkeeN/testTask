package com.vasyl.testTask.service.impl;

import com.vasyl.testTask.dto.AvatarDto;
import com.vasyl.testTask.entity.Avatar;
import com.vasyl.testTask.entity.User;
import com.vasyl.testTask.exceptions.UserNotFoundException;
import com.vasyl.testTask.repository.AvatarRepository;
import com.vasyl.testTask.repository.UserRepository;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.core.convert.ConversionService;

import java.util.Optional;

@ExtendWith(MockitoExtension.class)
public class AvatarServiceImplTest {
    @Mock
    private AvatarRepository avatarRepository;
    @Mock
    private UserRepository userRepository;
    @Mock
    private ConversionService conversionService;

    @InjectMocks
    private AvatarServiceImpl avatarService;

    @Test
    void avatarServiceDeleteShouldDeleteUser() {
        //GIVEN
        Mockito.when(userRepository.userExists(Mockito.anyInt())).thenReturn(true);
        //WHEN
        avatarService.delete(Mockito.anyInt());
        //THEN
        Mockito.verify(avatarRepository).deleteById(Mockito.anyInt());
    }

    @Test
    void avatarServiceDeleteShouldThrowExceptionWhenNoUserFound() {
        //GIVEN
        //WHEN
        //THEN
        Assertions.assertThrows(UserNotFoundException.class, () -> avatarService.delete(Mockito.anyInt()));
    }

    @Test
    void avatarServiceCreateShouldThrowExceptionWhenUserNotFound() {
        //GIVEN
        AvatarDto avatarDto = new AvatarDto();

        //WHEN
        //THEN
        Assertions.assertThrows(UserNotFoundException.class, () -> avatarService.createAvatar(avatarDto));

    }

    @Test
    void avatarServiceCreateShouldCreateAvatar(){
        //GIVEN
        Mockito.when(userRepository.userExists(null)).thenReturn(true);
        AvatarDto avatarDto = new AvatarDto();
        Mockito.when(userRepository.findById(avatarDto.getUserId())).thenReturn(Optional.of(new User()));
        Avatar avatar = new Avatar();
        Mockito.when(conversionService.convert(avatarDto, Avatar.class)).thenReturn(avatar);
        //WHEN
        avatarService.createAvatar(avatarDto);
        //THEN
        Mockito.verify(avatarRepository).save(avatar);
    }

    @Test
    void avatarServiceGetShouldThrowExceptionWhenNoUserFound() {
        //GIVEN
        //WHEN
        //THEN
        Assertions.assertThrows(UserNotFoundException.class, () -> avatarService.get(Mockito.anyInt()));
    }

    @Test
    void avatarServiceGetShouldFindAvatar() {
        //GIVEN
        Mockito.when(userRepository.userExists(0)).thenReturn(true);
        Avatar avatar = new Avatar();
        Mockito.when(avatarRepository.findById(Mockito.anyInt())).thenReturn(Optional.of(avatar));
        //WHEN
        avatarService.get(Mockito.anyInt());
        //THEN
        Mockito.verify(conversionService).convert(avatar, AvatarDto.class);
    }

}
