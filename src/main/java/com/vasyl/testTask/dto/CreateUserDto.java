package com.vasyl.testTask.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.NonNull;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class CreateUserDto {
    @NonNull
    private String username;
    @NonNull
    private String email;
    private String password;

}
