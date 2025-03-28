package com.charity.charity.controller;


import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
@RequiredArgsConstructor
public class UserController {


    @GetMapping("/login")
    public String getLoginPage() {
        return "login_page";
    }



}
