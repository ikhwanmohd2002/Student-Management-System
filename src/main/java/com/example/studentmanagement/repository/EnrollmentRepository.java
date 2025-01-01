package com.example.studentmanagement.repository;

import com.example.studentmanagement.models.Enrollment;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.List;

public interface EnrollmentRepository extends JpaRepository<Enrollment, Long> {
    List<Enrollment> findBySubjectId(Long subjectId);
}
