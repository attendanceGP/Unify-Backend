package com.example.attendance.Repository;

import com.example.attendance.Models.*;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;

import java.util.Date;
import java.util.List;

public interface StudentRepository extends CrudRepository<Student, Integer> {

    // Get student's List from attendance in certain date, group, and course
    // used in Attendance Service in "Get Student's List"
    @Query(value = "SELECT * from user WHERE dtype = 'Student' and id IN (SELECT fk_user_id from attendance WHERE date = :dateValue and user_group = :userGroup and fk_course_id = :courseId);", nativeQuery = true)
    List<Student> findStudentByCourseAndUserGroupAndDate(@Param("dateValue") Date date, @Param("userGroup") String userGroup, @Param("courseId") String courseId);

}
