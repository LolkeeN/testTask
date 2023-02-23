package com.vasyl.testTask.security.dto;

import lombok.Data;

import javax.validation.constraints.Email;
import javax.validation.constraints.Size;

@Data
public class LoginDto {
    @Email
    private String email;
    @Size(min = 8, max = 255)
    private String password;
}
