package com.example.attendance.University.Service;

import org.springframework.stereotype.Service;

import com.example.attendance.Models.Course;
import com.example.attendance.Models.User;
import com.example.attendance.Repository.CourseRepository;
import com.example.attendance.User.Repository.UserRepository;
import org.json.JSONArray;
import org.json.JSONObject;
import org.springframework.stereotype.Service;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.Date;

@Service
public class UniversityService {

    public static Integer UNIVERSITY_KEY = 5050;
    @Autowired
    private CourseRepository courseRepository;

    @Autowired
    private UserRepository userRepository;

    /// user for all courses and groups.
    ///key
    public String getAttendance(Integer Key){
        if(Key.equals(UNIVERSITY_KEY)) {

            return "";
        }
        else return "";
    }

    /// user, group, course, date.
    // all
    public String getAttendance(Integer Key, Integer userId, String group, String courseCode, Date date){
        if(Key.equals(UNIVERSITY_KEY)) {

            return "";
        }
        else return "";
    }


    /// user for all courses and groups.
    /// key and userid
    public String getAttendance(Integer Key, Integer userId, String courseCode){
        if(Key.equals(UNIVERSITY_KEY)) {

            return "";
        }
        else return "";
    }

    /// key and userid
    public String getAttendance(Integer Key, Integer userId){
        if(Key.equals(UNIVERSITY_KEY)) {

            return "";
        }
        else return "";
    }
    // key and userid and date
    public String getAttendance(Integer Key, Integer userId, Date date){
        if(Key.equals(UNIVERSITY_KEY)) {

            return "";
        }
        else return "";
    }

    /// user for a course given his group
    // key, userid, group, course
    public String getAttendance(Integer Key, Integer userId, String group, String courseCode){
        if(Key.equals(UNIVERSITY_KEY)) {

            return "";
        }
        else return "";
    }


    /// group, course
    public String getAttendance(Integer Key, String group, String courseCode){
        if(Key.equals(UNIVERSITY_KEY)) {

            return "";
        }
        else return "";
    }
    // group, course, date
    public String getAttendance(Integer Key, String group, String courseCode, Date date){
        if(Key.equals(UNIVERSITY_KEY)) {

            return "";
        }
        else return "";
    }

    /// course, date
    public String getAttendance(Integer Key, String courseCode, Date date){
        if(Key.equals(UNIVERSITY_KEY)) {

            return "";
        }
        else return "";
    }
   /// course
    public String getAttendance(Integer Key, String courseCode){
        if(Key.equals(UNIVERSITY_KEY)) {

            return "";
        }
        else return "";
    }
}
