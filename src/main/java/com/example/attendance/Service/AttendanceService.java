package com.example.attendance.Service;

import com.example.attendance.Models.*;
import com.example.attendance.Repository.AttendanceRepository;
import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;
import java.util.Optional;

@Service
public class AttendanceService {
    @Autowired
    private AttendanceRepository attendanceRepository;

    public List<Attendance>findByUserAndCourse(Student student, Course course){
        return attendanceRepository.findAttendanceByUserAndCourse(student,course);
    }
    public List<Attendance>findAttendanceByCourseAndUserGroupAndDateAndAbsent(Course course ,String userGroup, Date date,boolean absent){
        return attendanceRepository.findAttendanceByCourseAndUserGroupAndDateAndAbsent(course,userGroup,date,absent);
    }
    public void addAttendance(Attendance attendance){attendanceRepository.save(attendance);}
    public Optional<Attendance> getByID(long ID){return attendanceRepository.findById(ID);}

    public JSONObject getJsonFromAttendance(Attendance attendance){
        JSONObject json = new JSONObject();

        json.put("id",attendance.getId());
        json.put("userID",attendance.getUser().getId());
        json.put("courseName",attendance.getCourse().getCourseName());
        json.put("userGroup",attendance.getUserGroup());
        //json.put("date",attendance.getDate());
        json.put("absent",attendance.isAbsent());

        return json;
    }
}
