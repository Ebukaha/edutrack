package com.example.project.service;

import com.example.project.entity.Course;
import com.example.project.exception.ResourceNotFoundException;
import com.example.project.repository.CourseRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class CourseServiceImpl implements CourseService {

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
                .orElseThrow(() -> new ResourceNotFoundException("Course", "id", id));
    }

    @Override
    public Course updateCourse(int id, Course course) {
        Course existingCourse = repository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Course", "id", id));

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
            throw new ResourceNotFoundException("Course", "id", id);
        }

        repository.deleteById(id);
    }
}