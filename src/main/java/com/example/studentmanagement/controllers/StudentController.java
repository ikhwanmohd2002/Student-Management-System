package com.example.studentmanagement.controllers;

import com.example.studentmanagement.services.StudentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class StudentController {

    @Autowired
    private StudentService studentService;

    @GetMapping("/student/register")
    public String showStudentRegister() {
        return "student_register"; // Points to student_register.html
    }

    @PostMapping("/student/register")
    public String registerStudent(@RequestParam("name") String name,
                                  @RequestParam("email") String email,
                                  @RequestParam("gender") String gender,
                                  @RequestParam("address") String address,
                                  Model model) {
        try {
            studentService.registerStudent(name, email, gender, address);
            model.addAttribute("successMessage", "Student registered successfully! Check logs for password.");
        } catch (Exception e) {
            model.addAttribute("errorMessage", "Registration failed: " + e.getMessage());
        }
        return "student_register";
    }
}
