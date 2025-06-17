package com.example.demo.dto.request;

import lombok.Data;

@Data
public class UserUpdateRequest {
    private Integer userId;
    private String userName;
    private String userPass;
    private Integer userVersion;
}
