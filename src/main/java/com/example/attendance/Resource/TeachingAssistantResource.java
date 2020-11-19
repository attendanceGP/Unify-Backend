package com.example.attendance.Resource;

import com.example.attendance.Models.Attendance;
import com.example.attendance.Service.CourseService;
import com.example.attendance.Service.TeachingAssistantService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.util.Date;

@Controller
@RequestMapping(path = "/ta")
public class TeachingAssistantResource {
    @Autowired
    private TeachingAssistantService teachingAssistantService;

    @PostMapping( path = "postattendance")
    public @ResponseBody String postAttendance(@RequestParam("date") @DateTimeFormat(pattern = "dd-MM-yyyy") Date date,
                          @RequestParam String userGroup, @RequestParam String courseId, @RequestParam Integer userId){

        teachingAssistantService.postAttendance(date, userGroup, courseId, userId);

        return date.toString();
    }

    @GetMapping( path = "getAbsence")
    public @ResponseBody String getAbsence(@RequestParam Integer studentID, @RequestParam Integer userID, @RequestParam String courseId){
        return teachingAssistantService.getAbsence(studentID, userID, courseId);
    }
}
