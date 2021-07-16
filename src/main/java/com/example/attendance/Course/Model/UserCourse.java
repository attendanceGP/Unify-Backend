package com.example.attendance.Course.Model;

import com.example.attendance.Course.Model.Course;
import com.example.attendance.User.Models.User;

import javax.persistence.*;
import javax.validation.constraints.NotNull;

@Entity
public class UserCourse {
    @Id
    @GeneratedValue
    private Integer id;

    @ManyToOne
    @JoinColumn(name = "fk_user_id")
    private User user;

    @ManyToOne
    @JoinColumn(name = "fk_course_code")
    private Course course;

    @NotNull
    private String userGroup;

    private Integer attendanceCount;

    private Integer absenceCount;

    private Integer penaltyCount;

    public UserCourse() {
    }

    public UserCourse(User user, Course course, @NotNull String userGroup) {
        this.user = user;
        this.course = course;
        this.userGroup = userGroup;
        this.attendanceCount = 0;
        this.absenceCount =0;
        this.penaltyCount =0;
    }

    public UserCourse(User user, Course course, @NotNull String userGroup, Integer attendanceCount, Integer absenceCount, Integer penaltyCount) {
        this.user = user;
        this.course = course;
        this.userGroup = userGroup;
        this.attendanceCount = attendanceCount;
        this.absenceCount = absenceCount;
        this.penaltyCount = penaltyCount;
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

    public void setUser (User user) {
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

    public Integer getAttendanceCount() {
        return attendanceCount;
    }

    public void setAttendanceCount(Integer attendanceCount) {
        this.attendanceCount = attendanceCount;
    }

    public Integer getAbsenceCount() {
        return absenceCount;
    }

    public void setAbsenceCount(Integer absenceCount) {
        this.absenceCount = absenceCount;
    }

    public Integer getPenaltyCount() {
        return penaltyCount;
    }

    public void setPenaltyCount(Integer penaltyCount) {
        this.penaltyCount = penaltyCount;
    }
}
