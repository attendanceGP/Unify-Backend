package com.example.attendance.Announcements.Resource;

import com.example.attendance.Announcements.Service.AnnouncementService;
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
    Integer postAnnouncement(@RequestParam Integer userId, @RequestParam String courseId, @RequestParam("postedDate") @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss") Date postedDate,
                        @RequestParam String title,@RequestParam String announcementGroups, @RequestParam String post){
        announcementService.postAnnouncement(userId,courseId,postedDate,title,announcementGroups,post);
        return 0;

    }

    @DeleteMapping( path = "deletePostedAnnouncement")
    public @ResponseBody
    Integer deletePostedAnnouncement(@RequestParam Integer id){
        announcementService.deleteAnnouncement(id);
        return 0;
    }

    @GetMapping( path = "getTaAnnouncements")
    public @ResponseBody
    String getTaAnnouncements(@RequestParam Integer userId){
        return announcementService.getTaAnnouncements(userId).toString();
    }

    @GetMapping( path = "getStudentAnnouncements")
    public @ResponseBody
    String getStudentAnnouncements(@RequestParam Integer userId){
        return announcementService.getStudentAnnouncements(userId).toString();
    }

}

