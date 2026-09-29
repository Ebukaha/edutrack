package com.example.project.Repository;

import com.example.project.Entity.Student;
import org.springframework.data.jpa.repository.JpaRepository;

@org.springframework.stereotype.Repository
public interface Repository extends JpaRepository<Student, Integer>  {
}
