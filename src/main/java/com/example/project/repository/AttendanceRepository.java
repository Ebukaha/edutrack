package com.example.project.repository;

import com.example.project.entity.Attendance;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.util.List;

@Repository
public interface AttendanceRepository extends JpaRepository<Attendance, Integer> {

    List<Attendance> findByDate(LocalDate date);

    List<Attendance> findByDateAndCourseCode(LocalDate date, String courseCode);

    void deleteByDateAndCourseCode(LocalDate date, String courseCode);
}
