package com.example.studentmanagement.repository;

import java.time.LocalDate;
import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.example.studentmanagement.models.Attendance;

public interface AttendanceRepository extends JpaRepository<Attendance, Long> {

    // Find attendance by enrollment ID
    List<Attendance> findByEnrollmentId(Long enrollmentId);

    // Find attendance by date
    List<Attendance> findByDate(LocalDate date);

    // Find attendance for a specific enrollment on a specific date
    Attendance findByEnrollmentIdAndDate(Long enrollmentId, LocalDate date);

}
