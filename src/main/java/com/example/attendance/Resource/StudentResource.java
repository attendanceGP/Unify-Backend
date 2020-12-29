package com.example.attendance.Resource;

import com.example.attendance.User.Containers.StudentCourseContainer;
import com.example.attendance.Models.*;
import com.example.attendance.Service.CourseService;
import com.example.attendance.Service.StudentCourseService;
import com.example.attendance.Service.StudentService;
import com.example.attendance.User.Model.Student;
import com.example.attendance.User.Model.UserCourse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.Date;
import java.util.Optional;

@Controller
@RequestMapping(path = "/student")
public class StudentResource {
    @Autowired
    private StudentService studentService;

    @Autowired
    private CourseService courseService;

    @Autowired
    private StudentCourseService studentCourseService;

    @PostMapping( value = "/add", consumes = "application/json")
    public @ResponseBody void add(@Valid @RequestBody Student student){
        studentService.addStudent(student);
    }

    @PostMapping(path = "/test") // NOT ACTUAL FUNCTION
                                // DO NOT COPY INTO OTHER CLASSES
    public @ResponseBody void test()
    {
//        Student student = new Student(20170171, "Ali Samy", 4, 4.0);
//
//        Course course = new Course("CS213", "CS");
//
//        UserCourse userCourse = new UserCourse(student, course, "G1");
//
//        studentService.addStudent(student);
//        courseService.addCourse(course);
//        studentCourseService.addStudentCourse(userCourse);

//        StudentCourseContainer studentCourseContainer = studentService.getStudentCourses("x", "x");
//        System.out.println(studentCourseContainer.getStudent().getGpa());
//        System.out.println(studentCourseContainer.getCourseList()[1].getCourseCode());

        Student student = new Student(20170171, "Ali Samy", 4, 4.0);
        studentService.addStudent(student);

        Student student2 = new Student(20170399, "Lamya Raed", 4, 4.0);
        studentService.addStudent(student2);

        Course course = new Course("CS464", "Genetic Algorithms");
        courseService.addCourse(course);

        UserCourse userCourse = new UserCourse(student2, course, "G2");
        studentCourseService.addStudentCourse(userCourse);
        course = new Course("CS467", "Machine Learning");
        courseService.addCourse(course);

        userCourse = new UserCourse(student, course, "G1");
        studentCourseService.addStudentCourse(userCourse);


        System.out.println("done");
    }

    @PostMapping(path = "login")
    public @ResponseBody String login(@RequestParam String username, @RequestParam String password){
        StudentCourseContainer studentCourseContainer = studentService.getStudentCourses(username, password);

        if(studentCourseContainer == null) return "An error has occurred.";

        Optional<Student> studentOptional = studentService.findById(studentCourseContainer.getStudent().getId());

        if(studentOptional.isPresent())  return "User already exists.";

        for(int i=0; i<studentCourseContainer.getCourseList().length; i++) {
            Optional<Course> courseOptional =
                    courseService.findById(studentCourseContainer.getCourseList()[i].getCourseCode());
            if (courseOptional.isEmpty()) {
                return "Course id: " + studentCourseContainer.getCourseList()[i].getCourseCode() + " is not found.";
            }
        }

        studentService.addStudent(studentCourseContainer.getStudent());

        for(int i=0; i<studentCourseContainer.getCourseList().length; i++){
            Optional<Course> courseOptional =
                    courseService.findById(studentCourseContainer.getCourseList()[i].getCourseCode());

            UserCourse userCourse =
                    new UserCourse(studentCourseContainer.getStudent(), courseOptional.get(),
                            studentCourseContainer.getCourseList()[i].getGroup(), 0, 0);

            studentCourseService.addStudentCourse(userCourse);
        }

        return "Success.";
    }

    @PostMapping( path = "postattendance")
    public @ResponseBody String postAttendance(@RequestParam("date") @DateTimeFormat(pattern = "dd-MM-yyyy") Date date,
                                               @RequestParam String userGroup, @RequestParam String courseId, @RequestParam Integer userId){
        return studentService.postAttendance(date, userGroup, courseId, userId);
    }

}
