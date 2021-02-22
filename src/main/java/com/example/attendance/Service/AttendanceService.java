package com.example.attendance.Service;

import com.example.attendance.Models.*;
import com.example.attendance.Repository.AttendanceRepository;
import com.example.attendance.Repository.StudentRepository;
import org.json.JSONArray;
import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;

import java.util.ArrayList;
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

    public List<Attendance>findAttendanceByCourseAndUserGroupAndDateAndAbsent(Course course ,String userGroup, Date date,boolean absent){
        return attendanceRepository.findAttendanceByCourseAndUserGroupAndDateAndAbsent(course,userGroup,date,absent);
    }

    public void addAttendance(Attendance attendance){attendanceRepository.save(attendance);}
    public Optional<Attendance> getByID(long ID){return attendanceRepository.findById(ID);}

    public JSONObject getJsonFromAttendance(Attendance attendance) {
        JSONObject json = new JSONObject();

        json.put("id", attendance.getId());
        json.put("userID", attendance.getUser().getId());
        json.put("courseName", attendance.getCourse().getCourseName());
        json.put("userGroup", attendance.getUserGroup());
        //json.put("date",attendance.getDate());
        json.put("absent", attendance.isAbsent());

        return json;
    }

    // TA will be able to see all Students List in this course in a certain date.
    // this list will be used to enable TA to delete, add student for attendance.
    public JSONArray getStudentsList(String courseID, String Group, Date date){
        String[] groups = Group.split(" ");
        List<Object> returnArray = new ArrayList<>();
        for(String g: groups){
            List<Attendance> attendanceList = attendanceRepository.findStudentByCourseAndUserGroupAndDate(date, g, courseID);
            System.out.println(g);
            if(!attendanceList.isEmpty()) {
                returnArray.addAll(getJsonFromAttendance(attendanceList).toList());
                System.out.println(returnArray.size());
            }
        }
        System.out.println(returnArray.size());
        return new JSONArray(returnArray);
    }

    private JSONArray getJsonFromAttendance(List<Attendance> attendanceList){
        JSONArray jsonArray = new JSONArray();

        for(Attendance i: attendanceList){
            JSONObject jsonObject = new JSONObject();

            jsonObject.put("userID", i.getUser().getId());
            jsonObject.put("courseCode", i.getCourse().getCourseCode());
            jsonObject.put("name", i.getUser().getName());
            jsonObject.put("userGroup", i.getUserGroup());

            jsonArray.put(jsonObject);
        }

        return jsonArray;
    }

    public String setStudentsAbsence(String courseID, String Group, Date date, Integer studentID, boolean absence){
        String[] groups = Group.split(" ");
        List<Attendance> student = new ArrayList<>();
        for(String g: groups){
            student = attendanceRepository.findStudentByCourseAndUserGroupAndDateandID(date, g, courseID, studentID);
            if(!student.isEmpty()) break;
        }
        if(student.isEmpty()) return "{\"error_code\":2}"; //user not found
        Attendance i = student.get(0);
        if(i.getAbsent() == absence) return "{\"error_code\":4}"; //user is already absent

        attendanceRepository.UpdateStudentAbsence(date, i.getUserGroup(), courseID, studentID, absence);

        JSONObject jsonObject = new JSONObject();
        jsonObject.put("userID", i.getUser().getId());
        jsonObject.put("courseCode", i.getCourse().getCourseCode());
        jsonObject.put("name", i.getUser().getName());
        jsonObject.put("userGroup", i.getUserGroup());
        return jsonObject.toString();
    }
}
