package com.example.attendance.Forums.Repository;

import com.example.attendance.Forums.Model.ReplyVote;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;

import java.util.Date;
import java.util.List;

public interface ReplyVoteRepository extends CrudRepository<ReplyVote, Long> {
}
