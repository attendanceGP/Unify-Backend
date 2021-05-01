package com.example.attendance.Deadline.Service;

import com.example.attendance.Deadline.Model.Deadline;
import com.example.attendance.Deadline.Repository.DeadlineRepository;
import com.example.attendance.Models.Course;
import com.example.attendance.Models.User;
import com.example.attendance.Repository.CourseRepository;
import com.example.attendance.User.Repository.UserRepository;
import org.json.JSONArray;
import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;

@Service
public class DeadlineService {
    @Autowired
    private DeadlineRepository deadlineRepository;

    @Autowired
    private CourseRepository courseRepository;

    @Autowired
    private UserRepository userRepository;

    public JSONArray getStudentDeadlines(Integer userId){
        JSONArray jsonArray = getJsonFromDeadlines((deadlineRepository.getStudentDeadlines(userId)));
        return jsonArray;
    }

    private JSONArray getJsonFromDeadlines(List<Deadline> deadlines){
        JSONArray jsonArray = new JSONArray();

        for(Deadline deadline: deadlines){
            JSONObject jsonObject = new JSONObject();

            jsonObject.put("id", deadline.getId());
            jsonObject.put("courseCode", deadline.getCourse().getCourseCode());
            jsonObject.put("dueDate", deadline.getDeadlineDate());
            jsonObject.put("assignmentName", deadline.getName());

            jsonArray.put(jsonObject);
        }

        return jsonArray;
    }

    public int updateDueDate(Integer deadlineId, Date date){
        return deadlineRepository.updateDueDate(deadlineId, date);
    }

    public int postDeadline(Integer userId, String courseCode, String name, Date deadlineDate, Date postedDate) {
        User user = userRepository.findById(userId).get();
        Course course = courseRepository.findById(courseCode).get();

        Deadline deadline = new Deadline(user, course, postedDate, deadlineDate, name);

        deadlineRepository.save(deadline);

        System.out.println("jgjgj" + userId + " " + courseCode + " " + name + " " + deadlineDate + " " + postedDate);

        return 1;
    }
}
