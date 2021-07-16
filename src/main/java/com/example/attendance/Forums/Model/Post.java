package com.example.attendance.Forums.Model;

import com.example.attendance.Course.Model.Course;
import com.example.attendance.User.Models.User;

import javax.persistence.*;
import java.util.Date;

@Entity
public class Post {
    @Id
    @GeneratedValue
    private Integer id;

    @ManyToOne
    @JoinColumn(name = "fk_user_id")
    private User user;

    @ManyToOne
    @JoinColumn(name = "fk_course_id")
    private Course course;

    @Basic
    @Temporal(TemporalType.TIMESTAMP)
    private Date date;

    private String title;

    @Column(name="content", columnDefinition="TEXT")
    private String content;

    public Post() {
    }

    public Post(User user, Course course, Date date, String title, String content) {
        this.user = user;
        this.course = course;
        this.date = date;
        this.title = title;
        this.content = content;
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

    public Course getCourse() {
        return course;
    }
    public void setCourse(Course course) {
        this.course = course;
    }

    public Date getDate() {
        return date;
    }
    public void setDate(Date date) {
        this.date = date;
    }

    public String getTitle() {
        return title;
    }
    public void setTitle(String title) {
        this.title = title;
    }

    public String getContent() {
        return content;
    }
    public void setContent(String content) {
        this.content = content;
    }
}
