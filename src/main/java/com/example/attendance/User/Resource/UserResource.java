package com.example.attendance.User.Resource;

import com.example.attendance.Course.Service.CourseService;
import com.example.attendance.Course.Service.UserCourseService;
import com.example.attendance.User.Service.StudentService;
import com.example.attendance.User.Service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

@Controller
@RequestMapping(path = "/user")
public class UserResource {
    @Autowired
    private StudentService studentService;

    @Autowired
    private CourseService courseService;

    @Autowired
    private UserCourseService userCourseService;

    @Autowired
    private UserService userService;

    @GetMapping(path = "login")
    public @ResponseBody
    String login(@RequestParam String username, @RequestParam String password){
        return userService.login(username, password);
    }

    @GetMapping(path = "gettoken")
    public @ResponseBody
    String getToken(@RequestParam Integer id){
        return userService.getToken(id);
    }
}
