package com.vasyl.testTask.converters;

import com.vasyl.testTask.dto.AvatarDto;
import com.vasyl.testTask.entity.Avatar;
import org.springframework.core.convert.converter.Converter;
import org.springframework.stereotype.Component;

@Component
public class AvatarDtoToAvatarConverter implements Converter<AvatarDto, Avatar> {
    @Override
    public Avatar convert(AvatarDto source) {
        Avatar avatar = new Avatar();

        avatar.setContent(source.getContent());
        avatar.setFileName(source.getFileName());

        return avatar;
    }
}
