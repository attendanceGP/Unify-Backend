package com.example.attendance.Announcements.Repository;

import com.example.attendance.Announcements.Model.Announcement;
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
    @Query(value = "DELETE from announcement WHERE id = :id ;", nativeQuery = true)
    void deleteById(@Param("id") Integer id);

    //gets all announcements that a TA has posted according to their id
    @Query(value = "SELECT * from announcement WHERE fk_user_id = :userId ;", nativeQuery = true)
    List<Announcement> getTaAnnouncements(@Param("userId") Integer userId);

    //gets all announcements for a student in the courses they are enrolled in
    @Query(value = "SELECT * FROM announcement WHERE fk_course_id IN (SELECT fk_course_code FROM user_course where fk_user_id = :userId) ;", nativeQuery = true)
    List<Announcement> getStudentAnnouncements(@Param("userId") Integer userId);

}
