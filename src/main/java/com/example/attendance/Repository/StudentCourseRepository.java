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

    @Query(value = "SELECT * FROM user_course WHERE fk_course_code = :courseId and fk_user_id = :userId and user_group = :group ;", nativeQuery = true)
    List<UserCourse> findByUserIDAndCourseIDAndGroup(@Param("userId") Integer userId, @Param("courseId") String courseId, @Param("group") String Group);

    @Query(value = "SELECT * FROM user_course WHERE fk_course_code = :courseId and fk_user_id = :userId;", nativeQuery = true)
    List<UserCourse> findByStudentIDAndCourseID(@Param("userId") Integer userId, @Param("courseId") String courseId);
}
