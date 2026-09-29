package com.example.project.service;

import com.example.project.entity.Student;
import com.example.project.exception.ResourceNotFoundException;
import com.example.project.repository.CourseRepository;
import com.example.project.repository.StudentRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Collections;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class StudentServiceTest {

    @Mock
    private StudentRepository studentRepository;

    @Mock
    private CourseRepository courseRepository;

    @InjectMocks
    private StudentServiceImpl studentService;

    private Student testStudent;

    @BeforeEach
    void setUp() {
        testStudent = new Student();
        testStudent.setId(1);
        testStudent.setFirstName("Jane");
        testStudent.setLastName("Doe");
        testStudent.setEmail("jane.doe@example.com");
        testStudent.setStudentId("STU-1001");
        testStudent.setCourse("Computer Science");
        testStudent.setStatus("Active");
    }

    @Test
    void getStudents_ShouldReturnStudentList() {
        when(studentRepository.findAll()).thenReturn(List.of(testStudent));

        List<Student> students = studentService.getStudents();

        assertNotNull(students);
        assertEquals(1, students.size());
        assertEquals("Jane", students.get(0).getFirstName());
        verify(studentRepository, times(1)).findAll();
    }

    @Test
    void getStudentById_WhenFound_ShouldReturnStudent() {
        when(studentRepository.findById(1)).thenReturn(Optional.of(testStudent));

        Student found = studentService.getStudentById(1);

        assertNotNull(found);
        assertEquals(1, found.getId());
        assertEquals("STU-1001", found.getStudentId());
    }

    @Test
    void getStudentById_WhenNotFound_ShouldThrowResourceNotFoundException() {
        when(studentRepository.findById(999)).thenReturn(Optional.empty());

        assertThrows(ResourceNotFoundException.class, () -> studentService.getStudentById(999));
    }

    @Test
    void addStudent_ShouldSaveAndReturnStudent() {
        when(studentRepository.save(any(Student.class))).thenReturn(testStudent);
        when(courseRepository.findAll()).thenReturn(Collections.emptyList());

        Student saved = studentService.addStudent(testStudent);

        assertNotNull(saved);
        assertEquals("Jane", saved.getFirstName());
        verify(studentRepository, times(1)).save(testStudent);
    }

    @Test
    void deleteStudent_WhenFound_ShouldDeleteStudent() {
        when(studentRepository.findById(1)).thenReturn(Optional.of(testStudent));
        doNothing().when(studentRepository).deleteById(1);
        when(courseRepository.findAll()).thenReturn(Collections.emptyList());

        assertDoesNotThrow(() -> studentService.deleteStudent(1));
        verify(studentRepository, times(1)).deleteById(1);
    }
}
