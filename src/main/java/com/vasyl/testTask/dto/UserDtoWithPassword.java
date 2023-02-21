package com.vasyl.testTask.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.NonNull;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class UserDtoWithPassword {
    private int id;
    @NonNull
    private String name;
    @NonNull
    private String email;
    private String password;
    private boolean enabled = true;
    private String avatar;

}
