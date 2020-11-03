package com.example.attendance.Model;

import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToMany;
import java.util.ArrayList;
import java.util.List;

@Entity
public class Professor extends User{

    @ManyToMany(mappedBy = "professors")
    private List<Course> courses;
}
