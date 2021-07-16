package com.example.attendance.User.Models;

import javax.persistence.Entity;
import javax.validation.constraints.NotNull;

@Entity
public class Student extends User {
    @NotNull
    private Integer level;

    @NotNull
    private Double gpa;

    public Student() {
    }

    public Student(Integer id, @NotNull String name, @NotNull String username, @NotNull String password, @NotNull Integer level, @NotNull Double gpa) {
        super(id, name, username, password);
        this.level = level;
        this.gpa = gpa;
    }

    public Integer getLevel() {
        return level;
    }

    public void setLevel(Integer level) {
        this.level = level;
    }

    public Double getGpa() {
        return gpa;
    }

    public void setGpa(Double gpa) {
        this.gpa = gpa;
    }
}
