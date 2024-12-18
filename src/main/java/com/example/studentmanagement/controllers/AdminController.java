package com.example.studentmanagement.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.ui.Model;

import com.example.studentmanagement.services.AdminService;

@Controller
public class AdminController {
    @Autowired
    private AdminService adminService;

    @GetMapping("/login")
    public String login() {
        return "login";
    }

    @PostMapping("/register")
    public String registerAdmin(@RequestParam("username") String username, @RequestParam("password") String password,Model model) {
        try {
            adminService.registerAdmin(username, password);
            model.addAttribute("successMessage", "Registration successful! You can now log in.");
            System.out.println("Registration Successful!");
        } catch (Exception e) {
            System.err.println("Error during registration: " + e.getMessage());
            return "redirect:/register?error";
        }
        return "register";
    }

    @GetMapping("/register")
    public String register() {
        return "register";
    }


    @GetMapping("/home")
    public String home() {
        return "home";
    }
}
