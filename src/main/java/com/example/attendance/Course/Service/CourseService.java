package com.example.attendance.Course.Service;

import com.example.attendance.Course.Model.Course;
import com.example.attendance.Course.Repository.CourseRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class CourseService {
    @Autowired
    private CourseRepository courseRepository;

    public Optional<Course> findById(String id){
        return courseRepository.findById(id);
    }
    public Course findByCourseName(String courseName){return courseRepository.getCourseByCourseName(courseName);}
}
