package com.example.attendance.Forums.Model;

import com.example.attendance.Models.User;

import javax.persistence.*;
import java.util.Date;

@Entity
public class Reply {
    @Id
    @GeneratedValue
    private Integer id;

    @ManyToOne
    @JoinColumn(name = "fk_user_id")
    private User postedBy;

    @Basic
    @Temporal(TemporalType.DATE)
    private Date date;

    @ManyToOne
    @JoinColumn(name = "fk_post_id")
    private Post post;

    private String description;

    @ManyToOne
    @JoinColumn(name ="fk_parent_reply_id")
    private Reply parent_reply;

    public Integer getId() {
        return id;
    }
    public void setId(Integer id) {
        this.id = id;
    }

    public User getPostedBy() {
        return postedBy;
    }
    public void setPostedBy(User postedBy) {
        this.postedBy = postedBy;
    }

    public Date getDate() {
        return date;
    }
    public void setDate(Date date) {
        this.date = date;
    }

    public Post getPost() {
        return post;
    }
    public void setPost(Post post) {
        this.post = post;
    }

    public String getDescription() {
        return description;
    }
    public void setDescription(String description) {
        this.description = description;
    }

//    public Reply getParent_reply() {
//        return parent_reply;
//    }
//    public void setParent_reply(Reply parent_reply) {
//        this.parent_reply = parent_reply;
//    }

}
