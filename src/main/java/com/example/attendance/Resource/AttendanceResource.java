package com.example.attendance.Resource;

import com.example.attendance.Models.Attendance;
import com.example.attendance.Models.Course;
import com.example.attendance.Models.Student;
import com.example.attendance.Service.AttendanceService;
import com.example.attendance.Service.CourseService;
import com.example.attendance.Service.StudentService;
import org.json.JSONArray;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Optional;

@Controller
@RequestMapping(value = "/attendance")
public class AttendanceResource {
    @Autowired
    private StudentService studentService;

    @Autowired
    private CourseService courseService;

    @Autowired
    private AttendanceService attendanceService;

    //this function takes the student id and the course code as parameters
    //and return the attendance dates that the student attended this course
    @GetMapping(value = "/attendanceDates")
    @ResponseBody public String GetAttendanceDates(@RequestParam Integer studentId, @RequestParam String courseCode){
        Optional<Student>optionalStudent = studentService.findById(studentId);
        if (optionalStudent.isEmpty()) return "Error: student does not exist";
        Optional<Course>optionalCourse = courseService.findById(courseCode);
        if (optionalCourse.isEmpty()) return"Error: Course does not exist";
        Student student = optionalStudent.get();
        Course course = optionalCourse.get();
        List<Attendance> attendances = attendanceService.findByUserAndCourse(student,course);
        List<Date> attendancesDates = new ArrayList<>();
        for (int i = 0; i < attendances.size(); i++) {
            attendancesDates.add(attendances.get(i).getDate());
        }
        return attendancesDates.toString();
    }


    // this function shows the TA all students in a certain course, group, and date
    // to allow him confirming on total attended number, delete, or add students to attendance.
    @GetMapping(path = "/getStudentsAttendanceList")
    public @ResponseBody
    String getStudentsList(@RequestParam("courseID") String courseID, @RequestParam("group") String group,
                           @RequestParam("date") @DateTimeFormat(pattern = "dd-MM-yyyy") Date date){
        return attendanceService.getStudentsList(courseID,group,date).toString();
    }


    // this function is used by TAs and Professors to set students absent/present and add penalties to them.
    @GetMapping(path = "/setStudentAbsence")
    public @ResponseBody
    String setAbsence(@RequestParam("courseID") String courseID, @RequestParam("group") String group,
                             @RequestParam("date") @DateTimeFormat(pattern = "dd-MM-yyyy") Date date,
                             @RequestParam("studentID") Integer studentID, @RequestParam("absent") boolean absent, @RequestParam(defaultValue = "false") boolean penalty){
        return attendanceService.setStudentsAbsence(courseID, group, date, studentID, absent, penalty);
    }


    // TA confirms students attendance list
    @PostMapping(path = "/confirmAttendance")
    public @ResponseBody
    void confirmAttendance(@RequestParam("courseID") String courseID, @RequestParam("group") String group,
                      @RequestParam("date") @DateTimeFormat(pattern = "dd-MM-yyyy") Date date){
        attendanceService.confirmAttendance(courseID, group, date);
    }

}
