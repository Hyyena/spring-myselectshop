package com.sparta.myselectshop.controller.dto.response;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class UserResponse {

    String username;
    boolean isAdmin;
}