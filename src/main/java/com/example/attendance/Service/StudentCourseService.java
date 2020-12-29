package com.example.attendance.Service;

import com.example.attendance.User.Model.UserCourse;
import com.example.attendance.Repository.StudentCourseRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class StudentCourseService {
    @Autowired
    private StudentCourseRepository studentCourseRepository;

    public void addStudentCourse(UserCourse userCourse){
        studentCourseRepository.save(userCourse);
    }
}
