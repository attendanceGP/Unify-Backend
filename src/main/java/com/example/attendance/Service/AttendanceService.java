package com.example.attendance.Service;

import com.example.attendance.Models.Attendance;
import com.example.attendance.Models.Course;
import com.example.attendance.Models.Student;
import com.example.attendance.Repository.AttendanceRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;

@Service
public class AttendanceService {
    @Autowired
    private AttendanceRepository attendanceRepository;

    public List<Attendance>findByUserAndCourse(Student student, Course course){
        return attendanceRepository.findAttendanceByUserAndCourse(student,course);
    }
    public List<Attendance>findAttendanceByCourseAndUserGroupAndDate(Course course ,String userGroup, Date date){
        return attendanceRepository.findAttendanceByCourseAndUserGroupAndDate(course,userGroup,date);
    }
    public void addAttendance(Attendance attendance){attendanceRepository.save(attendance);}
}
