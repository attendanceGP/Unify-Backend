package com.example.attendance.Models;

import javax.persistence.Entity;
import javax.validation.constraints.NotNull;

@Entity
public class TeachingAssistant extends User{

    public TeachingAssistant() {

    }
    public TeachingAssistant(@NotNull Integer id, @NotNull String name) {
        super(id, name);
    }


}
