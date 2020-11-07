package com.example.attendance;

import com.example.attendance.Models.Student;
import com.example.attendance.Service.StudentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class AttendanceApplication {


    public static void main(String[] args) {
        SpringApplication.run(AttendanceApplication.class, args);
    }

}
