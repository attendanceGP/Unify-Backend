package com.example.attendance.Resource;

import com.example.attendance.Models.Course;
import com.example.attendance.Models.Student;
import com.example.attendance.Models.UserCourse;
import com.example.attendance.Service.CourseService;
import com.example.attendance.Service.StudentCourseService;
import com.example.attendance.Service.StudentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

@Controller
@RequestMapping(path = "/student")
public class StudentResource {
    @Autowired
    private StudentService studentService;

    @Autowired
    private CourseService courseService;

    @Autowired
    private StudentCourseService studentCourseService;

    @PostMapping(path = "/add") // Map ONLY POST Requests
    public @ResponseBody void add()
    {
        Student student = new Student(20170171, "Ali Samy", 4, 4.0);

        Course course = new Course("CS213", "CS");

        UserCourse userCourse = new UserCourse(student, course, "G1");

        studentService.addStudent(student);
        courseService.addCourse(course);
        studentCourseService.addStudentCourse(userCourse);
    }
}
