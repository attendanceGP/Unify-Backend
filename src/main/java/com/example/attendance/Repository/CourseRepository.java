package com.example.attendance.Repository;

import com.example.attendance.Models.Course;
import org.springframework.data.repository.CrudRepository;

import java.util.Optional;

public interface CourseRepository extends CrudRepository<Course, String> {
    Course getCourseByCourseName(String courseName);
}
