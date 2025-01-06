package com.example.studentmanagement.repository;

import java.time.LocalDate;
import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.example.studentmanagement.models.Attendance;

public interface AttendanceRepository extends JpaRepository<Attendance, Long> {

    List<Attendance> findByEnrollmentId(Long enrollmentId);

    List<Attendance> findByDate(LocalDate date);

    Attendance findByEnrollmentIdAndDate(Long enrollmentId, LocalDate date);

}
