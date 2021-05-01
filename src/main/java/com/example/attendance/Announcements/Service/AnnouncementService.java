package com.example.attendance.Announcements.Service;

import com.example.attendance.Announcements.Model.Announcement;
import com.example.attendance.Announcements.Repository.AnnouncementRepository;
import com.example.attendance.Models.Course;
import com.example.attendance.Models.TeachingAssistant;

import com.example.attendance.Repository.CourseRepository;
import com.example.attendance.Repository.TeachingAssistantRepository;
import org.json.JSONArray;
import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;

@Service
public class AnnouncementService {
    @Autowired
    private TeachingAssistantRepository teachingAssistantRepository;

    @Autowired
    private CourseRepository courseRepository;

    @Autowired
    private AnnouncementRepository announcementRepository;

    //adds an announcement to the database according to user input
    public void postAnnouncement(Integer userId, String courseId, Date postedDate, String title, String post){
        TeachingAssistant ta = teachingAssistantRepository.findById(userId).get();

        Course course = courseRepository.findById(courseId).get();

        Announcement announcement = new Announcement(ta,course,postedDate,title,post);

        announcementRepository.save(announcement);
    }

    //deletes an announcement from the database according to its date,title, associated course
    // and the id of the user
    //that posted it, will be used for the recycler view

    public void deleteAnnouncement(Date date, String title, Integer userId,String courseId){
        announcementRepository.deleteByDateAndTitleAndPostedByAndCourse(date, title, userId,courseId);
    }

    public JSONArray getTaAnnouncements(Integer userId){
        JSONArray jsonArray = getJsonFromAnnouncements((announcementRepository.getTaAnnouncements(userId)));
        return jsonArray;
    }

    public JSONArray getStudentAnnouncements(Integer userId){
        JSONArray jsonArray = getJsonFromAnnouncements((announcementRepository.getStudentAnnouncements(userId)));
        return jsonArray;
    }

    public JSONArray getSortedStudentAnnouncementsByCourse(Integer userId,String[] courseIds){
        JSONArray jsonArray = getJsonFromAnnouncements((announcementRepository.getSortedStudentAnnouncementsByCourseCode
                (userId,courseIds[0])));
        for(int i=1; i<courseIds.length;i++){
            for(int j=0;
                j<getJsonFromAnnouncements((announcementRepository.getSortedStudentAnnouncementsByCourseCode
                    (userId,courseIds[0]))).length();j++){

                jsonArray.put(getJsonFromAnnouncements((announcementRepository.getSortedStudentAnnouncementsByCourseCode
                        (userId,courseIds[i]))).getJSONObject(j));

            }
        }
        
        return jsonArray;
    }

    private JSONArray getJsonFromAnnouncements(List<Announcement> announcements){
        JSONArray jsonArray = new JSONArray();

        for(Announcement announcement: announcements){
            JSONObject jsonObject = new JSONObject();

            jsonObject.put("id", announcement.getId());
            jsonObject.put("title", announcement.getTitle());
            jsonObject.put("courseCode", announcement.getCourse().getCourseCode());
            jsonObject.put("postedDate", announcement.getPostedDate());
            jsonObject.put("postedBy", announcement.getPostedBy().getName());
            jsonObject.put("post", announcement.getPost());

            jsonArray.put(jsonObject);
        }

        return jsonArray;
    }
}
