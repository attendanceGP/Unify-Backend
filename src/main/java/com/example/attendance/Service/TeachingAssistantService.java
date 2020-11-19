package com.example.attendance.Service;

import com.example.attendance.Models.*;
import com.example.attendance.Repository.AttendanceRepository;
import com.example.attendance.Repository.CourseRepository;
import com.example.attendance.Repository.StudentCourseRepository;
import com.example.attendance.Repository.TeachingAssistantRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;
import java.util.Optional;

@Service
public class TeachingAssistantService {
    @Autowired
    private TeachingAssistantRepository teachingAssistantRepository;

    @Autowired
    private CourseRepository courseRepository;

    @Autowired
    private AttendanceRepository attendanceRepository;

    @Autowired
    private StudentCourseRepository studentCourseRepository;

    public Optional<TeachingAssistant> findTAById(Integer id){
        return teachingAssistantRepository.findById(id);
    }

    public void postAttendance(Date date, String userGroup, String courseId, Integer userId){
        TeachingAssistant ta = teachingAssistantRepository.findById(userId).get();

        Course course = courseRepository.findById(courseId).get();

        Attendance attendance = new Attendance(ta, course, userGroup, date);

        attendanceRepository.save(attendance);
    }

    public String getAbsence(Integer StudentID, Integer userID, String CourseID){
        List<UserCourse> StudentCourseGroup = studentCourseRepository.findByStudentIDAndCourseID(StudentID, CourseID);
        String group= StudentCourseGroup.get(0).getUserGroup();

        List<UserCourse> TAList = studentCourseRepository.findByUserIDAndCourseIDAndGroup(userID, CourseID, group);

        System.out.println("number of absences : " + (TAList.size()-StudentCourseGroup.size()));

        for(UserCourse userCourse: StudentCourseGroup) {
            for(UserCourse TAAttendanceList: TAList){
                //userCourse.get
            }
        }
        return "done";
    }
}
