package com.example.attendance.Service;

import com.example.attendance.Models.Course;
import com.example.attendance.Repository.CourseRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class CourseService {
    @Autowired
    private CourseRepository courseRepository;

    public void addCourse(Course course){
        courseRepository.save(course);
    }

    public Optional<Course> findById(String id){
        return courseRepository.findById(id);
    }
    public Course findByCourseName(String courseName){return courseRepository.getCourseByCourseName(courseName);}
}
