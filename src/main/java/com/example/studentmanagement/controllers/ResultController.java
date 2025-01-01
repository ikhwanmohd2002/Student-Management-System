package com.example.studentmanagement.controllers;


import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.example.studentmanagement.models.Result;
import com.example.studentmanagement.services.EnrollmentService;
import com.example.studentmanagement.services.ResultService;
import com.example.studentmanagement.services.SubjectService;

@Controller
@RequestMapping("/admin/results")
public class ResultController {

    @Autowired
    private ResultService resultService;

    @Autowired
    private EnrollmentService enrollmentService;

    @Autowired
    private SubjectService subjectService;

    @GetMapping
    public String manageResults(Model model) {
        List<Result> results = resultService.getAllResults();
        model.addAttribute("results", results);
        model.addAttribute("content", "manage_results");
        return "layout";
    }

    @GetMapping("/add")
    public String addResultPage(Model model) {
        model.addAttribute("enrollments", enrollmentService.getAllEnrollments());
        model.addAttribute("content", "add_result");
        return "layout";
    }

    @PostMapping("/add")
    public String addResult(@RequestParam("enrollmentId") Long enrollmentId,
                            @RequestParam("title") String title,
                            @RequestParam("marks") int marks,
                            RedirectAttributes redirectAttributes) {
        try {
            resultService.createResult(enrollmentId, title, marks);
            redirectAttributes.addFlashAttribute("successMessage", "Result added successfully!");
        } catch (Exception e) {
            redirectAttributes.addFlashAttribute("errorMessage", "Error adding result: " + e.getMessage());
        }
        return "redirect:/admin/results";
    }

    @PostMapping("/edit/marks")
public String editMarks(@RequestParam("resultId") Long resultId,
                        @RequestParam("marks") int marks,
                        RedirectAttributes redirectAttributes) {
    try {
        resultService.updateMarks(resultId, marks);
        redirectAttributes.addFlashAttribute("successMessage", "Marks updated successfully!");
    } catch (Exception e) {
        redirectAttributes.addFlashAttribute("errorMessage", "Error updating marks: " + e.getMessage());
    }
    return "redirect:/admin/results";
}

@PostMapping("/add/multiple")
public String addResultsForSubject(@RequestParam("subjectId") Long subjectId,
                                    @RequestParam("title") String title,
                                    RedirectAttributes redirectAttributes) {
    try {
        resultService.addResultsForSubject(subjectId, title);
        redirectAttributes.addFlashAttribute("successMessage", "Results added for all students in the subject!");
    } catch (Exception e) {
        redirectAttributes.addFlashAttribute("errorMessage", "Error adding results: " + e.getMessage());
    }
    return "redirect:/admin/results";
}

@GetMapping("/add/multiple")
public String showAddResultsForSubjectForm(Model model) {
    // Fetch all subjects to display in the dropdown
    model.addAttribute("subjects", subjectService.getAllSubjects());
    model.addAttribute("content", "add_results");
    return "layout";
}


    @PostMapping("/delete/{id}")
    public String deleteResult(@PathVariable Long id) {
        resultService.deleteResult(id);
        return "redirect:/admin/results";
    }
}

