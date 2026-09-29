package com.example.project.Service;

import com.example.project.Entity.Attendance;
import com.example.project.Repository.AttendanceRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.util.List;

@Service
@RequiredArgsConstructor
public class AttendanceServiceImpl implements AttendanceService {

    @Autowired
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
            throw new RuntimeException("No attendance records to save");
        }

        Attendance first = records.get(0);
        if (first.getDate() == null || first.getCourseCode() == null || first.getCourseCode().isBlank()) {
            throw new RuntimeException("Date and course are required");
        }

        repository.deleteByDateAndCourseCode(first.getDate(), first.getCourseCode());
        return repository.saveAll(records);
    }

    @Override
    public Attendance updateAttendance(int id, Attendance attendance) {
        Attendance existing = repository.findById(id)
                .orElseThrow(() -> new RuntimeException("Attendance record not found"));

        existing.setStatus(attendance.getStatus());
        existing.setStartTime(attendance.getStartTime());
        existing.setEndTime(attendance.getEndTime());
        existing.setCourseCode(attendance.getCourseCode());
        existing.setCourseName(attendance.getCourseName());
        existing.setDate(attendance.getDate());

        return repository.save(existing);
    }
}
