package com.example.attendance.Forums.Repository;

import com.example.attendance.Deadline.Model.Deadline;
import com.example.attendance.Forums.Model.Post;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;

import java.util.Date;
import java.util.List;

public interface PostRepository extends CrudRepository<Post, Long> {
    @Query(value = "SELECT * FROM POST WHERE fk_course_id IN (SELECT fk_course_code FROM user_course where fk_user_id = :userId) ORDER BY date DESC ;", nativeQuery = true)
    List<Post> getStudentPosts(@Param("userId") Integer id);

    @Query(value = "SELECT * FROM POST WHERE id = :postId ;", nativeQuery = true)
    Post findById(@Param("postId")Integer postId);

    @Query(value = "DELETE FROM Post WHERE id = :postId ;", nativeQuery = true)
    void deleteById(@Param("postId")Integer postId);
}
