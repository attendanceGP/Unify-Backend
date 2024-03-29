package com.example.attendance.Attendance.Model;

import com.example.attendance.Course.Model.Course;
import com.example.attendance.User.Models.User;

import javax.persistence.*;
import java.util.Date;

@Entity
public class Attendance {
    @Id
    @GeneratedValue
    private Long id;

    @ManyToOne
    @JoinColumn(name = "fk_user_id")
    private User user;

    @ManyToOne
    @JoinColumn(name = "fk_course_id")
    private Course course;

    private String userGroup;
    private boolean absent;

    @Basic
    @Temporal(TemporalType.DATE)
    private Date date;

    private boolean penalty;

    public boolean isAbsent() {
        return absent;
    }

    public void setAbsent(boolean absent) {
        this.absent = absent;
    }


    public Attendance() {
    }

    public Attendance(User user, Course course, String userGroup, Date date, boolean absent) {
        this.user = user;
        this.course = course;
        this.userGroup = userGroup;
        this.date = date;
        this.absent = absent;
    }

    public Attendance(Long id, User user, Course course, String userGroup, Date date, boolean absent) {
        this.id = id;
        this.user = user;
        this.course = course;
        this.userGroup = userGroup;
        this.date = date;
        this.absent = absent;
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

    public boolean getAbsent() {
        return absent;
    }
    public boolean isPenalty() {
        return penalty;
    }

    public void setPenalty(boolean penalty) {
        this.penalty = penalty;
    }

}
