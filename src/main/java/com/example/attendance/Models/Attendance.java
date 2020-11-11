package com.example.attendance.Models;

import javax.persistence.*;
import java.util.Date;

@Entity
public class Attendance {
    @Id
    private Long id;

    @ManyToOne
    @JoinColumn(name = "fk_slot_id")
    private Slot slot;

    @ManyToOne
    @JoinColumn(name = "fk_user_course_id")
    private UserCourse userCourse;

    @Basic
    @Temporal(TemporalType.DATE)
    private Date date;
}
