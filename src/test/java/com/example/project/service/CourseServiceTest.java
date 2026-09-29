package com.example.project.service;

import com.example.project.entity.Course;
import com.example.project.exception.ResourceNotFoundException;
import com.example.project.repository.CourseRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class CourseServiceTest {

    @Mock
    private CourseRepository courseRepository;

    @InjectMocks
    private CourseServiceImpl courseService;

    private Course testCourse;

    @BeforeEach
    void setUp() {
        testCourse = new Course();
        testCourse.setId(1);
        testCourse.setCourseName("Computer Science 101");
        testCourse.setCourseCode("CS101");
        testCourse.setInstructor("Dr. Smith");
        testCourse.setCredits(3);
        testCourse.setCapacity(50);
        testCourse.setStatus("Active");
    }

    @Test
    void getCourses_ShouldReturnCourseList() {
        when(courseRepository.findAll()).thenReturn(List.of(testCourse));

        List<Course> courses = courseService.getCourses();

        assertNotNull(courses);
        assertEquals(1, courses.size());
        assertEquals("CS101", courses.get(0).getCourseCode());
        verify(courseRepository, times(1)).findAll();
    }

    @Test
    void getCourseById_WhenFound_ShouldReturnCourse() {
        when(courseRepository.findById(1)).thenReturn(Optional.of(testCourse));

        Course found = courseService.getCourseById(1);

        assertNotNull(found);
        assertEquals("CS101", found.getCourseCode());
    }

    @Test
    void getCourseById_WhenNotFound_ShouldThrowResourceNotFoundException() {
        when(courseRepository.findById(999)).thenReturn(Optional.empty());

        assertThrows(ResourceNotFoundException.class, () -> courseService.getCourseById(999));
    }

    @Test
    void addCourse_ShouldSaveAndReturnCourse() {
        when(courseRepository.save(any(Course.class))).thenReturn(testCourse);

        Course saved = courseService.addCourse(testCourse);

        assertNotNull(saved);
        assertEquals("Computer Science 101", saved.getCourseName());
        verify(courseRepository, times(1)).save(testCourse);
    }

    @Test
    void deleteCourse_WhenExists_ShouldDeleteCourse() {
        when(courseRepository.existsById(1)).thenReturn(true);
        doNothing().when(courseRepository).deleteById(1);

        assertDoesNotThrow(() -> courseService.deleteCourse(1));
        verify(courseRepository, times(1)).deleteById(1);
    }
}
