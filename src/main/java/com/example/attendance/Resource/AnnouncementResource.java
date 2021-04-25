package com.example.attendance.Resource;

import com.example.attendance.Repository.CourseRepository;
import com.example.attendance.Repository.TeachingAssistantRepository;
import com.example.attendance.Service.AnnouncementService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.util.Date;

@Controller
@RequestMapping(path = "/announcement")
public class AnnouncementResource {

    @Autowired
    private AnnouncementService announcementService;

    @PostMapping( path = "postannouncement")
    public @ResponseBody
    void postAnnouncement(@RequestParam Integer userId, @RequestParam String courseId, @RequestParam("date") @DateTimeFormat(pattern = "dd-MM-yyyy") Date date,
                        @RequestParam String title, @RequestParam String post){
        announcementService.postAnnouncement(userId,courseId,date,title,post);

    }

    @DeleteMapping( path = "deletePostedAnnouncement")
    public @ResponseBody
    void deletePostedAnnouncement(@RequestParam("date") @DateTimeFormat(pattern = "dd-MM-yyyy") Date date
            , @RequestParam String title, @RequestParam Integer userID, @RequestParam("courseId") String courseId){
        announcementService.deleteAnnouncement(date, title, userID, courseId);
    }
}
