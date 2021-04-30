package com.example.attendance.Models;

import javax.persistence.Entity;
import javax.validation.constraints.NotNull;

@Entity
public class TeachingAssistant extends User{

    private Double longitude;
    private Double latitude;
    public TeachingAssistant() {

    }

    public TeachingAssistant(Integer id, @NotNull String name, @NotNull String username, @NotNull String password,double lon,double lat) {
        super(id, name, username, password);
        this.longitude = lon;
        this.latitude = lat;
    }
    public double getLongitude() {
        return longitude;
    }

    public void setLongitude(double longitude) {
        this.longitude = longitude;
    }

    public double getLatitude() {
        return latitude;
    }

    public void setLatitude(double latitude) {
        this.latitude = latitude;
    }


}
