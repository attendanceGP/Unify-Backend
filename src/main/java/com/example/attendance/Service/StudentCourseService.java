package com.example.attendance.Service;

import com.example.attendance.Models.UserCourse;
import com.example.attendance.Repository.UserCourseRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class StudentCourseService {
    @Autowired
    private UserCourseRepository userCourseRepository;

    public void addStudentCourse(UserCourse userCourse){
        userCourseRepository.save(userCourse);
    }
}
