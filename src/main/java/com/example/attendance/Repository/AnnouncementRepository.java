package com.example.attendance.Repository;

import com.example.attendance.Models.Announcement;
import com.example.attendance.Models.Attendance;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.transaction.annotation.Transactional;

import java.util.Date;
import java.util.List;

public interface AnnouncementRepository extends CrudRepository<Announcement, Integer> {

    //deletes announcement from database according to the following parameters in the query
    @Modifying @Transactional
    @Query(value = "DELETE from announcement WHERE date = :dateValue and title = :title and fk_user_id = :userID and fk_course_id = :courseId ;", nativeQuery = true)
    void deleteByDateAndTitleAndPostedByAndCourse(@Param("dateValue") Date date, @Param("title") String title, @Param("userID") Integer userID, @Param("courseId") String courseId);

    @Query(value = "SELECT * from announcement WHERE fk_user_id = :userID ;", nativeQuery = true)
    List<Announcement> findAnnouncementByPostedBy(@Param("userID") Integer userID);

}
