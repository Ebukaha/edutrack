package com.example.project.Service;

import com.example.project.Entity.Student;

import java.util.List;

public interface StudentService {

    public List<Student> GetStudents();


    public Student AddStudent(Student student);

    public Student GetstudentbyId(int id);

    public  Student UpdateStudent(int id, Student  student );

    public void  deleteS(int id);
}
