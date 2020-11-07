package com.example.attendance.Models;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.validation.constraints.NotNull;

@Entity
public class SlotDuration {
    @Id
    private Integer slotNumber;

    @NotNull
    private String startTime;

    @NotNull
    private String endTime;
}
