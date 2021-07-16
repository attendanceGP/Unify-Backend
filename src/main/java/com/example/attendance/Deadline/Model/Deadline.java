package com.example.attendance.Deadline.Model;

import com.example.attendance.Course.Model.Course;
import com.example.attendance.User.Models.User;

import javax.persistence.*;
import java.util.Date;

@Entity
public class Deadline {
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
    private Date postedDate;

    // this is a datetime because we'll need the date and time for the due date
    @Column(name = "deadline_date", columnDefinition="DATETIME")
    @Temporal(TemporalType.TIMESTAMP)
    private Date deadlineDate;

    private String name;

    public Deadline() {
    }

    public Deadline(User postedBy, Course course, Date postedDate, Date deadlineDate, String name) {
        this.postedBy = postedBy;
        this.course = course;
        this.postedDate = postedDate;
        this.deadlineDate = deadlineDate;
        this.name = name;
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

    public Date getDeadlineDate() {
        return deadlineDate;
    }

    public void setDeadlineDate(Date deadlineDate) {
        this.deadlineDate = deadlineDate;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}
