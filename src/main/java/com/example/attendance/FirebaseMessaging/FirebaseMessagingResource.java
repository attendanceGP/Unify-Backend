package com.example.attendance.FirebaseMessaging;

import com.example.attendance.Course.Service.UserCourseService;
import com.google.firebase.messaging.FirebaseMessagingException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

/**
 * this is made to test the notification only
 */
@Controller
public class FirebaseMessagingResource {
    @Autowired
    FirebaseMessagingService firebaseMessagingService;

    @Autowired
    UserCourseService userCourseService;

    @RequestMapping("/sendNotification")
    @ResponseBody
    public String sendNotification(@RequestParam String title, @RequestParam String body,
                                   @RequestParam String topic) throws FirebaseMessagingException {
        return firebaseMessagingService.sendNotification(title, body, topic);
    }

    @GetMapping(value = "/getUserCoursesAndGroups")
    public @ResponseBody String getStudentCourses(@RequestParam Integer userId){
        return userCourseService.getUserCoursesById(userId).toString();
    }
}
