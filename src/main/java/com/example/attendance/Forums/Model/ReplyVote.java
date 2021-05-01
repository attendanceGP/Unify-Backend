package com.example.attendance.Forums.Model;

import com.example.attendance.Models.User;

import javax.persistence.*;

@Entity
public class ReplyVote {
    @Id
    @GeneratedValue
    private Integer id;

    @ManyToOne
    @JoinColumn(name = "fk_reply_id")
    private Reply reply;

    @ManyToOne
    @JoinColumn(name = "fk_user_id")
    private User user;

    private boolean upvote;

    public Integer getId() {
        return id;
    }
    public void setId(Integer id) {
        this.id = id;
    }

    public Reply getReply() {
        return reply;
    }
    public void setReply(Reply reply) {
        this.reply = reply;
    }

    public User getUser() {
        return user;
    }
    public void setUser(User user) {
        this.user = user;
    }

    public boolean isUpvote() {
        return upvote;
    }
    public void setUpvote(boolean upvote) {
        this.upvote = upvote;
    }
}
