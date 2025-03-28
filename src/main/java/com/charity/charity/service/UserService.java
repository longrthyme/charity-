package com.charity.charity.service;

import com.charity.charity.entity.User;

import java.util.Optional;

public interface UserService {
    boolean registerUser(User user);

    Optional<User> findByUsername(String username);
}