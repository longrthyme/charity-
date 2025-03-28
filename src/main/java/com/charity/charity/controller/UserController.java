package com.charity.charity.controller;


import com.charity.charity.entity.User;
import com.charity.charity.service.impl.UserServiceImpl;
import com.charity.charity.utils.PasswordHasher;
import jakarta.servlet.http.HttpSession;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

@Controller
@AllArgsConstructor
public class UserController {

    private static final Logger logger = LogManager.getLogger(UserController.class);

    private final UserServiceImpl userService;
    private final PasswordHasher passwordEncoder;

    @GetMapping("/login")
    public String getLoginPage() {
        return "login_page.html";
    }

    @PostMapping("/login")
    public String loginUser(
            @RequestParam String username,
            @RequestParam String password,
            HttpSession session,
            Model model) {

        logger.info("Registering user: {} and {}", username, password);

        User user = userService.findByUsername(username).get();

        if (user != null && passwordEncoder.matches(password, user.getPassword())) {
            // Lưu thông tin user vào session
            session.setAttribute("loggedUser", user);
            return "home_page.html";
        } else {
            return "redirect:/login?error";
        }
    }


    @GetMapping("/register")
    public String registerPage() {
        return "register_page.html";
    }


    @PostMapping("/register")
    public String registerUser(@ModelAttribute User user) {

        boolean result = userService.registerUser(user);

        if (result) {
            return "redirect:/register?success";
        } else {
            return "redirect:/register?error";
        }
    }




}
