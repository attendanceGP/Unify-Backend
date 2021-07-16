package com.example.attendance.Course.Repository;

import com.example.attendance.Course.Model.Course;
import org.springframework.data.repository.CrudRepository;

public interface CourseRepository extends CrudRepository<Course, String> {
    Course getCourseByCourseName(String courseName);
}
