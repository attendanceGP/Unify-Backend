package com.example.attendance.Model;

import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToMany;
import java.util.ArrayList;
import java.util.List;

@Entity
public class TeachingAssistant extends  User{

    @ManyToMany(mappedBy = "students")
    private List<Course> courses;
}
