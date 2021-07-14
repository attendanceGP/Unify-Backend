package com.example.attendance.Repository;

import com.example.attendance.Models.Attendance;
import com.example.attendance.Models.Course;
import com.example.attendance.Models.User;
import com.example.attendance.Models.UserCourse;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.transaction.annotation.Transactional;

import java.util.Date;
import java.util.List;

public interface UserCourseRepository extends CrudRepository<UserCourse, Integer> {

    //a query to get the userCourse record of a student that correspond to his specific id and the course he is registered in
    //will be useful in getting absences to show for certain courses and we may make another query in another sprint
    //to get only records that correspond to this student's id to get all of his absence and attendance count from all courses
    //and show which group they belong to in each course
    @Query(value = "SELECT * FROM user_course WHERE fk_user_id = :userId and fk_course_code = :courseId", nativeQuery = true)
    List<UserCourse> findUserCourseByCourseAndAndUser(@Param("userId") Integer userId, @Param("courseId") String courseId);

    //return a list of userCourses from the database that correspond to the given course id and userGroup
    @Query(value = "SELECT * FROM user_course WHERE fk_course_code = :courseId and user_group = :userGroup", nativeQuery = true)
    List<UserCourse> findByCourseIdAndUserGroup(@Param("courseId") String courseId, @Param("userGroup") String userGroup);

    //returns a list of all course codes from the userCourse table where someone is registered by providing their id
    @Query(value = "SELECT fk_course_code FROM user_course WHERE fk_user_id = :userId", nativeQuery = true)
    List<String> findCourseCodeById(@Param("userId") Integer userId);

    // Used Update student's absenceCount, attendanceCount, and penaltyCount by ID in certain course, group.
    @Modifying @Transactional
    @Query(value = "UPDATE user_course SET attendance_count = :attend, absence_count = :absent, penalty_count =:att_penalty WHERE user_group = :userGroup and fk_course_code = :courseId and fk_user_id = :userID ;", nativeQuery = true)
    void UpdateStudentCounts(@Param("userGroup") String userGroup, @Param("courseId") String courseId, @Param("userID") Integer userID, @Param("attend") Integer attend, @Param("absent") Integer absent, @Param("att_penalty") Integer att_penalty);

    // Used to find student by ID in certain course, group.
    @Query(value = "SELECT * from user_course WHERE user_group = :userGroup and fk_course_code = :courseId and fk_user_id = :userID ;", nativeQuery = true)
    List<UserCourse> findStudentByCourseAndUserGroupAndID(@Param("userGroup") String userGroup, @Param("courseId") String courseId, @Param("userID") Integer userID);

    List<UserCourse> findUserCourseByUser(User user);
    UserCourse findUserCourseByUserAndCourse(User user , Course course);

    //returns a list of all userCourses from the userCourse table that have the given userID as a foreign key
    @Query(value = "SELECT * FROM user_course WHERE fk_user_id = :userId", nativeQuery = true)
    List<UserCourse> findUserCourseByUserId(@Param("userId") Integer userId);
}
