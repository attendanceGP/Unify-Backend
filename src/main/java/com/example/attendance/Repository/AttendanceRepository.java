package com.example.attendance.Repository;

import com.example.attendance.Models.*;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;

import java.util.Date;
import java.util.List;

public interface AttendanceRepository extends CrudRepository<Attendance, Long> {
   // List<Attendance> findByCourseAndUserGroupAndDate(Date date, String userGroup, Course course);

    @Query(value = "SELECT * FROM attendance WHERE date = :dateValue and user_group = :userGroup and fk_course_id = :courseId and fk_user_id IN (SELECT id FROM user WHERE dtype = 'TeachingAssistant');", nativeQuery = true)
    List<Attendance> findByCourseAndUserGroupAndDate(@Param("dateValue") Date date, @Param("userGroup") String userGroup, @Param("courseId") String courseId);


    @Query(value = "SELECT * FROM attendance WHERE fk_course_id = :courseId and fk_user_id = :userId and user_group = :group ;", nativeQuery = true)
    List<Attendance> findByUserIDAndCourseIDAndGroup(@Param("userId") Integer userId, @Param("courseId") String courseId, @Param("group") String Group);

    // Used for TA to check students attendees in a certain course and confirm.
    @Query(value = "SELECT * from attendance WHERE date = :dateValue and user_group = :userGroup and fk_course_id = :courseId and fk_user_id IN (SELECT id FROM user WHERE dtype = 'Student');", nativeQuery = true)
    List<Attendance> findStudentByCourseAndUserGroupAndDate(@Param("dateValue") Date date, @Param("userGroup") String userGroup, @Param("courseId") String courseId);


    List<Attendance> findAttendanceByUserAndCourse(Student student, Course course);
}
