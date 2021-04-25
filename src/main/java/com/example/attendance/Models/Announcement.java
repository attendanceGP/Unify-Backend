package com.example.attendance.Models;

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

    @Basic
    @Temporal(TemporalType.DATE)
    private Date date;

    private String title;

    private String post;

    public Announcement(User user, Course course, Date date, String title, String post) {
        this.postedBy = user;
        this.course = course;
        this.date = date;
        this.title = title;
        this.post = post;
    }

}
