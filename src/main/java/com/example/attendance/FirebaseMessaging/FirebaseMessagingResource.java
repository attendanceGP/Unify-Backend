package com.example.attendance.FirebaseMessaging;

import com.google.firebase.messaging.FirebaseMessagingException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.HashMap;
import java.util.Map;

/**
 * this is made to test the notification only
 */
@Controller
public class FirebaseMessagingResource {
    @Autowired
    FirebaseMessagingService firebaseMessagingService;

    @RequestMapping("/sendNotification")
    @ResponseBody
    public String sendNotification(@RequestParam String title, @RequestParam String body,
                                   @RequestParam String topic) throws FirebaseMessagingException {
        return firebaseMessagingService.sendNotification(title, body, topic);
    }
}
