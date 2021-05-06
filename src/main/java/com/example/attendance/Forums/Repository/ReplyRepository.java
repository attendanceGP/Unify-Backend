package com.example.attendance.Forums.Repository;

import com.example.attendance.Forums.Model.Post;
import com.example.attendance.Forums.Model.Reply;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;

import java.util.Date;
import java.util.List;

public interface ReplyRepository extends CrudRepository<Reply, Long> {
    @Query(value = "SELECT * FROM REPLY WHERE fk_post_id = :postId ;", nativeQuery = true)
    List<Reply> getPostReplies(@Param("postId") Integer postId);

    @Query(value = "DELETE FROM Reply WHERE id = :replyId ;", nativeQuery = true)
    Void deleteById(@Param("replyId")Integer replyId);
}
