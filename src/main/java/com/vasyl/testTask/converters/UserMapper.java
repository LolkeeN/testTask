package com.vasyl.testTask.converters;

import com.vasyl.testTask.dto.UserDto;
import com.vasyl.testTask.dto.UserDtoWithPassword;
import com.vasyl.testTask.entity.User;
import org.springframework.stereotype.Component;

@Component
public class UserMapper {
    public User toUser(UserDtoWithPassword userDto) {
        User user = new User();
        user.setId(userDto.getId());
        user.setName(userDto.getName());
        user.setEmail(userDto.getEmail());
        user.setEnabled(userDto.isEnabled());
        user.setPassword(userDto.getPassword());
        user.setAvatar(user.getAvatar());
        return user;
    }

    public UserDto toDto(User user) {
        UserDto userDto = new UserDto();
        userDto.setId(user.getId());
        userDto.setName(user.getName());
        userDto.setEmail(user.getEmail());
        userDto.setEnabled(user.isEnabled());
        userDto.setAvatar(user.getAvatar());
        return userDto;
    }
}
