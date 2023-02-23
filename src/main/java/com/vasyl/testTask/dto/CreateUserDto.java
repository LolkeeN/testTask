package com.vasyl.testTask.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.NonNull;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class CreateUserDto {
    @NonNull
    private String username;
    @NonNull
    @Email
    private String email;
    @NotBlank
    @NotEmpty
    @NotNull
    @Size(min = 8, max = 255, message = "Password length should be between 8 and 20")
    private String password;

}
