package com.example.attendance.Model;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Entity
public class University {
    @Id
    @GeneratedValue(strategy= GenerationType.AUTO)
    private Integer id;

    @OneToMany(mappedBy = "university")
    private List<Faculty> faculties;

    private String name;

    private String sourceAPI;   //API we'll use to contact the university servers
}
