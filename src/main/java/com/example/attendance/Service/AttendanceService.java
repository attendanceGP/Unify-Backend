package com.example.attendance.Service;

import com.example.attendance.Models.Attendance;
import com.example.attendance.Models.Course;
import com.example.attendance.Models.Student;
import com.example.attendance.Models.User;
import com.example.attendance.Repository.AttendanceRepository;
import com.example.attendance.Repository.StudentRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Date;
import java.util.Optional;

@Service
public class AttendanceService {
    @Autowired
    private AttendanceRepository attendanceRepository;
    @Autowired
    private StudentRepository studentRepository;

    public List<Attendance>findByUserAndCourse(Student student, Course course){
        return attendanceRepository.findAttendanceByUserAndCourse(student,course);
    }

    // TA will be able to see all Students List in this course in a certain date.
    // this list will be used to enable TA to delete, add student for attendance.
    public List<Attendance> getStudentsList(String courseID, String Group, Date date){
        return attendanceRepository.findStudentByCourseAndUserGroupAndDate(date, Group, courseID);
    }

    public String setAbsent(String courseID, String Group, Date date, Integer studentID){
        List<Attendance> student = attendanceRepository.findStudentByCourseAndUserGroupAndDateandID(date, Group, courseID, studentID);
        if(student.isEmpty()) return "No such ID";
        if(student.get(0).getAbsent()){
            return "Student is already absent";
        }
        attendanceRepository.UpdateStudentAbsence(date, Group, courseID, studentID, true);
        return "Done";
    }

    public String setPresent(String courseID, String Group, Date date, Integer studentID){
        List<Attendance> student = attendanceRepository.findStudentByCourseAndUserGroupAndDateandID(date, Group, courseID, studentID);
        if(student.isEmpty()) return "No such ID";
        if(!student.get(0).getAbsent()){
            return "Student is already present";
        }
        attendanceRepository.UpdateStudentAbsence(date, Group, courseID, studentID, false);
        return "Done";
    }

}
