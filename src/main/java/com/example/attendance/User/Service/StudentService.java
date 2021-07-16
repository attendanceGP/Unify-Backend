package com.example.attendance.User.Service;

import com.example.attendance.Attendance.Model.Attendance;
import com.example.attendance.Course.Model.Course;
import com.example.attendance.Attendance.Repository.AttendanceRepository;
import com.example.attendance.Course.Repository.CourseRepository;
import com.example.attendance.Course.Repository.UserCourseRepository;
import com.example.attendance.User.Repository.StudentRepository;
import com.example.attendance.User.Models.Student;
import com.example.attendance.Course.Model.UserCourse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;
import org.springframework.boot.web.client.RestTemplateBuilder;

import java.util.Date;
import java.util.List;
import java.util.Optional;

@Service
public class StudentService {
    @Autowired
    private StudentRepository studentRepository;

    public StudentService() {
    }

    public void addStudent(Student student){
        studentRepository.save(student);
    }

    public Optional<Student> findById(Integer id){
        return studentRepository.findById(id);
    }


}
