package com.example.attendance.Models;

import javax.persistence.*;
import java.util.Date;

@Entity
public class Reply {
    @Id
    @GeneratedValue
    private Integer id;

    @ManyToOne
    @JoinColumn(name = "fk_user_id")
    private User postedBy;

    @Basic
    @Temporal(TemporalType.DATE)
    private Date date;

    @ManyToOne
    @JoinColumn(name = "fk_post_id")
    private Post post;

    private String description;
}
