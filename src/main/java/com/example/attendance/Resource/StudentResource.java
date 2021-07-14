package com.example.attendance.Resource;

import com.example.attendance.Models.*;

import com.example.attendance.Repository.StudentRepository;
import com.example.attendance.Service.CourseService;
import com.example.attendance.Service.UserCourseService;
import com.example.attendance.Service.StudentService;
import com.example.attendance.Service.*;
import org.json.JSONObject;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.util.Date;
import java.util.List;
import java.util.Optional;

@Controller
@RequestMapping(path = "/student")
public class StudentResource {
    @Autowired
    private StudentService studentService;

    @Autowired
    private CourseService courseService;

    @Autowired
    private UserCourseService userCourseService;

    @Autowired
    private StudentRepository studentRepository;


    @Autowired
    private AttendanceService attendanceService;

    @Autowired
    private TeachingAssistantService teachingAssistantService;

    @GetMapping(value = "/getStudentCourses")
    public @ResponseBody String[]getStudentCourses(@RequestParam Integer studentID){
    Optional<Student> student = studentService.findById(studentID);
    if (student.isEmpty()) return new String[0];
    List<UserCourse> studentCourses = userCourseService.getUserCourses(student.get());
    if (studentCourses.isEmpty()) return new String[0];
    String [] courses = new String[studentCourses.size()];
        for (int i = 0; i <studentCourses.size() ; i++) {
            courses[i] = studentCourses.get(i).getCourse().getCourseName();
        }
        return courses;
    }

    @GetMapping(value = "/checkAttendance")
    public @ResponseBody String checkAttendance(@RequestParam Integer studentID, @RequestParam String[] courses ,
                                       @RequestParam("date") @DateTimeFormat(pattern = "dd-MM-yyyy") Date date){
        Student student = studentService.findById(studentID).get();
        List<Attendance>attendances=null;
        for (int i = 0; i <courses.length ; i++) {
            Course course = courseService.findByCourseName(courses[i]);
            UserCourse userCourse = userCourseService.getUserCourse(student,course);
            String userGroup=userCourse.getUserGroup();
            attendances = attendanceService.findAttendanceByCourseAndUserGroupAndDateAndAbsent(course,userGroup,date,false);
            Optional<TeachingAssistant> optionalTa = null;
           // int index=0;
            for (int j = 0; j < attendances.size(); j++) {

                User user = attendances.get(j).getUser();
                int id = user.getId();
                optionalTa = teachingAssistantService.findTAById(id);
                if (!optionalTa.isEmpty()) {
                    JSONObject jsonObject = attendanceService.getJsonFromAttendance(attendances.get(j));
                    return jsonObject.toString();

                }
            }
            }
        return attendanceService.getJsonFromAttendance(new Attendance()).toString();
    }

    @GetMapping(value = "/attend")
    public @ResponseBody String attend(@RequestParam Integer studentID, @RequestParam String courseName ,
                                       @RequestParam("date") @DateTimeFormat(pattern = "dd-MM-yyyy") Date date,@RequestParam long TAAttendanceID){
        Attendance attendance = attendanceService.getByID(TAAttendanceID).get();
        if (attendance.isAbsent()){
            return "the recording process is over";
        }
        Student student = studentService.findById(studentID).get();
        Course course = courseService.findByCourseName(courseName);
        UserCourse userCourse = userCourseService.getUserCourse(student,course);
        String userGroup=userCourse.getUserGroup();
        List<Attendance> attendancesAbsent = attendanceService.findAttendanceByCourseAndUserGroupAndDateAndAbsent(course,userGroup,date,true);
        long attendanceID=0;
        for (int i = 0; i <attendancesAbsent.size() ; i++) {
            User user = attendancesAbsent.get(i).getUser();
            int id = user.getId();
            if (id == studentID && attendancesAbsent.get(i).getId()>attendance.getId()){
                attendanceID=attendancesAbsent.get(i).getId();
                break;
            }
        }
        Attendance toBeAdded = new Attendance(attendanceID,student,course,userGroup,date,false);
        attendanceService.addAttendance(toBeAdded);
        return "attendance recorded successfully";
    }
}
