package com.example.attendance.Repository;

import com.example.attendance.Models.UserCourse;
import org.springframework.data.repository.CrudRepository;

public interface StudentCourseRepository extends CrudRepository<UserCourse, Integer> {
}
