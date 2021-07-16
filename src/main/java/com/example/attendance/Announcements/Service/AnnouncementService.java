package com.example.attendance.Announcements.Service;

import com.example.attendance.Announcements.Model.Announcement;
import com.example.attendance.Announcements.Repository.AnnouncementRepository;
import com.example.attendance.Course.Model.Course;
import com.example.attendance.FirebaseMessaging.FirebaseMessagingService;

import com.example.attendance.Course.Repository.CourseRepository;
import com.example.attendance.User.Models.Professor;
import com.example.attendance.User.Repository.TeachingAssistantRepository;
import com.example.attendance.Course.Repository.UserCourseRepository;
import com.example.attendance.User.Models.TeachingAssistant;
import com.example.attendance.Course.Model.UserCourse;
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

    /**
     *
     * void postAnnouncement(Integer userId, String courseId, Date postedDate, String title,
     *             String announcementGroups, String post)
     *
     * Summary of the getJsonFromAnnouncements function:
     *
     *    puts an announcement in the database and sends out notifications
     *
     * Parameters   : userId: the id of the user making the announcements
     *                courseId: the course which the announcement is for
     *                postedDate: the date in which the announcement was made
     *                title: the announcement title
     *                announcementGroups: groups concerned with said announcement
     *                post: the information of the announcement
     *
     * Return Value : nothing -- Note:adds announcement to the database.
     *
     * Description:
     *
     *    this functions takes information about an announcement from a user and inserts that announcement
     *    into the database and sends out notifications for all concerned parties.
     *
     */
    public void postAnnouncement(Integer userId, String courseId, Date postedDate, String title,
            String announcementGroups, String post){
        TeachingAssistant ta = teachingAssistantRepository.findById(userId).get();

        Course course = courseRepository.findById(courseId).get();

        Announcement announcement = new Announcement(ta,course,postedDate,title,announcementGroups,post);

        announcementRepository.save(announcement);

        String[] groups = announcementGroups.split(" ");


        if(groups.length == 1 && groups[0].equalsIgnoreCase("all")){
            try {
                firebaseMessagingService.sendNotification("New announcement",
                        courseId + ": " + title, courseId);
            } catch (FirebaseMessagingException e) {
                e.printStackTrace();
            }
        }else{
            for(String group: groups){
                try {
                    firebaseMessagingService.sendNotification("New announcement",
                            courseId + ": " + title, courseId + "-" + group);
                } catch (FirebaseMessagingException e) {
                    e.printStackTrace();
                }
            }
        }
    }

    //deletes an announcement from the database
    public void deleteAnnouncement(Integer id){
        announcementRepository.deleteById(id);
    }

    //get all the announcements in descending order that a TA has posted
    public JSONArray getTaAnnouncements(Integer userId){
        JSONArray jsonArray = getJsonFromAnnouncements((announcementRepository.getTaAnnouncements(userId)));

        return sortJsonArrayByDate(jsonArray);
    }

    //gets all announcements that concern a student in descending order
    public JSONArray getStudentAnnouncements(Integer userId){
        JSONArray jsonArray = getJsonFromAnnouncements((announcementRepository.getStudentAnnouncements(userId)));

        JSONArray groupFilteredArray=filterJsonArrayByGroup(jsonArray,userId);

        return sortJsonArrayByDate(groupFilteredArray);
    }


    /**
     *
     * JSONArray getJsonFromAnnouncements(List<Announcement> announcements)
     *
     * Summary of the getJsonFromAnnouncements function:
     *
     *    transforms announcement list from db to a json array
     *
     * Parameters   : announcements: a list of announcement objects
     *
     * Return Value : jsonArray -- Note:array contains json strings parsed from the announcements.
     *
     * Description:
     *
     *    this functions takes a list announcements from the database and parses them into
     *    json strings and puts them inside a json array for use in our front end application.
     *
     */
    private JSONArray getJsonFromAnnouncements(List<Announcement> announcements){
        JSONArray jsonArray = new JSONArray();

        for(Announcement announcement: announcements){
            JSONObject jsonObject = new JSONObject();

            String postedByType = null;

            //checking which type of user is making the announcement and
            if(announcement.getPostedBy() instanceof TeachingAssistant){
                postedByType = "TA ";
            }
            else if(announcement.getPostedBy() instanceof Professor){
                postedByType = "DR ";
            }

            //putting all the required data of each individual announcement inside the json object
            jsonObject.put("id", announcement.getId());
            jsonObject.put("title", announcement.getTitle());
            jsonObject.put("courseId", announcement.getCourse().getCourseCode());
            jsonObject.put("postedDate", announcement.getPostedDate());
            //adding their type before their name
            jsonObject.put("postedBy",postedByType + announcement.getPostedBy().getName());
            jsonObject.put("description", announcement.getPost());
            jsonObject.put("announcementGroups",announcement.getAnnouncementGroups());

            jsonArray.put(jsonObject);
        }

        return jsonArray;
    }


    /**
     *
     * JSONArray sortJsonArrayByDate(JSONArray announcements)
     *
     * Summary of the sortJsonArrayByDate function:
     *
     *    sorts the announcement json array in descending order according to date
     *
     * Parameters   : announcements: a json array of objects(objects are parsed announcements from the
     *                  getJsonFromAnnouncements function)
     *
     * Return Value : sortedAnnouncements -- Note:sorted json array of announcements.
     *
     * Description:
     *
     *    this functions takes a json array of json objects and sorts them in a descending order
     *    according to date by using a comparator to compare between dates inside the json objects.
     *
     */
    public JSONArray sortJsonArrayByDate(JSONArray announcements){
        JSONArray sortedAnnouncements = new JSONArray();

        List list = new ArrayList();

        for(int i = 0; i < announcements.length(); i++) {
            list.add(announcements.getJSONObject(i));
        }

        //using the collections.sort function by using a custom comparator for the dates inside the json
        //object as the basis of the sort
        Collections.sort(list, new Comparator<JSONObject>() {
            private static final String KEY_NAME = "postedDate";

            @Override
            public int compare(JSONObject a, JSONObject b) {
                Date d1 = new Date();
                Date d2 = new Date();
                try {
                    //getting the date inside each json object
                    d1 = (Date)a.get(KEY_NAME);
                    d2 = (Date)b.get(KEY_NAME);
                } catch(JSONException e) {
                    e.printStackTrace();
                }
                //the reverse will sort in ascending order
                return d2.compareTo(d1);
            }
        });

        //we put the list of the sorted announcements into the json array
        for(int i = 0; i < announcements.length(); i++) {
            sortedAnnouncements.put(list.get(i));
        }
        return sortedAnnouncements;
    }


    /**
     *
     * JSONArray filterJsonArrayByGroup(JSONArray announcements,int userId)
     *
     * Summary of the sortJsonArrayByDate function:
     *
     *    removes the groups in which the student is not in from the announcements related to the courses
     *    they're in.
     *
     * Parameters   : announcements: a json array of objects(objects are parsed announcements from the
     *                  getJsonFromAnnouncements function)
     *                userId: the id of the student
     *
     * Return Value : announcements -- Note:json array of the announcements in the courses and groups
     *                 the student is in.
     *
     * Description:
     *
     *    this functions takes a json array of json objects removes the elements that don't correspond
     *    to a student's userCourse date, meaning it only has the announcement where the
     *    student is enrolled in a specific group in a specific course.
     *
     */
    public JSONArray filterJsonArrayByGroup(JSONArray announcements,int userId){
        List<UserCourse> studentCourses = userCourseRepository.findUserCourseByUserId(userId);

        for(int i=0; i<announcements.length();i++) {

            JSONObject x;
            x = announcements.getJSONObject(i);

            String announcementGroups =(String) x.get("announcementGroups");
            String crsCode =(String) x.get("courseId");

            for(int j=0;j<studentCourses.size();j++){
                //checks if the student is enrolled in a course that has a course code
                //in the current announcement
                if(studentCourses.get(j).getCourse().getCourseCode().equals(crsCode)){
                    //if the group in the announcement is all then we don't remove it
                    //as this announcement is for all students
                    if(announcementGroups.equals("all") || announcementGroups.equals("All")){
                        break;
                    }
                    //if the student's group in said course is not the same as the group for the
                    //announcement then we remove the announcement from the json array
                    //since it does not concern that student
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
