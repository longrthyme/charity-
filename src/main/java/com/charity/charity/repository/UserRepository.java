package com.charity.charity.repository;

import com.charity.charity.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface UserRepository extends JpaRepository<User, Long> {
    boolean existsByUsername(String username);

    Optional<User> findByUsername(String username);

    List<User> findByFullNameContainingIgnoreCase(String fullName);

    Optional<User> findByEmail(String email);
}