package com.example.attendance.Repository;

import com.example.attendance.Models.Attendance;
import com.example.attendance.Models.UserCourse;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;

import java.util.Date;
import java.util.List;

public interface StudentCourseRepository extends CrudRepository<UserCourse, Integer> {
    //List<UserCourse> findByUserID


    List<UserCourse> findByFk_user_idAndFk_course_id(Integer fk_user_id, String fk_course_id);
}
