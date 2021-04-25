package com.example.attendance.Service;

import com.example.attendance.Models.Announcement;
import com.example.attendance.Models.Course;
import com.example.attendance.Models.TeachingAssistant;
import com.example.attendance.Repository.AnnouncementRepository;

import com.example.attendance.Repository.CourseRepository;
import com.example.attendance.Repository.TeachingAssistantRepository;
import com.example.attendance.Repository.UserCourseRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Date;

@Service
public class AnnouncementService {
    @Autowired
    private TeachingAssistantRepository teachingAssistantRepository;

    @Autowired
    private CourseRepository courseRepository;

    @Autowired
    private AnnouncementRepository announcementRepository;

    //adds an announcement to the database according to user input
    public void postAnnouncement(Integer userId, String courseId, Date date, String title, String post){
        TeachingAssistant ta = teachingAssistantRepository.findById(userId).get();

        Course course = courseRepository.findById(courseId).get();

        Announcement announcement = new Announcement(ta,course,date,title,post);

        announcementRepository.save(announcement);
    }

    //deletes an announcement from the database according to its date,title, associated course
    // and the id of the user
    //that posted it, will be used for the recycler view

    public void deleteAnnouncement(Date date, String title, Integer userID,String courseId){
        announcementRepository.deleteByDateAndTitleAndPostedByAndCourse(date, title, userID,courseId);
    }
}
