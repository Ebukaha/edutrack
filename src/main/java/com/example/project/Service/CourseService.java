package com.example.project.Service;

import com.example.project.Entity.Course;

import java.util.List;

public interface CourseService {

    List<Course> getCourses();

    Course addCourse(Course course);

    Course getCourseById(int id);

    Course updateCourse(int id, Course course);

    void deleteCourse(int id);
}