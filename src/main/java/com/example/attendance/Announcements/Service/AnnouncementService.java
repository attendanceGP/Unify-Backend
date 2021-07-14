package com.example.attendance.Announcements.Service;

import com.example.attendance.Announcements.Model.Announcement;
import com.example.attendance.Announcements.Repository.AnnouncementRepository;
import com.example.attendance.FirebaseMessaging.FirebaseMessagingService;
import com.example.attendance.Models.*;

import com.example.attendance.Repository.CourseRepository;
import com.example.attendance.Repository.TeachingAssistantRepository;
import com.example.attendance.Repository.UserCourseRepository;
import com.google.firebase.messaging.FirebaseMessagingException;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.*;

@Service
public class AnnouncementService {
    @Autowired
    private TeachingAssistantRepository teachingAssistantRepository;

    @Autowired
    private CourseRepository courseRepository;

    @Autowired
    private UserCourseRepository userCourseRepository;

    @Autowired
    private AnnouncementRepository announcementRepository;

    @Autowired
    private FirebaseMessagingService firebaseMessagingService;

    //adds an announcement to the database according to user input
    public void postAnnouncement(Integer userId, String courseId, Date postedDate, String title,
            String announcementGroups, String post){
        TeachingAssistant ta = teachingAssistantRepository.findById(userId).get();

        Course course = courseRepository.findById(courseId).get();

        Announcement announcement = new Announcement(ta,course,postedDate,title,announcementGroups,post);

        announcementRepository.save(announcement);

        try {
            firebaseMessagingService.sendNotification("New announcement",
                    courseId + ": " + title, courseId);
        } catch (FirebaseMessagingException e) {
            e.printStackTrace();
        }
    }

    //deletes an announcement from the database according to its date,title, associated course
    // and the id of the user
    //that posted it, will be used for the recycler view

    public void deleteAnnouncement(Integer id){
        announcementRepository.deleteById(id);
    }

    public JSONArray getTaAnnouncements(Integer userId){
        JSONArray jsonArray = getJsonFromAnnouncements((announcementRepository.getTaAnnouncements(userId)));
        return sortJsonArrayByDate(jsonArray);
    }

    public JSONArray getStudentAnnouncements(Integer userId){
        JSONArray jsonArray = getJsonFromAnnouncements((announcementRepository.getStudentAnnouncements(userId)));
        JSONArray groupFilteredArray=filterJsonArrayByGroup(jsonArray,userId);
        return sortJsonArrayByDate(groupFilteredArray);
    }

    private JSONArray getJsonFromAnnouncements(List<Announcement> announcements){
        JSONArray jsonArray = new JSONArray();

        for(Announcement announcement: announcements){
            JSONObject jsonObject = new JSONObject();

            String postedByType = null;
            //String []class_string = announcement.getPostedBy().getClass().toString().split("\\.");
            if(announcement.getPostedBy() instanceof TeachingAssistant){
                postedByType = "TA ";
            }
            else if(announcement.getPostedBy() instanceof TeachingAssistant){
                postedByType = "DR ";
            }

            jsonObject.put("id", announcement.getId());
            jsonObject.put("title", announcement.getTitle());
            jsonObject.put("courseId", announcement.getCourse().getCourseCode());
            jsonObject.put("postedDate", announcement.getPostedDate());
            jsonObject.put("postedBy",postedByType + announcement.getPostedBy().getName());
            jsonObject.put("description", announcement.getPost());
            jsonObject.put("announcementGroups",announcement.getAnnouncementGroups());

            jsonArray.put(jsonObject);
        }

        return jsonArray;
    }

    public JSONArray sortJsonArrayByDate(JSONArray announcements){
        JSONArray sortedAnnouncements = new JSONArray();
        List list = new ArrayList();
        for(int i = 0; i < announcements.length(); i++) {
            list.add(announcements.getJSONObject(i));
        }

        Collections.sort(list, new Comparator<JSONObject>() {
            private static final String KEY_NAME = "postedDate";
            @Override
            public int compare(JSONObject a, JSONObject b) {
                Date d1 = new Date();
                Date d2 = new Date();
                try {
                    d1 = (Date)a.get(KEY_NAME);
                    d2 = (Date)b.get(KEY_NAME);
                } catch(JSONException e) {
                    e.printStackTrace();
                }
                return d2.compareTo(d1);
            }
        });
        for(int i = 0; i < announcements.length(); i++) {
            sortedAnnouncements.put(list.get(i));
        }
        return sortedAnnouncements;
    }

    public JSONArray filterJsonArrayByGroup(JSONArray announcements,int userId){
        List<UserCourse> studentCourses = userCourseRepository.findUserCourseByUserId(userId);

        for(int i=0; i<announcements.length();i++) {
            JSONObject x;
            x = announcements.getJSONObject(i);
            String announcementGroups =(String) x.get("announcementGroups");
            String crsCode =(String) x.get("courseId");

            for(int j=0;j<studentCourses.size();j++){
                if(studentCourses.get(j).getCourse().getCourseCode().equals(crsCode)){
                    if(announcementGroups.equals("all") || announcementGroups.equals("All")){
                        break;
                    }
                    else if (!announcementGroups.contains(studentCourses.get(j).getUserGroup())) {
                        announcements.remove(i);
                    }
                    break;
                }
            }

        }
        return announcements;
    }
}
