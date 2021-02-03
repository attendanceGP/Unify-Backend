package com.example.attendance.Models;

import javax.persistence.*;
import java.util.Date;

@Entity
public class Attendance {
    @Id
    @GeneratedValue
    private Long id;

//    @ManyToOne
//    @JoinColumn(name = "fk_slot_id")
//    private Slot slot;

    @ManyToOne
    @JoinColumn(name = "fk_user_id")
    private User user;

    @ManyToOne
    @JoinColumn(name = "fk_course_id")
    private Course course;

    private String userGroup;

    @Basic
    @Temporal(TemporalType.DATE)
    private Date date;

    private boolean absent;
    public Attendance() {
    }

    public Attendance(User user, Course course, String userGroup, Date date) {
        this.user = user;
        this.course = course;
        this.userGroup = userGroup;
        this.date = date;
    }

    public Attendance(Long id, User user, Course course, String userGroup, Date date) {
        this.id = id;
        this.user = user;
        this.course = course;
        this.userGroup = userGroup;
        this.date = date;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
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

    public String getUserGroup() {
        return userGroup;
    }

    public void setUserGroup(String userGroup) {
        this.userGroup = userGroup;
    }

    public Date getDate() {
        return date;
    }

    public void setDate(Date date) {
        this.date = date;
    }
}
