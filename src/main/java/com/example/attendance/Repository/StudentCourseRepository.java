package com.example.attendance.Repository;

import com.example.attendance.User.Model.UserCourse;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface StudentCourseRepository extends CrudRepository<UserCourse, Integer> {

    //a query to get the userCourse record of a student that correspond to his specific id and the course he is registered in
    //will be useful in getting absences to show for certain courses and we may make another query in another sprint
    //to get only records that correspond to this student's id to get all of his absence and attendance count from all courses
    //and show which group they belong to in each course
    @Query(value = "SELECT * FROM user_course WHERE fk_user_id = :userId and fk_course_code = :courseId", nativeQuery = true)
    List<UserCourse> findUserCourseByCourseAndAndUser(@Param("userId") Integer userId, @Param("courseId") String courseId);

    @Query(value = "SELECT * FROM user_course WHERE fk_course_code = :courseId and fk_user_id = :userId", nativeQuery = true)
    List<UserCourse> findByStudentIDAndCourseID(@Param("userId") Integer userId, @Param("courseId") String courseId);
}
