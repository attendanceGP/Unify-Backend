package com.example.attendance.Model;

import java.util.ArrayList;
import java.util.List;
import javax.persistence.*;
import javax.validation.constraints.*;

@Entity
public class Course {
    @Id
    private String courseId;

    @NotNull
    private String name;

    @ManyToMany
    @JoinColumn(name = "STUDENT_ID")
    private List<Student> students;

    @ManyToMany
    @JoinColumn(name = "TA_ID")
    private List<TeachingAssistant> TAs;

    @ManyToMany
    @JoinColumn(name = "PROFESSOR_ID")
    private List<Professor> professors;
}
