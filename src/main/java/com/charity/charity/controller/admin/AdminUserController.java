package com.charity.charity.controller.admin;


import com.charity.charity.dto.UserDTO;
import com.charity.charity.entity.User;
import com.charity.charity.service.UserService;
import jakarta.servlet.http.HttpServletRequest;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Controller
//@RequestMapping("")
public class AdminUserController {

    private static final Logger log = LoggerFactory.getLogger(AdminUserController.class);
    @Autowired
    private UserService userService;

    @GetMapping("/admin/users")
    public String listUsers(@RequestParam(required = false) String search,
                            Model model,
                            HttpServletRequest request) {
        List<User> users = userService.getUsers(search);
        model.addAttribute("users", users);
        model.addAttribute("currentURI", request.getRequestURI()); // Truyền URL vào Thymeleaf
        return "users_list";    }

    @PostMapping("/admin/delete-user")
    public ResponseEntity<?> deleteUser(@RequestParam String email) {

            boolean isDeleted = userService.deleteUserByEmail(email);
            Map<String, String> response = new HashMap<>();

            if (isDeleted) {
                response.put("status", "success");
                response.put("message", "Người dùng đã được xóa.");
                return ResponseEntity.ok(response);  // ✅ Return JSON
            } else {
                response.put("status", "error");
                response.put("message", "Không tìm thấy người dùng!");
                return ResponseEntity.status(HttpStatus.NOT_FOUND).body(response);
            }

    }


    @GetMapping("/admin/users/toggle/{id}")
    public String toggleStatus(@PathVariable Long id) {
        userService.toggleUserStatus(id);
        return "redirect:/admin/users";
    }


    @PutMapping("/admin/update-user/{id}")
    public ResponseEntity<?> updateUser(@PathVariable Long id, @RequestBody UserDTO userDTO) {
        try {
            boolean isUpdated = userService.updateUser(id, userDTO);
            if (isUpdated) {
                return ResponseEntity.ok("success");
            } else {
                return ResponseEntity.status(HttpStatus.NOT_FOUND)
                        .body("Người dùng không tồn tại!");
            }
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body("Lỗi khi cập nhật người dùng.");
        }
    }


    @PostMapping("/admin/create-user")
    public ResponseEntity<Map<String, String>> addUser(@RequestBody UserDTO userDTO, RedirectAttributes redirectAttributes) {
        System.out.println("value post  " + userDTO);
        try {
            log.debug("data " + userDTO);
            userService.createUser(userDTO);
            Map<String, String> response = new HashMap<>();
            response.put("message", "Người dùng đã được thêm thành công!");
            return ResponseEntity.ok(response);
        } catch (Exception e) {
            log.error("error " + e.getMessage());
            Map<String, String> errorResponse = new HashMap<>();
            errorResponse.put("error", "Lỗi: " + e.getMessage());
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(errorResponse);
        }
    }
}
