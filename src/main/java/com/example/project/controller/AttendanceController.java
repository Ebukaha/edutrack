package com.example.project.controller;

import com.example.project.entity.Attendance;
import com.example.project.service.AttendanceService;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.util.List;

@CrossOrigin(origins = {
        "http://localhost:8080",
        "http://localhost:63342"
})
@RequiredArgsConstructor
@RestController
@RequestMapping("/Attendance")
public class AttendanceController {

    private static final Logger log = LoggerFactory.getLogger(AttendanceController.class);
    private final AttendanceService attendanceService;

    @GetMapping
    public List<Attendance> getAttendance(
            @RequestParam(required = false)
            @DateTimeFormat(iso = DateTimeFormat.ISO.DATE)
            LocalDate date) {
        return attendanceService.getAttendance(date);
    }

    @PostMapping
    public List<Attendance> saveAttendance(@RequestBody List<Attendance> records) {
        log.info("Saving {} attendance records", records == null ? 0 : records.size());
        return attendanceService.saveSession(records);
    }

    @PutMapping("/{id}")
    public Attendance updateAttendance(@PathVariable int id, @RequestBody Attendance attendance) {
        return attendanceService.updateAttendance(id, attendance);
    }
}
