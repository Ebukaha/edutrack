package com.example.project.controller;

import com.example.project.entity.Student;
import com.example.project.exception.GlobalExceptionHandler;
import com.example.project.exception.ResourceNotFoundException;
import com.example.project.service.StudentService;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.context.annotation.Import;
import org.springframework.http.MediaType;
import org.springframework.test.context.bean.override.mockito.MockitoBean;
import org.springframework.test.web.servlet.MockMvc;

import java.util.List;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@WebMvcTest(StudentController.class)
@AutoConfigureMockMvc(addFilters = false)
@Import(GlobalExceptionHandler.class)
class StudentControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockitoBean
    private StudentService studentService;

    @Autowired
    private ObjectMapper objectMapper;

    private Student testStudent;

    @BeforeEach
    void setUp() {
        testStudent = new Student();
        testStudent.setId(1);
        testStudent.setFirstName("Alice");
        testStudent.setLastName("Smith");
        testStudent.setEmail("alice@example.com");
        testStudent.setStudentId("STU-2002");
        testStudent.setCourse("Business Admin");
        testStudent.setStatus("Active");
    }

    @Test
    void getAllStudents_ShouldReturnOkAndList() throws Exception {
        when(studentService.getStudents()).thenReturn(List.of(testStudent));

        mockMvc.perform(get("/Students"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$[0].firstName").value("Alice"))
                .andExpect(jsonPath("$[0].studentId").value("STU-2002"));
    }

    @Test
    void getStudentById_WhenExists_ShouldReturnStudent() throws Exception {
        when(studentService.getStudentById(1)).thenReturn(testStudent);

        mockMvc.perform(get("/Students/1"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.email").value("alice@example.com"));
    }

    @Test
    void getStudentById_WhenNotFound_ShouldReturn404() throws Exception {
        when(studentService.getStudentById(99)).thenThrow(new ResourceNotFoundException("Student", "id", 99));

        mockMvc.perform(get("/Students/99"))
                .andExpect(status().isNotFound())
                .andExpect(jsonPath("$.status").value(404))
                .andExpect(jsonPath("$.error").value("Not Found"));
    }

    @Test
    void addStudent_ShouldReturnCreatedStudent() throws Exception {
        when(studentService.addStudent(any(Student.class))).thenReturn(testStudent);

        mockMvc.perform(post("/Students")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(testStudent)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.firstName").value("Alice"));
    }

    @Test
    void deleteStudent_ShouldReturnOk() throws Exception {
        doNothing().when(studentService).deleteStudent(1);

        mockMvc.perform(delete("/Students/1"))
                .andExpect(status().isOk());
    }
}
