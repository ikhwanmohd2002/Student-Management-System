package com.example.studentmanagement.services;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.studentmanagement.models.Enrollment;
import com.example.studentmanagement.models.Result;
import com.example.studentmanagement.repository.EnrollmentRepository;
import com.example.studentmanagement.repository.ResultRepository;

@Service
public class ResultService {

    @Autowired
    private ResultRepository resultRepository;

    @Autowired
    private EnrollmentRepository enrollmentRepository;

    public List<Result> getAllResults() {
        return resultRepository.findAll();
    } 

    public List<Result> getResultsByEnrollmentId(Long enrollmentId) {
        return resultRepository.findByEnrollmentId(enrollmentId);
    }

    public void createResult(Long enrollmentId, String title, int marks) {
        Enrollment enrollment = enrollmentRepository.findById(enrollmentId)
                .orElseThrow(() -> new IllegalArgumentException("Enrollment not found with ID: " + enrollmentId));

        String grade = calculateGrade(marks); // Automatically determine grade based on marks

        Result result = new Result();
        result.setEnrollment(enrollment);
        result.setTitle(title);
        result.setMarks(marks);
        result.setGrade(grade); // Set grade dynamically
        resultRepository.save(result);
    }

    public void updateMarks(Long resultId, int marks) {
        Result result = resultRepository.findById(resultId)
                .orElseThrow(() -> new IllegalArgumentException("Result not found with ID: " + resultId));

        result.setMarks(marks); // Update marks
        result.setGrade(calculateGrade(marks)); // Recalculate grade
        resultRepository.save(result);
    }

    public void addResultsForSubject(Long subjectId, String title) {
        List<Enrollment> enrollments = enrollmentRepository.findBySubjectId(subjectId);
    
        for (Enrollment enrollment : enrollments) {
            Result result = new Result();
            result.setEnrollment(enrollment);
            result.setTitle(title);
            result.setMarks(0); // Default marks
            result.setGrade("F"); // Default grade
            resultRepository.save(result);
        }
    }

    public void deleteResult(Long id) {
        resultRepository.deleteById(id);
    }

    private String calculateGrade(int marks) {
        if (marks >= 80) {
            return "A";
        } else if (marks >= 70) {
            return "B";
        } else if (marks >= 60) {
            return "C";
        } else if (marks >= 50) {
            return "D";
        } else {
            return "F";
        }
    }
}
