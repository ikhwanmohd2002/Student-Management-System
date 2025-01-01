package com.example.studentmanagement.controllers;


import com.example.studentmanagement.models.Attendance;
import com.example.studentmanagement.services.AttendanceService;
import com.example.studentmanagement.services.EnrollmentService;
import com.example.studentmanagement.services.SubjectService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.time.LocalDate;
import java.util.List;


@Controller
@RequestMapping("/admin/attendance")
public class AttendanceController {

    @Autowired
    private AttendanceService attendanceService;

    @Autowired
    private EnrollmentService enrollmentService;

    @Autowired
    private SubjectService subjectService;

    @GetMapping
    public String manageAttendance(Model model) {
        List<Attendance> attendanceList = attendanceService.getAllAttendance();
        model.addAttribute("attendanceList", attendanceList);
        model.addAttribute("content", "manage_attendance");
        return "layout";
    }

    @PostMapping("/delete/{id}")
    public String deleteAttendance(@PathVariable Long id) {
        attendanceService.deleteAttendance(id);
        return "redirect:/admin/attendance";
    }


        @GetMapping("/bulk")
        public String bulkAttendancePage(Model model) {
            model.addAttribute("subjects", subjectService.getAllSubjects());
            model.addAttribute("content", "bulk_attendance_form");
            return "layout";
        }


    @PostMapping("/bulk")
    public String processBulkAttendance(@RequestParam("subjectId") Long subjectId,
                                         @RequestParam("date") String date,
                                         RedirectAttributes redirectAttributes) {
        try {
            attendanceService.markBulkAttendance(subjectId, LocalDate.parse(date));
            redirectAttributes.addFlashAttribute("successMessage", "Attendance added for all students!");
        } catch (Exception e) {
            redirectAttributes.addFlashAttribute("errorMessage", "Error: " + e.getMessage());
        }
        return "redirect:/admin/attendance/bulk";
    }


    @PostMapping("/mark")
    public String markIndividualAttendance(@RequestParam("enrollmentId") Long enrollmentId,
                                           @RequestParam("date") String date,
                                           @RequestParam("status") String status,
                                           RedirectAttributes redirectAttributes) {
        try {
            attendanceService.markIndividualAttendance(enrollmentId, LocalDate.parse(date), status);
            redirectAttributes.addFlashAttribute("successMessage", "Attendance marked successfully!");
        } catch (Exception e) {
            redirectAttributes.addFlashAttribute("errorMessage", "Error: " + e.getMessage());
        }
        return "redirect:/admin/attendance";
    }
}

