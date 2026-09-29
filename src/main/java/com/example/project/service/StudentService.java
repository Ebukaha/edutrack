package com.example.project.service;

import com.example.project.entity.Student;

import java.util.List;

public interface StudentService {

    List<Student> getStudents();

    Student addStudent(Student student);

    Student getStudentById(int id);

    Student updateStudent(int id, Student student);

    void deleteStudent(int id);
}
