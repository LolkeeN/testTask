package com.vasyl.testTask.dto;

import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;

@Data
@RequiredArgsConstructor
@NoArgsConstructor
public class GetUserDto {
    private Integer id;

    @NonNull
    private String username;

    @NonNull
    private String email;

    private String avatarUrl;

}
