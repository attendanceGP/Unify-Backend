package com.example.attendance.Service;

import com.example.attendance.Models.*;
import com.example.attendance.Repository.AttendanceRepository;
import com.example.attendance.Repository.CourseRepository;
import com.example.attendance.Repository.StudentCourseRepository;
import com.example.attendance.Repository.TeachingAssistantRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.repository.query.Param;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
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

//    public void addTeachingAssistant(TeachingAssistant TA, Student student, Course course){
//        teachingAssistantRepository.save(TA);
//        //Date date = new Date(2020,10,20);
//        //Attendance attendance = new Attendance(student, course, "G1", date);
//        //attendanceRepository.save(attendance);
//    }

    public void postAttendance(Date date, String userGroup, String courseId, Integer userId){
        TeachingAssistant ta1 = new TeachingAssistant(userId, "sarah", "s", "S");
        teachingAssistantRepository.save(ta1);
        TeachingAssistant ta = teachingAssistantRepository.findById(userId).get();

        Course course = courseRepository.findById(courseId).get();

        Attendance attendance = new Attendance(ta, course, userGroup, date);

        attendanceRepository.save(attendance);
    }

    public ArrayList<Date> getAbsence(Integer StudentID, Integer userID, String CourseID){
        List<UserCourse> StudentCourseGroup = studentCourseRepository.findByStudentIDAndCourseID(StudentID, CourseID);
        String group= StudentCourseGroup.get(0).getUserGroup();
        List<Attendance> TAList = attendanceRepository.findByUserIDAndCourseIDAndGroup(userID, CourseID, group);
        List<Attendance> StudentList = attendanceRepository.findByUserIDAndCourseIDAndGroup(StudentID, CourseID, group);
        System.out.println("number of absences : " + (TAList.size()-StudentList.size()));

        ArrayList<Date> TADates = new ArrayList<>();
        ArrayList<Date> stdDates = new ArrayList<>();
        for(Attendance TA : TAList){
            TADates.add(TA.getDate());
        }
        for(Attendance std: StudentList){
            stdDates.add(std.getDate());
        }
        TADates.removeAll(stdDates);
        return TADates;
    }
}
