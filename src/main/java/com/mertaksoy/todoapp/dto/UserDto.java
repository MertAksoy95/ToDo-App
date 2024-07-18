package com.mertaksoy.todoapp.dto;

import lombok.Builder;
import lombok.Data;

@Builder
@Data
public class UserDto {

    private String username;
    private String firstName;
    private String lastName;
    private String password;

}
