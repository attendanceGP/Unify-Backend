package com.example.attendance.Models;

import javax.persistence.*;

@Entity
public class Slot {
    @Id
    @GeneratedValue
    private Integer id;

    private Integer slotNumber;


    @ManyToOne
    @JoinColumn(name = "fk_course_code")
    private Course course;

    private String studentGroup;
}
