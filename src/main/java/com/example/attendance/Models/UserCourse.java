package com.example.attendance.Models;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import java.util.Date;

@Entity
public class UserCourse {
    @Id
    @GeneratedValue
    private Integer id;

    @ManyToOne
    @JoinColumn(name = "fk_user_id")
    private Student student;

    @ManyToOne
    @JoinColumn(name = "fk_course_code")
    private Course course;

    @NotNull
    private String studentGroup;

    private Integer attendanceCount;

    private Integer absenceCount;

    private String type;

    @Basic
    @Temporal(TemporalType.DATE)
    private Date date;

    public UserCourse() {
    }

    public UserCourse(Student student, Course course, @NotNull String studentGroup) {
        this.student = student;
        this.course = course;
        this.studentGroup = studentGroup;
    }

    public UserCourse(Student student, Course course, @NotNull String studentGroup, Integer attendanceCount, Integer absenceCount) {
        this.student = student;
        this.course = course;
        this.studentGroup = studentGroup;
        this.attendanceCount = attendanceCount;
        this.absenceCount = absenceCount;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Student getStudent() {
        return student;
    }

    public void setStudent(Student student) {
        this.student = student;
    }

    public Course getCourse() {
        return course;
    }

    public void setCourse(Course course) {
        this.course = course;
    }

    public String getStudentGroup() {
        return studentGroup;
    }

    public void setStudentGroup(String studentGroup) {
        this.studentGroup = studentGroup;
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
}
