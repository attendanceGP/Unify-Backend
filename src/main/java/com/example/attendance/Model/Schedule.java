package com.example.attendance.Model;

import javax.persistence.*;
import java.util.List;

@Entity
public class Schedule {
    @Id
    @GeneratedValue(strategy= GenerationType.AUTO)
    private Integer id;

    @OneToMany(mappedBy = "schedule")
    private List<Slot> slots;
}
