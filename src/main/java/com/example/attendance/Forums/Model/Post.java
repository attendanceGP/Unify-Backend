package com.example.attendance.Forums.Model;

import com.example.attendance.Models.Course;
import com.example.attendance.Models.User;

import javax.persistence.*;
import java.util.Date;

@Entity
public class Post {
    @Id
    @GeneratedValue
    private Integer id;

    @ManyToOne
    @JoinColumn(name = "fk_user_id")
    private User postedBy;

    @ManyToOne
    @JoinColumn(name = "fk_course_id")
    private Course course;

    @Basic
    @Temporal(TemporalType.DATE)
    private Date date;

    private String title;

    private String description;

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

    public String getDescription() {
        return description;
    }
    public void setDescription(String description) {
        this.description = description;
    }
}
