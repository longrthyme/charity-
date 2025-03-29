package com.charity.charity.service.impl;

import com.charity.charity.dto.UserDTO;
import com.charity.charity.entity.Role;
import com.charity.charity.entity.User;
import com.charity.charity.repository.RoleRepository;
import com.charity.charity.repository.UserRepository;
import com.charity.charity.service.UserService;
import com.charity.charity.utils.PasswordHasher;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import java.util.*;

@Service
public class UserServiceImpl implements UserService {

    private static final Logger logger = LogManager.getLogger(UserServiceImpl.class);

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
    public boolean updateUser(Long id, UserDTO userDTO) {
        Optional<User> optionalUser = userRepository.findById(id);
        if (optionalUser.isPresent()) {
            User user = optionalUser.get();
            user.setFullName(userDTO.getFullName());
            user.setPhoneNumber(userDTO.getPhone());
            user.setAddress(userDTO.getAddress());
//            user.setRole(userDTO.getRole()); // Role should be updated only if necessary

            userRepository.save(user);
            return true;
        }
        return false;
    }


    @Override
    public boolean deleteUserByEmail(String email) {
        Optional<User> user = userRepository.findByEmail(email);
        if (user.isPresent()) {
            userRepository.delete(user.get());
            return true;
        }
        return false;
    }

    @Override
    public void toggleUserStatus(Long id) {
        userRepository.findById(id).ifPresent(user -> {
            user.setStatus(user.getStatus() == 1 ? 0 : 1); // Toggle status (1 -> 0, 0 -> 1)
            userRepository.save(user);
        });
    }

    @Override
    public void createUser(UserDTO userDTO) {

        logger.debug("value object " + userDTO);
        User user = new User();
        user.setFullName(userDTO.getFullName());
        user.setEmail(userDTO.getEmail());
        user.setPhoneNumber(userDTO.getPhone());
        user.setAddress(userDTO.getAddress());
        user.setUsername(userDTO.getUsername());
        user.setPassword(passwordEncoder.hashPassword(userDTO.getPassword()));
        Role role = roleRepository.findByName(userDTO.getRole())
                .orElseThrow(() -> new RuntimeException("Role not found"));
        Set<Role> roles = new HashSet<>();
        roles.add(role);
        user.setRoles(roles);

        userRepository.save(user);
    }
}