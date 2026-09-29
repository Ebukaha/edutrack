package com.example.project.Controller;

import com.example.project.Entity.Student;
import com.example.project.Service.StudentServiceImpl;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
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
public class StudentController {

    @Autowired
    public StudentServiceImpl studentService;

    private static final Logger log = LoggerFactory.getLogger(StudentController.class);

    @GetMapping("/Students")
    public List<Student> getAllStudents( ){

        return studentService.GetStudents();
    }
    @PostMapping("/Students")
    public Student AddStudent(@RequestBody Student students){

        log.info("Adding student {}", students);

        return studentService.AddStudent(students);

    }
    @GetMapping("/Students/{id}")
    public Student getStudentById(@PathVariable int id ){

        return studentService.GetstudentbyId(id);

    }
    @PutMapping("/Students/{id}")
    public Student updateStudent(@PathVariable int id,@RequestBody Student student) {

        return studentService.UpdateStudent(id, student);
    }
    @DeleteMapping("/Students/{id}")
    public ResponseEntity<?> deleteStudent(@PathVariable int id){
        studentService.deleteS(id);
        return new ResponseEntity<>(HttpStatus.OK);
    }
}
