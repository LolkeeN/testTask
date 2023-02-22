package com.vasyl.testTask.service.impl;

import com.vasyl.testTask.dto.AvatarDto;
import com.vasyl.testTask.entity.Avatar;
import com.vasyl.testTask.entity.User;
import com.vasyl.testTask.exceptions.AvatarNotFoundException;
import com.vasyl.testTask.exceptions.UserNotFoundException;
import com.vasyl.testTask.repository.AvatarRepository;
import com.vasyl.testTask.repository.UserRepository;
import com.vasyl.testTask.service.AvatarService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.convert.ConversionService;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

@Service
public class AvatarServiceImpl implements AvatarService {

    @Autowired
    private AvatarRepository avatarRepository;
    @Autowired
    private UserRepository userRepository;
    @Autowired
    private ConversionService conversionService;

    @Override
    @Transactional
    public void createAvatar(AvatarDto avatarDto) {
        if (!userRepository.userExists(avatarDto.getUserId())) {
            throw new UserNotFoundException("No user found with this id " + avatarDto.getUserId());
        }
        User user = userRepository.findById(avatarDto.getUserId()).get();
        Avatar avatar = conversionService.convert(avatarDto, Avatar.class);
        avatar.setUser(user);
        avatarRepository.save(avatar);
    }

    @Override
    @Transactional
    public void delete(Integer userId) {
        if (!userRepository.userExists(userId)) {
            throw new UserNotFoundException("No user found with this id " + userId);
        }
        avatarRepository.deleteById(userId);
    }

    @Override
    public AvatarDto get(Integer userId) {
        if (!userRepository.userExists(userId)) {
            throw new UserNotFoundException("No user found with this id " + userId);
        }
        Optional<Avatar> optionalAvatar = avatarRepository.findById(userId);

        Avatar avatar = optionalAvatar.orElseThrow(
                () -> new AvatarNotFoundException("No avatar found for user with id " + userId)
        );

        return conversionService.convert(avatar, AvatarDto.class);
    }
}
