package com.charity.charity.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class UserDTO {
    private String fullName;
    private String email;
    private String phone;
    private String address;
    private String username;
    private String password;
    private String role;

    // Getters và Setters
}