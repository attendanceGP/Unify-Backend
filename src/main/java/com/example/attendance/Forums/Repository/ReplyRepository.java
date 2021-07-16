package com.example.attendance.Forums.Repository;

import com.example.attendance.Forums.Model.Post;
import com.example.attendance.Forums.Model.Reply;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.transaction.annotation.Transactional;

import java.util.Date;
import java.util.List;

public interface ReplyRepository extends CrudRepository<Reply, Long> {

    /**
     * Query for getting replies for a forum (post) given its ID
     * @param postId
     * @return list of replies
     */
    @Query(value = "SELECT * FROM REPLY WHERE fk_post_id = :postId ORDER BY date ASC ;", nativeQuery = true)
    List<Reply> getPostReplies(@Param("postId") Integer postId);

    /**
     * Query for deleting a reply given its ID
     * @param replyId
     * @return success status
     */
    @Transactional
    @Modifying
    @Query(value = "DELETE FROM Reply WHERE id = :replyId ;", nativeQuery = true)
    Integer deleteReplyById(@Param("replyId")Integer replyId);

    /**
     * Query for deleting all replies for a specific post, used for when deleting a post
     * @param postId
     * @return success status
     */
    @Transactional
    @Modifying
    @Query(value = "DELETE FROM Reply WHERE fk_post_id = :postId ;", nativeQuery = true)
    Integer deleteReplyByPostId(@Param("postId")Integer postId);
}
