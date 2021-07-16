package com.example.attendance.User.Resource;

import com.example.attendance.Absence.Recent;
import com.example.attendance.Absence.TARecent;
import com.example.attendance.Absence.Absence;
import com.example.attendance.User.Models.TeachingAssistant;
import com.example.attendance.User.Service.TeachingAssistantService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.util.Date;
import java.util.List;

@Controller
@RequestMapping(path = "/ta")
public class TeachingAssistantResource {
    @Autowired
    private TeachingAssistantService teachingAssistantService;

    @PostMapping( path = "postattendance")
    public @ResponseBody void postAttendance(@RequestParam("date") @DateTimeFormat(pattern = "dd-MM-yyyy") Date date,
                          @RequestParam String userGroup, @RequestParam String courseId, @RequestParam Integer userId){

        teachingAssistantService.postAttendance(date, userGroup, courseId, userId);
    }

    @GetMapping( path = "getTaughtCourses")
    public @ResponseBody List<String> getTaughtCourses(@RequestParam Integer userId){
        return teachingAssistantService.getRegisteredCourses(userId);
    }

    @GetMapping( path = "/getAbsence")
    public @ResponseBody
    Absence[] getAbsence(@RequestParam Integer studentID){
        return teachingAssistantService.getAbsence(studentID);
    }
    @GetMapping(path = "/getRecent")
    public @ResponseBody
    Recent[]getRecent(@RequestParam int studentId){
        return teachingAssistantService.getRecent(studentId);
    }

    @GetMapping(path = "/getRecentTA")
    public @ResponseBody
    TARecent[]getRecentTA(@RequestParam int TAId){
        return teachingAssistantService.getRecentTA(TAId);
    }

    @PostMapping( path = "closeTAattendance")
    public @ResponseBody void closeTAattendance(@RequestParam("date") @DateTimeFormat(pattern = "dd-MM-yyyy") Date date,
                                             @RequestParam String userGroup, @RequestParam String courseId, @RequestParam Integer userId){

        teachingAssistantService.closeTaAttendance(date, userGroup, courseId, userId);

    }

    @GetMapping(value = "updateTALocation")
    public @ResponseBody void updateTaLocation(@RequestParam int id ,@RequestParam double longitude,@RequestParam double latitude ){
        teachingAssistantService.updateTALocation(id,longitude,latitude);
    }

    @GetMapping(value = "getTA")
    public @ResponseBody TeachingAssistant getTA(@RequestParam int id){
        return teachingAssistantService.getTa(id);
    }

    @GetMapping( path = "getExistingAttendanceGroups")
    public @ResponseBody List<String>  getExistingAttendanceGroups(@RequestParam("date") @DateTimeFormat(pattern = "dd-MM-yyyy") Date date,
                                                           @RequestParam String userGroup, @RequestParam String courseId){
        return teachingAssistantService.attendanceGroupsAlreadyExist(date,courseId,userGroup);
    }
}
