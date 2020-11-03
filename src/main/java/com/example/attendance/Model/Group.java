package com.example.attendance.Model;

import javax.persistence.*;
import java.util.List;

@Entity
public class Group {
    @Id
    @GeneratedValue(strategy= GenerationType.AUTO)
    private Integer id;

    private String name;

    @ManyToMany
    @JoinColumn(name = "student_id")
    private List<Student> students;
}
