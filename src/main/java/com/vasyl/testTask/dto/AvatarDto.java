package com.vasyl.testTask.dto;

import lombok.Data;

@Data
public class AvatarDto {
    private Integer userId;

    private String fileName;

    private byte[] content;
}
