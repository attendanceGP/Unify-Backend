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

    private String post;

}