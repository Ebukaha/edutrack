package com.example.project.Service;

import com.example.project.Entity.Course;
import com.example.project.Repository.CourseRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class CourseServiceImpl implements CourseService {

    @Autowired
    private final CourseRepository repository;

    @Override
    public List<Course> getCourses() {
        return repository.findAll();
    }

    @Override
    public Course addCourse(Course course) {
        return repository.save(course);
    }

    @Override
    public Course getCourseById(int id) {

        return repository.findById(id)
                .orElseThrow(() -> new RuntimeException("Course not found"));
    }

    @Override
    public Course updateCourse(int id, Course course) {

        Course existingCourse = repository.findById(id)
                .orElseThrow(() -> new RuntimeException("Course not found"));

        existingCourse.setCourseName(course.getCourseName());
        existingCourse.setCourseCode(course.getCourseCode());
        existingCourse.setInstructor(course.getInstructor());
        existingCourse.setCredits(course.getCredits());
        existingCourse.setEnrolled(course.getEnrolled());
        existingCourse.setCapacity(course.getCapacity());
        existingCourse.setStatus(course.getStatus());
        existingCourse.setDepartment(course.getDepartment());
        existingCourse.setDescription(course.getDescription());

        return repository.save(existingCourse);
    }

    @Override
    public void deleteCourse(int id) {

        if (!repository.existsById(id)) {
            throw new RuntimeException("Course not found");
        }

        repository.deleteById(id);
    }
}