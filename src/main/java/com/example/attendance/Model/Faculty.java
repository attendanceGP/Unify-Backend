package com.example.attendance.Model;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Entity
public class Faculty {
    @Id
    @GeneratedValue(strategy= GenerationType.AUTO)
    private Integer id;

    @ManyToOne
    @JoinColumn(name = "university_id")
    private University university;

    @OneToOne
    @JoinColumn(name = "schedule_id")
    private Schedule schedule;

    private String name;

    /*@OneToMany
    @JoinColumn(name = "STUDENT_ID", referencedColumnName = "id")
    private List<Student> students;

    @OneToMany
    @JoinColumn(name = "PROFESSOR_ID", referencedColumnName = "id")
    private List<Professor> professors;

    @OneToMany
    @JoinColumn(name = "TA_ID", referencedColumnName = "id")
    private List<TeachingAssistant> TAs;*/

}
