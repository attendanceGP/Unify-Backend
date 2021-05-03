package com.example.attendance.Forums.Repository;

import com.example.attendance.Deadline.Model.Deadline;
import com.example.attendance.Forums.Model.Post;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;

import java.util.Date;
import java.util.List;

public interface PostRepository extends CrudRepository<Post, Long> {
    @Query(value = "SELECT * FROM POST WHERE fk_course_id IN (SELECT fk_course_code FROM user_course where fk_user_id = :userId);", nativeQuery = true)
    List<Post> getStudentPosts(@Param("userId") Integer id);

}
