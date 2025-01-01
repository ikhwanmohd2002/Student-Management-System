package com.example.studentmanagement.controllers;

import com.example.studentmanagement.models.Enrollment;
import com.example.studentmanagement.models.Student;
import com.example.studentmanagement.models.Subject;
import com.example.studentmanagement.services.EnrollmentService;
import com.example.studentmanagement.services.StudentService;
import com.example.studentmanagement.services.SubjectService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.util.List;

@Controller
@RequestMapping("/admin/enrollments")
public class EnrollmentController {

    @Autowired
    private EnrollmentService enrollmentService;

    @Autowired
    private StudentService studentService;

    @Autowired
    private SubjectService subjectService;

    @GetMapping
    public String manageEnrollments(Model model) {
        List<Enrollment> enrollments = enrollmentService.getAllEnrollments();
        model.addAttribute("enrollments", enrollments);
        model.addAttribute("content", "manage_enrollments");
        return "layout";
    }

    @GetMapping("/add")
    public String addEnrollmentPage(Model model) {
        model.addAttribute("students", studentService.getAllStudents());
        model.addAttribute("subjects", subjectService.getAllSubjects());
        model.addAttribute("content", "add_enrollment");
        return "layout";
    }

    @PostMapping("/add")
public String addEnrollment(@RequestParam("studentId") Long studentId,
                             @RequestParam("subjectId") Long subjectId,
                             RedirectAttributes redirectAttributes) {
    try {
        enrollmentService.createEnrollment(studentId, subjectId);
        redirectAttributes.addFlashAttribute("successMessage", "Enrollment added successfully!");
        return "redirect:/admin/enrollments";
    } catch (IllegalArgumentException e) {
        redirectAttributes.addFlashAttribute("errorMessage", e.getMessage());
    } catch (Exception e) {
        redirectAttributes.addFlashAttribute("errorMessage", "Cannot enroll student to same subjects");
    }
    return "redirect:/admin/enrollments/add";
}

    @PostMapping("/delete/{id}")
    public String deleteEnrollment(@PathVariable Long id) {
        enrollmentService.deleteEnrollment(id);
        return "redirect:/admin/enrollments";
    }
}
