package com.charity.charity.controller;


import com.charity.charity.dto.UserDonationRequest;
import com.charity.charity.entity.Donation;
import com.charity.charity.entity.User;
import com.charity.charity.entity.UserDonation;
import com.charity.charity.service.impl.DonationServiceImpl;
import com.charity.charity.service.impl.UserServiceImpl;
import com.charity.charity.utils.PasswordHasher;
import jakarta.servlet.http.HttpSession;
import lombok.AllArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.List;
import java.util.stream.Collectors;

@Controller
@AllArgsConstructor
public class UserController {

    private static final Logger logger = LogManager.getLogger(UserController.class);

    private final UserServiceImpl userService;
    private final PasswordHasher passwordEncoder;

    private final DonationServiceImpl donationService;

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


            boolean isAdmin = user.getRoles().stream()
                    .anyMatch(role -> role.getName().equalsIgnoreCase("ADMIN"));

            if (isAdmin) {
                return "redirect:/admin/users";  // Chuyển hướng trang admin
            } else {
                return "redirect:/";
            }

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


    @GetMapping("/")
    public String getAllDonations(
                                  @RequestParam(defaultValue = "0") int page,
                                  @RequestParam(defaultValue = "5") int size,
                                  Model model) {

        SimpleDateFormat inputFormat = new SimpleDateFormat("yyyy-MM-dd");
        SimpleDateFormat outputFormat = new SimpleDateFormat("dd/MM/yyyy");

        Page<Donation> donationPage = donationService.getDonations("",PageRequest.of(page, size));

        List<Donation> donations = donationPage.getContent().stream()
                .map(donation -> {
                    try {
                        donation.setStartDate(outputFormat.format(inputFormat.parse(donation.getStartDate())));
                        donation.setEndDate(outputFormat.format(inputFormat.parse(donation.getEndDate())));
                    } catch (ParseException e) {
                        e.printStackTrace();
                    }
                    return donation;
                })
                .collect(Collectors.toList());

        model.addAttribute("donations", donations);
        model.addAttribute("currentPage", page);
        model.addAttribute("totalPages", donationPage.getTotalPages());


        return "home_page";  // Tên file Thymeleaf
    }


    @GetMapping("/donation/detail/{id}")
    public String showDonationDetail(@PathVariable Integer id, Model model) {
        Donation donation = donationService.getDonationById(String.valueOf(id));
        List<UserDonation> donors = donationService.getDonorsByDonationId(id);

        model.addAttribute("donation", donation);
        model.addAttribute("donors", donors);

        logger.debug("donation " + donation);
        logger.debug("donation er  " + donors);

        return "donation-detail"; // Thymeleaf template name
    }


    @PostMapping("/donation/add/user")
    public ResponseEntity<?> createDonation(@RequestBody UserDonationRequest request, HttpSession session) {
        // Get the logged-in user from the session
        User loggedUser = (User) session.getAttribute("loggedUser");

        if (loggedUser == null) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("User not logged in");
        }

        // Call the service method to create a new donation
        UserDonation newDonation = donationService.createDonation(request, loggedUser);

        return ResponseEntity.ok(newDonation);
    }
}
