package com.example.project.service;

import com.example.project.entity.Attendance;

import java.time.LocalDate;
import java.util.List;

public interface AttendanceService {

    List<Attendance> getAttendance(LocalDate date);

    List<Attendance> saveSession(List<Attendance> records);

    Attendance updateAttendance(int id, Attendance attendance);
}
