/*
package com.example.attendance.Resource;

import com.example.attendance.AttendanceApplication;
import com.example.attendance.Attendance.Model.Attendance;
import com.example.attendance.Course.Model.Course;
import com.example.attendance.User.Models.Student;
import com.example.attendance.Attendance.Repository.AttendanceRepository;
import com.example.attendance.Course.Repository.CourseRepository;
import com.example.attendance.User.Repository.StudentRepository;
import com.example.attendance.Attendance.Service.AttendanceService;
import com.example.attendance.Course.Service.CourseService;
import com.example.attendance.User.Service.StudentService;
import org.junit.Before;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.junit.runner.RunWith;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.context.TestConfiguration;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.context.annotation.Bean;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.RequestBuilder;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.web.context.WebApplicationContext;

import static org.mockito.BDDMockito.given;
import static org.springframework.test.web.servlet.setup.MockMvcBuilders.webAppContextSetup;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
@RunWith(SpringRunner.class)
@ContextConfiguration(classes = AttendanceApplication.class)
@WebMvcTest(controllers = AttendanceResource.class)
class AttendanceResourceTest {

    @TestConfiguration
    static class AttendanceServiceTest{
        @Bean
        public AttendanceService attendanceService(){
            return new AttendanceService();
        }

        public StudentService studentService(){
            return new StudentService(null);
        }
        public CourseService courseService(){
            return new CourseService();
        }
    }

    @Autowired
    MockMvc mvc;
    @Autowired
    private AttendanceService attendanceService;
    @MockBean
    private AttendanceRepository attendanceRepository;
    @Autowired
    private StudentService studentService;
    @Autowired
    private CourseService courseService;
    @MockBean
    private StudentRepository studentRepository;
    @MockBean
    private CourseRepository courseRepository;
    @Autowired
    private WebApplicationContext webApplicationContext;
    */
/*@Before
    public void setUp()
    {
        Student s = new Student(20170339,"Youssef",4,2.8);
        studentRepository.save(s);
        Course course =new Course("CS_123","GA");
        courseRepository.save(course);
        Attendance attendance = new Attendance(s,course,"CS_IS_2",new Date(2020-01-01));
        attendanceRepository.save(attendance);
        List<Attendance> attendanceList = new ArrayList<>();
        attendanceList.add(attendance);
        Mockito.when(studentRepository.findById(s.getId())).thenReturn(java.util.Optional.of(s));
        Mockito.when(courseRepository.findById(course.getCourseCode())).thenReturn(java.util.Optional.of(course));
        Mockito.when(attendanceRepository.findAttendanceByUserAndCourse(s,course)).thenReturn(attendanceList);

    }*//*

    @Test
    void getAttendanceDates() throws Exception {
        Student s = new Student(20170339,"Youssef",4,2.8);
        //studentRepository.save(s);
        Course course =new Course("CS_123","GA");
        //courseRepository.save(course);
        //attendanceRepository.save(new Attendance(s,course,"CS_IS_2",new Date(2020-01-01)));
        Attendance attendance = new Attendance(s,course,"CS_IS_2",new Date(2020-01-01));
        List<Attendance> attendanceList = new ArrayList<>();
        attendanceList.add(attendance);
        given(studentService.findById(20170339)).willReturn(java.util.Optional.of(s));
        given(courseService.findById("CS_123")).willReturn(java.util.Optional.of(course));
        given(attendanceService.findByUserAndCourse(s,course)).willReturn(attendanceList);
        mvc.perform(get("/attendance/attendanceDates?studentId=20170339&courseCode=CS_123"))
                .andExpect(content().string(attendanceList.get(0).getDate().toString()));
        //attendanceRepository.save(attendance);
        */
/*RequestBuilder requestBuilder = MockMvcRequestBuilders
                .get("/attendance/attendanceDates?studentId=20170339&courseCode=CS_123");
        MvcResult result = mvc.perform(requestBuilder).andReturn();
        String test = result.getResponse().getContentAsString();
        System.out.println(test);
        assertEquals("[2020-01-01, 2020-01-02, 2020-01-03]",result.getResponse().getContentAsString());*//*


    }
}*/
