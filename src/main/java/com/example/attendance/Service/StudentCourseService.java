package com.example.attendance.Service;

import com.example.attendance.Models.Course;
import com.example.attendance.Models.User;
import com.example.attendance.Models.UserCourse;
import com.example.attendance.Repository.StudentCourseRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class StudentCourseService {
    @Autowired
    private StudentCourseRepository studentCourseRepository;

    public void addStudentCourse(UserCourse userCourse){
        studentCourseRepository.save(userCourse);
    }
    public List<UserCourse> getUserCourses(User user){
        return studentCourseRepository.findUserCourseByUser(user);
    }
    public UserCourse getUserCourse(User user, Course course){return studentCourseRepository.findUserCourseByUserAndCourse(user,course);}
}
