package com.example.project.controller;

import com.example.project.entity.Course;
import com.example.project.service.CourseService;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@CrossOrigin(origins = {
        "http://localhost:8080",
        "http://localhost:63342"
})
@RequiredArgsConstructor
@RestController
@RequestMapping("/Courses")
public class CourseController {

    private static final Logger log = LoggerFactory.getLogger(CourseController.class);
    private final CourseService courseService;

    @GetMapping
    public List<Course> getAllCourses() {
        return courseService.getCourses();
    }

    @PostMapping
    public Course addCourse(@RequestBody Course course) {
        log.info("Adding course {}", course);
        return courseService.addCourse(course);
    }

    @GetMapping("/{id}")
    public Course getCourseById(@PathVariable int id) {
        return courseService.getCourseById(id);
    }

    @PutMapping("/{id}")
    public Course updateCourse(@PathVariable int id, @RequestBody Course course) {
        return courseService.updateCourse(id, course);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteCourse(@PathVariable int id) {
        courseService.deleteCourse(id);
        return new ResponseEntity<>(HttpStatus.OK);
    }
}
