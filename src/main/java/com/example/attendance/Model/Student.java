package com.example.attendance.Model;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToMany;
import java.util.ArrayList;
import java.util.List;

@Entity
public class Student extends User{
    private Integer level;
    private String username;
    private String password;
    private String macAddress;  //lamya not sure

    @ManyToMany(mappedBy = "students")
    private List<Course> courses;

    @ManyToMany(mappedBy = "students")
    private List<Group> groups;
}
