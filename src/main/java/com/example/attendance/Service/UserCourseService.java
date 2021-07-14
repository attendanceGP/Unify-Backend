package com.example.attendance.Service;

import com.example.attendance.Models.Course;
import com.example.attendance.Models.User;
import com.example.attendance.Models.UserCourse;
import com.example.attendance.Repository.UserCourseRepository;
import org.json.JSONArray;
import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class UserCourseService {
    @Autowired
    private UserCourseRepository userCourseRepository;

    public void addStudentCourse(UserCourse userCourse){
        userCourseRepository.save(userCourse);
    }
    public List<UserCourse> getUserCourses(User user){
        return userCourseRepository.findUserCourseByUser(user);
    }
    public UserCourse getUserCourse(User user, Course course){return userCourseRepository.findUserCourseByUserAndCourse(user,course);}

    public JSONArray getUserCoursesById(Integer userId) {
        return getJsonFromUserCourse(userCourseRepository.findUserCourseByUserId(userId));
    }

    public JSONArray getJsonFromUserCourse(List<UserCourse> userCourses){
        JSONArray jsonArray = new JSONArray();

        for(UserCourse userCourse: userCourses){
            JSONObject jsonObject = new JSONObject();

            jsonObject.put("user_group", userCourse.getUserGroup());
            jsonObject.put("course_code", userCourse.getCourse().getCourseCode());

            jsonArray.put(jsonObject);
        }

        return jsonArray;
    }
}
