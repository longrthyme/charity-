package com.charity.charity.service;

import com.charity.charity.dto.UserDTO;
import com.charity.charity.entity.User;

import java.util.List;
import java.util.Optional;

public interface UserService {
    boolean registerUser(User user);

    Optional<User> findByUsername(String username);

    List<User> getUsers(String search);

    void deleteUser(Long id);

    void toggleUserStatus(Long id);

    void createUser(UserDTO userDTO);
}