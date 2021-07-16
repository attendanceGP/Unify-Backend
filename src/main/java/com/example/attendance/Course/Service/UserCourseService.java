package com.example.attendance.Course.Service;

import com.example.attendance.Course.Model.Course;
import com.example.attendance.User.Models.User;
import com.example.attendance.Course.Model.UserCourse;
import com.example.attendance.Course.Repository.UserCourseRepository;
import org.json.JSONArray;
import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class UserCourseService {
    @Autowired
    private UserCourseRepository userCourseRepository;
    public List<UserCourse> getUserCourses(User user){
        return userCourseRepository.findUserCourseByUser(user);
    }
    public UserCourse getUserCourse(User user, Course course){return userCourseRepository.findUserCourseByUserAndCourse(user,course);}

    public JSONArray getUserCoursesById(Integer userId) {
        return getJsonFromUserCourse(userCourseRepository.findUserCourseByUserId(userId));
    }

    // returns json array of group and course code
    public JSONArray getJsonFromUserCourse(List<UserCourse> userCourses){
        JSONArray jsonArray = new JSONArray();

        for(UserCourse userCourse: userCourses){
            JSONObject jsonObject = new JSONObject();

            jsonObject.put("userGroup", userCourse.getUserGroup());
            jsonObject.put("courseCode", userCourse.getCourse().getCourseCode());

            jsonArray.put(jsonObject);
        }

        return jsonArray;
    }
}
