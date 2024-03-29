package com.example.attendance.User.Resource;

import com.example.attendance.Attendance.Model.Attendance;
import com.example.attendance.Attendance.Service.AttendanceService;
import com.example.attendance.Course.Model.Course;

import com.example.attendance.User.Repository.StudentRepository;
import com.example.attendance.Course.Service.CourseService;
import com.example.attendance.Course.Service.UserCourseService;
import com.example.attendance.User.Service.StudentService;
import com.example.attendance.User.Models.Student;
import com.example.attendance.User.Models.TeachingAssistant;
import com.example.attendance.User.Models.User;
import com.example.attendance.User.Service.TeachingAssistantService;
import com.example.attendance.Course.Model.UserCourse;
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
    private AttendanceService attendanceService;

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
    // checks if there is any active attendance that i should attend by checking my courses,group and current date
    @GetMapping(value = "/checkAttendance")
    public @ResponseBody String checkAttendance(@RequestParam Integer studentID, @RequestParam String[] courses ,
                                       @RequestParam("date") @DateTimeFormat(pattern = "dd-MM-yyyy") Date date){
        Student student = studentService.findById(studentID).get();
        List<Attendance>attendances=null;
        for (int i = 0; i <courses.length ; i++) {
            // get the user's data
            Course course = courseService.findByCourseName(courses[i]);
            UserCourse userCourse = userCourseService.getUserCourse(student,course);
            String userGroup=userCourse.getUserGroup();
            // find the ta's row that indicates an active attendance
            attendances = attendanceService.findAttendanceByCourseAndUserGroupAndDateAndAbsent(course,userGroup,date,false);
            for (int j = 0; j < attendances.size(); j++) {
                User user = attendances.get(j).getUser();
                if (user instanceof TeachingAssistant) {
                    JSONObject jsonObject = attendanceService.getJsonFromAttendance(attendances.get(j));
                    return jsonObject.toString();
                }
            }
            }
        return attendanceService.getJsonFromAttendance(new Attendance()).toString();
    }
    // record the attendance of the student (change the boolean from 1 to 0) and if already recorded nothing will happen

    @GetMapping(value = "/attend")
    public @ResponseBody String attend(@RequestParam Integer studentID, @RequestParam String courseName ,
                                       @RequestParam("date") @DateTimeFormat(pattern = "dd-MM-yyyy") Date date,@RequestParam long TAAttendanceID){
        Attendance attendance = attendanceService.getByID(TAAttendanceID).get();
        if (attendance.isAbsent()){
            return "the recording process is over";
        }
        // get the student data
        Student student = studentService.findById(studentID).get();
        Course course = courseService.findByCourseName(courseName);
        UserCourse userCourse = userCourseService.getUserCourse(student,course);
        String userGroup=userCourse.getUserGroup();
        // get the absent student that should attend
        List<Attendance> attendancesAbsent = attendanceService.findAttendanceByUserAndCourseAndUserGroupAndDateAndAbsent(student,course,userGroup,date,true);
        for (int i = 0; i <attendancesAbsent.size() ; i++) {
            // check if the student's row id > ta's row id in the database
            if (attendancesAbsent.get(i).getId()>attendance.getId()){
               long attendanceID=attendancesAbsent.get(i).getId();
                Attendance toBeAdded = new Attendance(attendanceID,student,course,userGroup,date,false);
                attendanceService.addAttendance(toBeAdded);
                return "attendance recorded successfully";
            }
        }

        return "recording failed";
    }
}
