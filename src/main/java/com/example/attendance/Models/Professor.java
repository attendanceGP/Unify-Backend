package com.example.attendance.Models;


import javax.persistence.Entity;
import javax.validation.constraints.NotNull;

@Entity
public class Professor extends User{
    public Professor() {
    }

    public Professor(Integer id, @NotNull String name, @NotNull String username, @NotNull String password) {
        super(id, name, username, password);
    }
}
