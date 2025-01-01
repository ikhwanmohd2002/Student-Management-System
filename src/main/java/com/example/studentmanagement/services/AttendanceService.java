package com.example.studentmanagement.services;


import com.example.studentmanagement.models.Attendance;
import com.example.studentmanagement.models.Enrollment;
import com.example.studentmanagement.repository.AttendanceRepository;
import com.example.studentmanagement.repository.EnrollmentRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.List;

@Service
public class AttendanceService {

    @Autowired
    private AttendanceRepository attendanceRepository;

    @Autowired
    private EnrollmentRepository enrollmentRepository;

    public List<Attendance> getAllAttendance() {
        return attendanceRepository.findAll();
    }

    public List<Attendance> getAttendanceByEnrollmentId(Long enrollmentId) {
        return attendanceRepository.findByEnrollmentId(enrollmentId);
    }

    public List<Attendance> getAttendanceByDate(LocalDate date) {
        return attendanceRepository.findByDate(date);
    }

        // Bulk Attendance Marking
        public void markBulkAttendance(Long subjectId, LocalDate date) {
            List<Enrollment> enrollments = enrollmentRepository.findBySubjectId(subjectId);
    
            for (Enrollment enrollment : enrollments) {
                // Check if attendance already exists
                Attendance existing = attendanceRepository.findByEnrollmentIdAndDate(enrollment.getId(), date);
                if (existing == null) {
                    // Create new attendance record
                    Attendance attendance = new Attendance();
                    attendance.setEnrollment(enrollment);
                    attendance.setDate(date);
                    attendance.setStatus("Absent"); // Default to "Absent"
                    attendanceRepository.save(attendance);
                }
            }
        }

        public void markIndividualAttendance(Long enrollmentId, LocalDate date, String status) {
            Attendance attendance = attendanceRepository.findByEnrollmentIdAndDate(enrollmentId, date);
    
            if (attendance == null) {
                // Create new attendance record
                attendance = new Attendance();
                attendance.setEnrollment(enrollmentRepository.findById(enrollmentId)
                        .orElseThrow(() -> new IllegalArgumentException("Enrollment not found")));
                attendance.setDate(date);
            }
    
            // Update the attendance status
            attendance.setStatus(status);
            attendanceRepository.save(attendance);
        }

    public void markAttendance(Long enrollmentId, LocalDate date, String status) {
        Enrollment enrollment = enrollmentRepository.findById(enrollmentId)
                .orElseThrow(() -> new IllegalArgumentException("Enrollment not found with ID: " + enrollmentId));

        // Check if attendance already exists for the date
        Attendance existingAttendance = attendanceRepository.findByEnrollmentIdAndDate(enrollmentId, date);
        if (existingAttendance != null) {
            throw new IllegalArgumentException("Attendance already marked for this date and enrollment.");
        }

        Attendance attendance = new Attendance();
        attendance.setEnrollment(enrollment);
        attendance.setDate(date);
        attendance.setStatus(status);
        attendanceRepository.save(attendance);
    }

    public void deleteAttendance(Long id) {
        attendanceRepository.deleteById(id);
    }
}

