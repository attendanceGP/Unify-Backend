package com.example.attendance.User.Resource;

import com.example.attendance.Containers.StudentCourseContainer;
import com.example.attendance.Models.Course;
import com.example.attendance.Models.Student;
import com.example.attendance.Models.User;
import com.example.attendance.Models.UserCourse;
import com.example.attendance.Service.CourseService;
import com.example.attendance.Service.StudentCourseService;
import com.example.attendance.Service.StudentService;
import com.example.attendance.User.Service.UserService;
import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.util.Optional;

@Controller
@RequestMapping(path = "/user")
public class UserResource {
    @Autowired
    private StudentService studentService;

    @Autowired
    private CourseService courseService;

    @Autowired
    private StudentCourseService studentCourseService;

    @Autowired
    private UserService userService;

    @GetMapping(path = "login")
    public @ResponseBody
    String login(@RequestParam String username, @RequestParam String password){
        /*
        1- we contact the uni server with the username and password
        2- if user is already logged in, we inform the user
        3- if the user is not logged in, we add the courses of the user to UserCourse
        4- if the user is valid we return a json with the user info requeired by the app
         */

        // contact uni server
        return userService.login(username, password);
    }
}
