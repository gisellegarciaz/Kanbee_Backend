package com.kanbee.api.application.dto;

import lombok.Data;

@Data
public class CreateUserDTO {
    private String email;
    private String password;
    private String name;
    private String avatarUrl;
}