package com.example.studentmanagement.repository;

import com.example.studentmanagement.models.Admin;
import org.springframework.data.jpa.repository.JpaRepository;

public interface AdminRepository extends JpaRepository<Admin, Long>{
    Admin findByUsername(String username);
}
