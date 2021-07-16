package com.example.attendance.Forums.Repository;

import com.example.attendance.Deadline.Model.Deadline;
import com.example.attendance.Forums.Model.Post;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.transaction.annotation.Transactional;

import java.util.Date;
import java.util.List;

public interface PostRepository extends CrudRepository<Post, Long> {

    /**
     * Query for retrieving forums for each user according to his registered courses
     *
     * @param id
     * @return List <Post>
     */
    @Query(value = "SELECT * FROM POST WHERE fk_course_id IN (SELECT fk_course_code FROM user_course where fk_user_id = :userId) ORDER BY date DESC ;", nativeQuery = true)
    List<Post> getStudentPosts(@Param("userId") Integer id);

    /**
     * Query for retrieving a forum given its ID
     * → PostID is unique
     *
     * @param postId
     * @return Post
     */
    @Query(value = "SELECT * FROM POST WHERE id = :postId ;", nativeQuery = true)
    Post findById(@Param("postId")Integer postId);

    /**
     * Query for deleting a forum (post) given its ID
     *
     * @param postId
     * @return success status
     */
    @Transactional
    @Modifying
    @Query(value = "DELETE FROM Post WHERE id = :postId ;", nativeQuery = true)
    Integer deletePostById(@Param("postId")Integer postId);

}
