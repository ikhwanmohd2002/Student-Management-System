package com.example.studentmanagement.services;

import com.example.studentmanagement.models.Subject;
import com.example.studentmanagement.repository.SubjectRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class SubjectService {

    @Autowired
    private SubjectRepository subjectRepository;

    public List<Subject> getAllSubjects() {
        return subjectRepository.findAll();
    }

    public Subject getSubjectById(Long id) {
        return subjectRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("Subject not found with ID: " + id));
    }

    public void createSubject(String name, String description) {
        Subject subject = new Subject();
        subject.setName(name);
        subject.setDescription(description);
        subjectRepository.save(subject);
    }

    public void updateSubject(Long id, String name, String description) {
        Subject subject = getSubjectById(id);
        subject.setName(name);
        subject.setDescription(description);
        subjectRepository.save(subject);
    }

    public void deleteSubject(Long id) {
        subjectRepository.deleteById(id);
    }
}
