package com.example.attendance.Attendance.Repository;

import com.example.attendance.Attendance.Model.Attendance;
import com.example.attendance.Course.Model.Course;

import com.example.attendance.User.Models.Student;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.transaction.annotation.Transactional;

import java.util.Date;
import java.util.List;

public interface AttendanceRepository extends CrudRepository<Attendance, Long> {
   // List<Attendance> findByCourseAndUserGroupAndDate(Date date, String userGroup, Course course);

    @Query(value = "SELECT * FROM attendance WHERE date = :dateValue and user_group = :userGroup and fk_course_id = :courseId and fk_user_id IN (SELECT id FROM user WHERE dtype = 'TeachingAssistant');", nativeQuery = true)
    List<Attendance> findByCourseAndUserGroupAndDate(@Param("dateValue") Date date, @Param("userGroup") String userGroup, @Param("courseId") String courseId);


    // Used for TA to check students attendees in a certain course (PRESENT STUDENT)
    // -------- (FOR ATTENDEES LIST) ----------------
    @Query(value = "SELECT * from attendance WHERE date = :dateValue and user_group = :userGroup and fk_course_id = :courseId and absent = false and fk_user_id IN (SELECT id FROM user WHERE dtype = 'Student');", nativeQuery = true)
    List<Attendance> findStudentByCourseAndUserGroupAndDate(@Param("dateValue") Date date, @Param("userGroup") String userGroup, @Param("courseId") String courseId);

    // Used for TA to check students attendees in a certain course and
    // ----------- CONFIRM AND UPDATE COUNTS -----------------
    @Query(value = "SELECT * from attendance WHERE date = :dateValue and user_group = :userGroup and fk_course_id = :courseId and fk_user_id IN (SELECT id FROM user WHERE dtype = 'Student');", nativeQuery = true)
    List<Attendance> findByCourseAndUserGroupandDate(@Param("dateValue") Date date, @Param("userGroup") String userGroup, @Param("courseId") String courseId);

    // Used to find student by ID in certain course, group, and date to be able to modify it to attended or absent.
    // ----------- FOR SET ABSENT/PRESENT BY TA -----------
    @Query(value = "SELECT * from attendance WHERE date = :dateValue and user_group = :userGroup and fk_course_id = :courseId and fk_user_id = :userID ;", nativeQuery = true)
    List<Attendance> findStudentByCourseAndUserGroupAndDateandID(@Param("dateValue") Date date, @Param("userGroup") String userGroup, @Param("courseId") String courseId, @Param("userID") Integer userID);

    // Used Update student's absent statues by ID in certain course, group, and date to be able to modify it to attended or absent.
    @Modifying @Transactional
    @Query(value = "UPDATE attendance SET absent = :absent_update, penalty = :att_penalty WHERE date = :dateValue and user_group = :userGroup and fk_course_id = :courseId and fk_user_id = :userID ;", nativeQuery = true)
    void UpdateStudentAbsence(@Param("dateValue") Date date, @Param("userGroup") String userGroup, @Param("courseId") String courseId, @Param("userID") Integer userID, @Param("absent_update") boolean absent_update, @Param("att_penalty") boolean att_penalty);



    List<Attendance> findAttendanceByUserAndCourse(Student student, Course course);
    List<Attendance> findAttendanceByCourseAndUserGroupAndDateAndAbsent(Course course ,String userGroup, Date date,boolean absent);
    List<Attendance> findAttendanceByUserAndCourseAndUserGroupAndDateAndAbsent(Student user, Course course , String userGroup, Date date, boolean absent);
    @Query(value = "SELECT * FROM attendance WHERE fk_course_id = :courseId and fk_user_id = :userId and absent = :isAbsent ;", nativeQuery = true)
    List<Attendance> findByUserIDAndCourseIDAndAbsent(@Param("userId") Integer userId, @Param("courseId") String courseId,@Param("isAbsent") boolean isAbsent);

    @Query(value = "SELECT * FROM attendance WHERE fk_user_id = :userId ;", nativeQuery = true)
    List<Attendance> findByUserID(@Param("userId") Integer userId);
    List<Attendance> findByCourseAndDateAndUserGroup(Course course,Date date,String userGroup);
    @Query(value = "SELECT * FROM attendance WHERE fk_user_id = :userId and absent = :isAbsent ;", nativeQuery = true)
    List<Attendance> findByUserIDAndAbsent(@Param("userId") Integer userId,@Param("isAbsent")boolean isAbsent);

    // returns a list of all attendances recorded on a specific date with a specific course
    @Query(value = "SELECT * from attendance WHERE date = :dateValue and fk_course_id = :courseId ;", nativeQuery = true)
    List<Attendance> findByCourseAndDate(@Param("dateValue") Date date, @Param("courseId") String courseId);

    @Query(value = "SELECT * FROM attendance WHERE absent = 0 " +
            "AND (:userId IS NULL OR fk_user_id = :userId) " +
            "AND (:courseCode IS NULL OR fk_course_id = :courseCode) " +
            "AND (:group IS NULL OR user_group = :group) " +
            "AND (:date IS NULL OR date = :date) ;", nativeQuery = true)
    List<Attendance> getAttendanceForUni(@Param("userId") Integer userId, @Param("courseCode") String courseCode,
                                         @Param("group") String group, @Param("date") Date date);

    @Query(value = "SELECT * FROM attendance WHERE absent = 1 " +
            "AND (:userId IS NULL OR fk_user_id = :userId) " +
            "AND (:courseCode IS NULL OR fk_course_id = :courseCode) " +
            "AND (:group IS NULL OR user_group = :group) " +
            "AND (:date IS NULL OR date = :date) ;", nativeQuery = true)
    List<Attendance> getAbsenceForUni(@Param("userId") Integer userId, @Param("courseCode") String courseCode,
                                         @Param("group") String group, @Param("date") Date date);
}
