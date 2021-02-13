package com.example.attendance.Service;

import com.example.attendance.Models.*;
import com.example.attendance.Repository.AttendanceRepository;
import com.example.attendance.Repository.StudentRepository;
import org.json.JSONArray;
import org.json.JSONObject;
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
    public JSONArray getStudentsList(String courseID, String Group, Date date){
        return getJsonFromAttendance(attendanceRepository.findStudentByCourseAndUserGroupAndDate(date, Group, courseID));
    }

    private JSONArray getJsonFromAttendance(List<Attendance> attendanceList){
        JSONArray jsonArray = new JSONArray();

        for(Attendance i: attendanceList){
            JSONObject jsonObject = new JSONObject();

            jsonObject.put("userId", i.getUser().getId());
            jsonObject.put("courseCode", i.getCourse().getCourseCode());
            jsonObject.put("name", i.getUser().getName());
            jsonObject.put("userGroup", i.getUserGroup());

            jsonArray.put(jsonObject);
        }

        return jsonArray;
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
