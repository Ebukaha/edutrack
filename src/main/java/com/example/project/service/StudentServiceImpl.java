package com.example.project.service;

import com.example.project.entity.Course;
import com.example.project.entity.Student;
import com.example.project.exception.ResourceNotFoundException;
import com.example.project.repository.CourseRepository;
import com.example.project.repository.StudentRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class StudentServiceImpl implements StudentService {

    private final StudentRepository studentRepository;
    private final CourseRepository courseRepository;

    @Override
    public List<Student> getStudents() {
        return studentRepository.findAll();
    }

    @Override
    public Student addStudent(Student student) {
        Student saved = studentRepository.save(student);
        syncCourseEnrollment(saved.getCourse());
        return saved;
    }

    @Override
    public Student getStudentById(int id) {
        return studentRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Student", "id", id));
    }

    @Override
    public Student updateStudent(int id, Student student) {
        Student existingStudent = studentRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Student", "id", id));

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

        Student updated = studentRepository.save(existingStudent);
        syncCourseEnrollment(oldCourse);
        syncCourseEnrollment(updated.getCourse());
        return updated;
    }

    @Override
    public void deleteStudent(int id) {
        Student existing = studentRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Student", "id", id));

        studentRepository.deleteById(id);
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
                long count = studentRepository.findAll().stream()
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
