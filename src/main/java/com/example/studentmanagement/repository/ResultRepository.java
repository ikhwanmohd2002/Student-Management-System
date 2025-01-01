package com.example.studentmanagement.repository;


import com.example.studentmanagement.models.Result;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface ResultRepository extends JpaRepository<Result, Long> {

    // Find all results for a specific enrollment
    List<Result> findByEnrollmentId(Long enrollmentId);
}

