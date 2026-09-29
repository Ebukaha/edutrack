package com.example.project.Service;

import com.example.project.Entity.Course;
import com.example.project.Entity.Student;
import com.example.project.Repository.CourseRepository;
import com.example.project.Repository.Repository;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http .ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class StudentServiceImpl implements StudentService {

    private Student student;

    @Autowired
    public Repository repository;

    @Autowired
    private CourseRepository courseRepository;

    @Override
    public List<Student> GetStudents() {
        return repository.findAll();
    }

    @Override
    public Student AddStudent(Student student) {
        Student saved = repository.save(student);
        syncCourseEnrollment(saved.getCourse());
        return saved;
    }

    @Override
    public Student GetstudentbyId(int id) {
        return repository.findById(id).orElseThrow(() -> new RuntimeException("Student not found with id: " + id));
    }

    @Override
    public Student UpdateStudent(int id, Student student) {

        Student existingStudent = repository.findById(id)
                .orElseThrow(() -> new RuntimeException("Student not found with id: " + id));

        String oldCourse = existingStudent.getCourse();

        existingStudent.setFirstName(student.getFirstName());
        existingStudent.setLastName(student.getLastName());
        existingStudent.setEmail(student.getEmail());
        existingStudent.setPhone(student.getPhone());
        existingStudent.setStudentId(student.getStudentId());
        existingStudent.setDob(student.getDob());
        existingStudent.setCourse(student.getCourse());
        existingStudent.setYearOfStudy(student.getYearOfStudy());
        existingStudent.setGender(student.getGender());
        existingStudent.setStatus(student.getStatus());
        existingStudent.setAddress(student.getAddress());
        existingStudent.setNotes(student.getNotes());

        Student updated = repository.save(existingStudent);
        syncCourseEnrollment(oldCourse);
        syncCourseEnrollment(updated.getCourse());
        return updated;
    }

    @Override
    public void deleteS(int id) {
        Student existing = repository.findById(id).orElse(null);
        if (existing == null) {
            throw new RuntimeException("Student not found with id: " + id);
        }

        repository.deleteById(id);
        syncCourseEnrollment(existing.getCourse());
    }

    private void syncCourseEnrollment(String courseNameOrCode) {
        if (courseNameOrCode == null || courseNameOrCode.isBlank()) return;
        List<Course> courses = courseRepository.findAll();
        for (Course c : courses) {
            String code = c.getCourseCode() == null ? "" : c.getCourseCode().toLowerCase();
            String name = c.getCourseName() == null ? "" : c.getCourseName().toLowerCase();
            String target = courseNameOrCode.toLowerCase();

            if ((!code.isEmpty() && target.contains(code)) || (!name.isEmpty() && target.contains(name))) {
                long count = repository.findAll().stream()
                        .filter(s -> {
                            if (s.getCourse() == null) return false;
                            String sCourse = s.getCourse().toLowerCase();
                            return (!code.isEmpty() && sCourse.contains(code)) || (!name.isEmpty() && sCourse.contains(name));
                        })
                        .count();
                c.setEnrolled((int) count);
                courseRepository.save(c);
            }
        }
    }
}
