package com.vasyl.testTask.dto;

import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;

@Data
@RequiredArgsConstructor
@NoArgsConstructor
public class UserDto {
    private int id;
    @NonNull
    private String name;
    @NonNull
    private String email;
    private boolean enabled = true;
    private String avatar;

}
