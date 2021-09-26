package com.loginsystem.loginsystem.model;

import lombok.Data;

@Data
public class AuthResponse {
    private String accessToken;
    private boolean isSuccess;
}
