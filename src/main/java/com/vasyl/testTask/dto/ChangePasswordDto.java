package com.vasyl.testTask.dto;

import lombok.Data;

@Data
public class ChangePasswordDto {
    private Integer id;
    private String oldPassword;
    private String newPassword;
}
