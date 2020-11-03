package com.example.attendance.Model;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.validation.constraints.NotNull;

@Entity
public abstract class User {
    @Id
    private String id;

    @NotNull
    private String name;

}
