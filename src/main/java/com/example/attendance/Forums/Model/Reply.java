package com.example.attendance.Forums.Model;

import com.example.attendance.User.Models.User;

import javax.persistence.*;
import java.util.Date;

@Entity
public class Reply {
    @Id
    @GeneratedValue
    private Integer id;

    @ManyToOne
    @JoinColumn(name = "fk_user_id")
    private User user;

    @Basic
    @Temporal(TemporalType.TIMESTAMP)
    private Date date;

    @ManyToOne
    @JoinColumn(name = "fk_post_id")
    private Post post;

    @Column(name="description", columnDefinition="TEXT")
    private String description;

    public Reply() {
    }

    public Reply(User user, Post post, Date date, String description) {
        this.user = user;
        this.post = post;
        this.date = date;
        this.description = description;
    }

    public Integer getId() {
        return id;
    }
    public void setId(Integer id) {
        this.id = id;
    }

    public User getUser() {
        return user;
    }
    public void setUser(User user) {
        this.user = user;
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

}
