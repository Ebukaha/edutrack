package com.example.project.service;

import com.example.project.entity.Attendance;
import com.example.project.exception.ResourceNotFoundException;
import com.example.project.repository.AttendanceRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.util.List;

@Service
@RequiredArgsConstructor
public class AttendanceServiceImpl implements AttendanceService {

    private final AttendanceRepository repository;

    @Override
    public List<Attendance> getAttendance(LocalDate date) {
        if (date == null) {
            return repository.findAll();
        }
        return repository.findByDate(date);
    }

    @Override
    @Transactional
    public List<Attendance> saveSession(List<Attendance> records) {
        if (records == null || records.isEmpty()) {
            throw new IllegalArgumentException("No attendance records to save");
        }

        Attendance first = records.get(0);
        if (first.getDate() == null || first.getCourseCode() == null || first.getCourseCode().isBlank()) {
            throw new IllegalArgumentException("Date and course are required");
        }

        repository.deleteByDateAndCourseCode(first.getDate(), first.getCourseCode());
        return repository.saveAll(records);
    }

    @Override
    public Attendance updateAttendance(int id, Attendance attendance) {
        Attendance existing = repository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Attendance", "id", id));

        existing.setStatus(attendance.getStatus());
        existing.setStartTime(attendance.getStartTime());
        existing.setEndTime(attendance.getEndTime());
        existing.setCourseCode(attendance.getCourseCode());
        existing.setCourseName(attendance.getCourseName());
        existing.setDate(attendance.getDate());

        return repository.save(existing);
    }
}
