package com.example.attendance.University.Resources;


import com.example.attendance.Models.*;


import com.example.attendance.University.Service.UniversityService;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.web.bind.annotation.*;

import java.util.Date;

@Controller
@RequestMapping(path = "/university")
public class UniversityResource {

    @Autowired
    private UniversityService universityService;

    @GetMapping(path = "getAttendance")
    public @ResponseBody
    String getAttendance(@RequestParam("Key") Integer Key,
                         @RequestParam(value ="userId", required = false) Integer userId,
                         @RequestParam(value ="group", required = false) String group,
                         @RequestParam(value ="courseCode", required = false) String courseCode,
                         @RequestParam(value = "date", required = false) @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss") Date date){
        // key only
        if(userId == null && group == null && courseCode == null && date == null) return universityService.getAttendance(Key);

        // key and userId
        else if(group == null && courseCode == null && date == null) return universityService.getAttendance(Key, userId);

        // key, userid, group, course
        else if(userId != null && group != null && courseCode != null && date == null) return universityService.getAttendance(Key, userId, group, courseCode);
        
        // key, userid, course
        else if(userId != null && group == null && courseCode != null && date == null) return universityService.getAttendance(Key, userId, courseCode);

        /// key, userid, date
        else if(userId != null && group == null && courseCode == null && date != null) return universityService.getAttendance(Key, userId, date);

        // key, group, course
        else if(userId == null && group != null && courseCode != null && date == null) return universityService.getAttendance(Key, group, courseCode);

        // key, group, course, date
        else if(userId == null && group != null && courseCode != null && date != null) return universityService.getAttendance(Key, group, courseCode, date);

        // key, course, date
        else if(userId == null && group == null && courseCode != null && date != null) return universityService.getAttendance(Key, courseCode, date);

        // key, course
        else if(userId == null && group == null && courseCode != null && date == null) return universityService.getAttendance(Key, courseCode);

        // all parameters available
        else return universityService.getAttendance(Key, userId, group, courseCode, date);
    }

}
