package com.example.attendance.Announcements.Model;

import com.example.attendance.Models.Course;
import com.example.attendance.Models.User;

import javax.persistence.*;
import java.util.Date;

@Entity
public class Announcement {
    @Id
    @GeneratedValue
    private Integer id;

    @ManyToOne
    @JoinColumn(name = "fk_user_id")
    private User postedBy;

    @ManyToOne
    @JoinColumn(name = "fk_course_id")
    private Course course;

    // this is a datetime because we'll need the date and time for the due date
    @Column(name = "posted_date", columnDefinition="DATETIME")
    @Temporal(TemporalType.TIMESTAMP)
    private Date postedDate;

    private String title;

    @Column(name="post", columnDefinition="TEXT")
    private String post;

    public Announcement(User user, Course course, Date postedDate, String title, String post) {
        this.postedBy = user;
        this.course = course;
        this.postedDate = postedDate;
        this.title = title;
        this.post = post;
    }

    public Announcement() {

    }

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

    public Date getPostedDate() {
        return postedDate;
    }

    public void setPostedDate(Date postedDate) {
        this.postedDate = postedDate;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getPost() {
        return post;
    }

    public void setPost(String post) {
        this.post = post;
    }

}
