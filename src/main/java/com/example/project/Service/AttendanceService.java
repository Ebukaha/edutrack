package com.example.project.Service;

import com.example.project.Entity.Attendance;

import java.time.LocalDate;
import java.util.List;

public interface AttendanceService {

    List<Attendance> getAttendance(LocalDate date);

    List<Attendance> saveSession(List<Attendance> records);

    Attendance updateAttendance(int id, Attendance attendance);
}
