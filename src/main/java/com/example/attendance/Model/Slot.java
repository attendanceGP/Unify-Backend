package com.example.attendance.Model;

import javassist.compiler.ast.Pair;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.Date;

@Entity
public class Slot {
    @Id
    @GeneratedValue(strategy= GenerationType.AUTO)
    private Integer id;

    @ManyToOne
    @JoinColumn(name = "schedule_id")
    private Schedule schedule;

    @ManyToOne
    @JoinColumn(name = "COURSE_ID", referencedColumnName = "courseId")
    private Course course;

    private Date startTime;

    private Date endTime;

}
