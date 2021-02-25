package com.example.attendance.Resource;

import com.example.attendance.Models.Attendance;
import com.example.attendance.Models.TeachingAssistant;
import com.example.attendance.Service.CourseService;
import com.example.attendance.Service.TeachingAssistantService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Controller
@RequestMapping(path = "/ta")
public class TeachingAssistantResource {
    @Autowired
    private TeachingAssistantService teachingAssistantService;

//    @PostMapping( path = "/addTA") public @ResponseBody String addTA(){
//        TeachingAssistant Ta1 = new TeachingAssistant(20192000, "first TA", "TA1", "TA1");
//        teachingAssistantService.addTeachingAssistant(Ta1);
//        return "done";
//    }

    @PostMapping( path = "postattendance")
    public @ResponseBody void postAttendance(@RequestParam("date") @DateTimeFormat(pattern = "dd-MM-yyyy") Date date,
                          @RequestParam String userGroup, @RequestParam String courseId, @RequestParam Integer userId){

        teachingAssistantService.postAttendance(date, userGroup, courseId, userId);

        //return date.toString();
    }

    @GetMapping( path = "getTaughtCourses")
    public @ResponseBody List<String> postAttendance(@RequestParam Integer userId){
        return teachingAssistantService.getRegisteredCourses(userId);
    }

    @GetMapping( path = "/getAbsence")
    public @ResponseBody
    String getAbsence(@RequestParam Integer studentID, @RequestParam String courseID){
        return teachingAssistantService.getAbsence(studentID,courseID);
    }

    @PostMapping( path = "closeTAattendance")
    public @ResponseBody void closeTAattendance(@RequestParam("date") @DateTimeFormat(pattern = "dd-MM-yyyy") Date date,
                                             @RequestParam String userGroup, @RequestParam String courseId, @RequestParam Integer userId){

        teachingAssistantService.closeTaAttendance(date, userGroup, courseId, userId);

    }
}
