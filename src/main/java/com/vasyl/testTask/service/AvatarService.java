package com.vasyl.testTask.service;

import com.vasyl.testTask.dto.AvatarDto;

public interface AvatarService {
    void createAvatar(AvatarDto avatarDto);

    void delete(Integer userId);

    AvatarDto get(Integer userId);
}
