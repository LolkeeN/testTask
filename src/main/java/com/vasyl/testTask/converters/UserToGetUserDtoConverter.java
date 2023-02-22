package com.vasyl.testTask.converters;

import com.vasyl.testTask.dto.GetUserDto;
import com.vasyl.testTask.dto.CreateUserDto;
import com.vasyl.testTask.entity.User;
import org.springframework.core.convert.converter.Converter;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

@Component
public class UserToGetUserDtoConverter implements Converter<User, GetUserDto> {

    @Override
    public GetUserDto convert(User user) {
        GetUserDto getUserDto = new GetUserDto();
        getUserDto.setId(user.getId());
        getUserDto.setUsername(user.getUsername());
        getUserDto.setEmail(user.getEmail());
        getUserDto.setAvatarUrl(getAvatarDownloadUrl(user.getId()));
        return getUserDto;
    }

    private String getAvatarDownloadUrl(Integer userId) {
        String baseUrl = ServletUriComponentsBuilder.fromCurrentContextPath().build().toUriString();

        return String.format("%s/users/avatar/%s", baseUrl, userId);
    }

}
