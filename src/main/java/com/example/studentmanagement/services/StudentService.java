package com.example.studentmanagement.services;

import com.example.studentmanagement.models.Student;
import com.example.studentmanagement.repository.StudentRepository;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class StudentService {

    @Autowired
    private StudentRepository studentRepository;



    public void registerStudent(String name, String email, String gender, String address) {
    if (studentRepository.findByEmail(email) != null) {
        throw new IllegalArgumentException("Email already exists");
    }

    Student student = new Student();
    student.setName(name);
    student.setEmail(email);
    student.setGender(gender);
    student.setAddress(address);
    studentRepository.save(student);
    }

    public List<Student> getAllStudents() {
        return studentRepository.findAll(); // Fetch all students from the repository
    }



    public Student getStudentById(Long id) {
        return studentRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("Student not found with ID: " + id));
    }
    
    public void updateStudent(Long id, String name, String email, String gender, String address) {
        Student student = getStudentById(id);
        student.setName(name);
        student.setEmail(email);
        student.setGender(gender);
        student.setAddress(address);
        studentRepository.save(student);
    }

    public void deleteStudent(Long id) {
        studentRepository.deleteById(id);
    }
    
}
