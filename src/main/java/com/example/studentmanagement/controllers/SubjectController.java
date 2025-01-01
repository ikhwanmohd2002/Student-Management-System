package com.example.studentmanagement.controllers;

import com.example.studentmanagement.models.Subject;
import com.example.studentmanagement.services.SubjectService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Controller
@RequestMapping("/admin/subjects")
public class SubjectController {

    @Autowired
    private SubjectService subjectService;

    @GetMapping
    public String manageSubjects(Model model) {
        List<Subject> subjects = subjectService.getAllSubjects();
        model.addAttribute("subjects", subjects);
        model.addAttribute("content", "manage_subjects");
        return "layout";
    }

    @GetMapping("/add")
    public String addSubjectPage(Model model) {
        model.addAttribute("content", "add_subject");
        return "layout";
    }

    @PostMapping("/add")
    public String addSubject(@RequestParam("name") String name,
                             @RequestParam("description") String description) {
        subjectService.createSubject(name, description);
        return "redirect:/admin/subjects";
    }

    @GetMapping("/edit/{id}")
    public String editSubjectPage(@PathVariable Long id, Model model) {
        Subject subject = subjectService.getSubjectById(id);
        model.addAttribute("subject", subject);
        model.addAttribute("content", "edit_subject");
        return "layout";
    }

    @PostMapping("/edit/{id}")
    public String editSubject(@PathVariable Long id,
                              @RequestParam("name") String name,
                              @RequestParam("description") String description) {
        subjectService.updateSubject(id, name, description);
        return "redirect:/admin/subjects";
    }

    @PostMapping("/delete/{id}")
    public String deleteSubject(@PathVariable Long id) {
        subjectService.deleteSubject(id);
        return "redirect:/admin/subjects";
    }
}
