package com.example.studentmanagement.controllers;

import com.example.studentmanagement.models.Student;
import com.example.studentmanagement.services.StudentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

import java.util.List;

@Controller
@RequestMapping("/admin/students")
public class StudentController {

    @Autowired
    private StudentService studentService;

    @GetMapping
    public String manageStudents(Model model) {
    // Retrieve all students from the database
    List<Student> students = studentService.getAllStudents();
    model.addAttribute("content", "manage_students");
    model.addAttribute("students", students); // Pass the list of students to the view
    return "layout"; // Use the common layout
    }


    @PostMapping("/student/register")
    public String registerStudent(@RequestParam("name") String name,
                                  @RequestParam("email") String email,
                                  @RequestParam("gender") String gender,
                                  @RequestParam("address") String address,
                                  Model model) {
        try {
            studentService.registerStudent(name, email, gender, address);
            model.addAttribute("successMessage", "Student registered successfully!");
        } catch (Exception e) {
            model.addAttribute("errorMessage", "Registration failed: " + e.getMessage());
        }
        return "student_register";
    }


    @GetMapping("/add")
    public String addStudentPage(Model model) {
    model.addAttribute("content", "add_student");
    return "layout";
    }

    @PostMapping("/add")
    public String addStudent(@RequestParam("name") String name,
                         @RequestParam("email") String email,
                         @RequestParam("gender") String gender,
                         @RequestParam("address") String address,
                         RedirectAttributes redirectAttributes) {
    try {
        studentService.registerStudent(name, email, gender, address);
        redirectAttributes.addFlashAttribute("successMessage", "Student added successfully!");
    } catch (Exception e) {
        redirectAttributes.addFlashAttribute("errorMessage", "Error adding student: " + e.getMessage());
    }
    return "redirect:/admin/students";
    }

        @GetMapping("/edit/{id}")
    public String editStudentPage(@PathVariable Long id, Model model) {
        Student student = studentService.getStudentById(id);
        model.addAttribute("student", student);
        model.addAttribute("content", "edit_student");
        return "layout";
    }

    @PostMapping("/edit/{id}")
    public String updateStudent(@PathVariable Long id,
                                @RequestParam("name") String name,
                                @RequestParam("email") String email,
                                @RequestParam("gender") String gender,
                                @RequestParam("address") String address,
                                RedirectAttributes redirectAttributes) {
        try {
            studentService.updateStudent(id, name, email, gender, address);
            redirectAttributes.addFlashAttribute("successMessage", "Student updated successfully!");
        } catch (Exception e) {
            redirectAttributes.addFlashAttribute("errorMessage", "Error updating student: " + e.getMessage());
        }
        return "redirect:/admin/students";
    }

    @PostMapping("/delete/{id}")
    public String deleteStudent(@PathVariable Long id, RedirectAttributes redirectAttributes) {
    try {
        studentService.deleteStudent(id);
        redirectAttributes.addFlashAttribute("successMessage", "Student deleted successfully!");
    } catch (Exception e) {
        redirectAttributes.addFlashAttribute("errorMessage", "Error deleting student: " + e.getMessage());
    }
    return "redirect:/admin/students";
    }
}
