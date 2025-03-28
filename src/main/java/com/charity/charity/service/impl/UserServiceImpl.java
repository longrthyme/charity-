package com.charity.charity.service.impl;

import com.charity.charity.entity.Role;
import com.charity.charity.entity.User;
import com.charity.charity.repository.RoleRepository;
import com.charity.charity.repository.UserRepository;
import com.charity.charity.service.UserService;
import com.charity.charity.utils.PasswordHasher;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Collections;
import java.util.List;
import java.util.Optional;

@Service
public class UserServiceImpl implements UserService {
    @Autowired
    private UserRepository userRepository;

    @Autowired
    private RoleRepository roleRepository;

    @Autowired
    private PasswordHasher passwordEncoder;



    @Override
    public boolean registerUser(User user) {
        System.out.println("register " + user );
        if (userRepository.existsByUsername(user.getUsername())) {
            return false; // Username đã tồn tại
        }
        user.setPassword(passwordEncoder.hashPassword(user.getPassword())); // Mã hóa mật khẩu
        user.setEmail(user.getEmail());
        user.setUsername(user.getUsername());
        Role userRole = roleRepository.findByName("ROLE_USER")
                .orElseThrow(() -> new RuntimeException("Role not found"));
        user.setRoles(Collections.singleton(userRole));
        userRepository.save(user);
        return true;
    }
    @Override
    public Optional<User> findByUsername(String username) {
        return userRepository.findByUsername(username);
    }

    @Override
    public List<User> getUsers(String search) {
        if (search == null || search.isEmpty()) {
            return userRepository.findAll();
        }

        return userRepository.findByFullNameContainingIgnoreCase(search);
    }


    @Override
    public void deleteUser(Long id) {
        userRepository.deleteById(id);
    }

    @Override
    public void toggleUserStatus(Long id) {
        userRepository.findById(id).ifPresent(user -> {
            user.setStatus(user.getStatus() == 1 ? 0 : 1); // Toggle status (1 -> 0, 0 -> 1)
            userRepository.save(user);
        });
    }
}