package com.example.attendance.Deadline.Repository;

import com.example.attendance.Deadline.Model.Deadline;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface DeadlineRepository extends CrudRepository<Deadline, Integer> {

    @Query(value = "SELECT * FROM deadline WHERE deadline_date > current_timestamp() and fk_course_id IN (SELECT fk_course_code FROM user_course where fk_user_id = :userId);", nativeQuery = true)
    List<Deadline> getStudentDeadlines(@Param("userId") Integer id);
}
