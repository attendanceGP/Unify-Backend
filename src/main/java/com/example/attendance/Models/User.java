package com.example.attendance.Models;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import java.util.Date;

@Entity
public abstract class User {
    @Id
    private Integer id;

    @NotNull
    private String name;

    @NotNull
    private String username;

    @NotNull
    private String password;

    @Basic
    @Temporal(TemporalType.DATE)
    private Date lastLoginDate;

    private String token;

    public User() {
    }

    public User(Integer id, @NotNull String name, @NotNull String username, @NotNull String password) {
        this.id = id;
        this.name = name;
        this.username = username;
        this.password = password;
        this.lastLoginDate = null;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public Date getLastLoginDate() {
        return lastLoginDate;
    }

    public void setLastLoginDate(Date lastLoginDate) {
        this.lastLoginDate = lastLoginDate;
    }

    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }
}
