package com.vasyl.testTask.converters;

import com.vasyl.testTask.dto.AvatarDto;
import com.vasyl.testTask.entity.Avatar;
import org.springframework.core.convert.converter.Converter;
import org.springframework.stereotype.Component;

@Component
public class AvatarToAvatarDtoConverter implements Converter<Avatar, AvatarDto> {

    @Override
    public AvatarDto convert(Avatar source) {
        AvatarDto avatarDto = new AvatarDto();
        avatarDto.setUserId(source.getId());
        avatarDto.setContent(source.getContent());
        avatarDto.setFileName(source.getFileName());

        return avatarDto;
    }
}
