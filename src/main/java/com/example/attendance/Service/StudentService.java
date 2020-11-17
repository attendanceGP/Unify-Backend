package com.example.attendance.Service;

import com.example.attendance.Containers.StudentCourseContainer;
import com.example.attendance.Models.Attendance;
import com.example.attendance.Models.Course;
import com.example.attendance.Models.Student;
import com.example.attendance.Models.TeachingAssistant;
import com.example.attendance.Repository.AttendanceRepository;
import com.example.attendance.Repository.CourseRepository;
import com.example.attendance.Repository.StudentRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;
import org.springframework.boot.web.client.RestTemplateBuilder;
import org.springframework.http.*;

import java.util.Date;
import java.util.List;
import java.util.Optional;

@Service
public class StudentService {
    @Autowired
    private StudentRepository studentRepository;

    @Autowired
    private AttendanceRepository attendanceRepository;

    @Autowired
    private CourseRepository courseRepository;

    private final RestTemplate restTemplate;    // to perform http requests to get students from uni

    public StudentService(RestTemplateBuilder restTemplateBuilder) {
        this.restTemplate = restTemplateBuilder.build();
    }

    /**
     *
     * @param username
     * @param password
     * @return studentCourseContainer
     *
     * This function perfoms an http request asking the faculty/uni to verify the username and the password
     * and if they are correct they return a json string which should map to the StudentCourseContainer class
     * and if they are not correct then we handle the exception and return null
     *
     */
    public StudentCourseContainer getStudentCourses(String username, String password){
        String url = "http://my-json-server.typicode.com/alialfie/testingJSON/db";  // dummy data for testing

        try{
            ResponseEntity<StudentCourseContainer> response =
                    this.restTemplate.getForEntity(url, StudentCourseContainer.class);
            if(response.getStatusCode() == HttpStatus.OK && response.getStatusCodeValue() == 200) {
                return response.getBody();
            } else {
                return null;
            }
        }catch (Exception e){
            return null;
        }
    }

    public void addStudent(Student student){
        studentRepository.save(student);
    }

    public Optional<Student> findById(Integer id){
        return studentRepository.findById(id);
    }


    public String postAttendance(Date date, String userGroup, String courseId, Integer userId){
        List<Attendance> entries = attendanceRepository.findByCourseAndUserGroupAndDate(date, userGroup, courseId);

        if(entries.size() == 0){
            return "No TA started taking attendance for this course and day.";
        }

        Student student = studentRepository.findById(userId).get();

        Course course = courseRepository.findById(courseId).get();

        Attendance attendance = new Attendance(student, course, userGroup, date);

        attendanceRepository.save(attendance);

        return "Done.";
    }
}
