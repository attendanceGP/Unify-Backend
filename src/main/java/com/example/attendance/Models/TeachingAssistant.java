package com.example.attendance.Models;

import javax.persistence.Entity;
import javax.validation.constraints.NotNull;

@Entity
public class TeachingAssistant extends User{

    public TeachingAssistant() {

    }

    public TeachingAssistant(Integer id, @NotNull String name, @NotNull String username, @NotNull String password) {
        super(id, name, username, password);
    }

}
