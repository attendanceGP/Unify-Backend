package com.example.attendance.Service;

import com.example.attendance.Models.*;
import com.example.attendance.Repository.AttendanceRepository;
import com.example.attendance.Repository.CourseRepository;
import com.example.attendance.Repository.UserCourseRepository;
import com.example.attendance.Repository.TeachingAssistantRepository;
import com.example.attendance.Resource.StudentResource;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Optional;
import com.example.attendance.Absence.*;
@Service
public class TeachingAssistantService {
    @Autowired
    private TeachingAssistantRepository teachingAssistantRepository;

    @Autowired
    private CourseRepository courseRepository;

    @Autowired
    private AttendanceRepository attendanceRepository;

    @Autowired
    private UserCourseRepository userCourseRepository;

    @Autowired
    private StudentResource studentResource;
    public Optional<TeachingAssistant> findTAById(Integer id){
        return teachingAssistantRepository.findById(id);
    }

    public List<User> getRegisteredUserIds(String userGroup, String courseId){
        List<UserCourse> UCs = userCourseRepository.findByCourseIdAndUserGroup(courseId,userGroup);
        List<User> associatedIds = new ArrayList<User>();
        for(int i=0; i<UCs.size(); i++){
            associatedIds.add(UCs.get(i).getUser());
        }
        return associatedIds;
    }

    //when posting attendance, for the TA if the absent boolean is false that means the TA is taking attendance
    //and students can attend, otherwise if false no student can attend the course as the attendance is closed
    //for students if absent is true this means they are absent if false this means they have attended said
    //course in said date in said group
    public void postAttendance(Date date, String userGroup, String courseId, Integer userId){
        TeachingAssistant ta = teachingAssistantRepository.findById(userId).get();

        Course course = courseRepository.findById(courseId).get();

        String[] groups = userGroup.split(" ");

        if(groups.length == 1) {
            Attendance attendance = new Attendance(ta, course, userGroup, date, false);

            attendanceRepository.save(attendance);

            List<User> attendingStudentIds = getRegisteredUserIds(userGroup, courseId);
            for (int i = 0; i < attendingStudentIds.size(); i++) {
                if(!(attendingStudentIds.get(i) instanceof Professor) &
                        !(attendingStudentIds.get(i) instanceof TeachingAssistant)){
                    Attendance studentAttendance = new Attendance(attendingStudentIds.get(i), course, userGroup, date, true);

                    attendanceRepository.save(studentAttendance);
                }
            }
        }

        else{
            for(int i=0;i<groups.length; i++){
                Attendance multiAttendance = new Attendance(ta, course, groups[i], date, false);

                attendanceRepository.save(multiAttendance);

                List<User> attendingStudentIds = getRegisteredUserIds(groups[i], courseId);

                for (int j = 0; j < attendingStudentIds.size(); j++) {
                    String []class_string = attendingStudentIds.get(j).getClass().toString().split("\\.");
                    if(!(class_string[(class_string.length)-1].equals("TeachingAssistant")) &
                            !(class_string[(class_string.length)-1].equals("Professor"))) {
                        Attendance studentAttendance = new Attendance(attendingStudentIds.get(j), course, groups[i], date, true);

                        attendanceRepository.save(studentAttendance);
                    }
                }
            }
        }
    }
    // get the overview absence of the student that returns the course code, number of absence and num of pens
    public absence[] getAbsence(Integer StudentID){
        // get the students data
        String[] courses=studentResource.getStudentCourses(StudentID);
        absence[] toBeReturned = new absence[courses.length];
        Course[] coursesCodes=new Course[courses.length];
        for (int i = 0; i < courses.length; i++) {
                    coursesCodes[i]=courseRepository.getCourseByCourseName(courses[i]);
        }
        // store the data of every missed lab/section
        for (int i = 0; i < coursesCodes.length; i++) {
            List<Attendance> absenceList = attendanceRepository.findByUserIDAndCourseIDAndAbsent(StudentID,coursesCodes[i].getCourseCode(),true);
            toBeReturned[i] = new absence();
            toBeReturned[i].setCourseCode(coursesCodes[i].getCourseCode());
            toBeReturned[i].setAbsenceCounter(absenceList.size());
            int pen=0;
            for (int j = 0; j <absenceList.size() ; j++) {
                if (absenceList.get(j).isPenalty())pen++;
            }
            toBeReturned[i].setPenCounter(pen);
        }

        return toBeReturned;
    }

    // get the students recent absences that contains the courseCode, ta name , date and penalty
    public Recent[] getRecent(int UserId){
        List<Attendance> attendances = attendanceRepository.findByUserIDAndAbsent(UserId,true);
        Recent [] recents = new Recent[attendances.size()];
        for (int i = 0; i < recents.length; i++) {
            String taName = null;
            Date date = attendances.get(i).getDate();
            Course course = attendances.get(i).getCourse();
            String userGroup = attendances.get(i).getUserGroup();
            //get all the attendances rows that contain the date,course and userGroup to get the ta name
            List<Attendance>toGetTA = attendanceRepository.findByCourseAndDateAndUserGroup(course,date,userGroup);
            for (int j = 0; j < toGetTA.size(); j++) {
                User user = toGetTA.get(j).getUser();
                if (user instanceof TeachingAssistant){
                    taName = user.getName();
                    break;
                }
            }
            // store backward to appear correctly in the app
            recents[attendances.size()-1-i]=new Recent(course.getCourseCode(),taName,date.toString(),attendances.get(i).isPenalty());
        }

        return recents;
    }
    //function that gets all the attendances that the ta started with the dates and the number of absences / attendances
    public TaRecent[] getRecentTA(int id){
        List<Attendance> attendances = attendanceRepository.findByUserID(id);
        TaRecent[] recents = new TaRecent[attendances.size()];
        // loop that get all the student that attended/didn't attend.
        for (int i = 0; i < attendances.size(); i++) {
            ArrayList<User> toBeChecked=new ArrayList<>();
            Course course = attendances.get(i).getCourse();
            Date date = attendances.get(i).getDate();
            String userGroup = attendances.get(i).getUserGroup();
            // get the attendances of the date above, course and group
            List<Attendance>todayAttendance = attendanceRepository.findByCourseAndDateAndUserGroup(course,date,userGroup);
            int attendedCounter = 0;
            int absenceCounter=0;
            for (int j = 0; j < todayAttendance.size(); j++) {
                if (todayAttendance.get(j).getUser() instanceof TeachingAssistant)continue;
                //to avoid wrong readings if contains many attendances of the same things in the same day
                if (toBeChecked.contains(todayAttendance.get(j).getUser())) continue;
                //check if the student was not absent
                if (!(todayAttendance.get(j).isAbsent()) && todayAttendance.get(j).getId() > attendances.get(i).getId()){
                    attendedCounter++;
                    toBeChecked.add(todayAttendance.get(j).getUser());
                }
                // check if student was absent
                else if (todayAttendance.get(j).isAbsent() && todayAttendance.get(j).getId() > attendances.get(i).getId()){
                    absenceCounter++;
                    toBeChecked.add(todayAttendance.get(j).getUser());
                }
            }
            // add the data from the most recent to the oldest
            recents[attendances.size()-1-i] = new TaRecent(course.getCourseCode(),date.toString(),attendedCounter,absenceCounter,attendances.get(i).getUserGroup());
        }
        return recents;
    }


    public List<String> getRegisteredCourses(Integer userID){
        return userCourseRepository.findCourseCodeById(userID);
        }

    public void closeTaAttendance(Date date, String userGroup, String courseId, Integer userId) {
        String[] groups = userGroup.split(" ");
        if(groups.length == 1) {
            attendanceRepository.UpdateStudentAbsence(date,userGroup,courseId,userId,true, false);
        }
        else{
            for(int i=0;i<groups.length; i++){
                attendanceRepository.UpdateStudentAbsence(date,groups[i],courseId,userId,true, false );
            }
        }
    }
    public void updateTALocation(int id , double longitude,double latitude){
        TeachingAssistant ta = teachingAssistantRepository.findById(id).get();
        ta.setLatitude(latitude);
        ta.setLongitude(longitude);
        teachingAssistantRepository.save(ta);

    }
    public TeachingAssistant getTa(int id){
        return teachingAssistantRepository.findById(id).get();
    }

    public List<String> attendanceGroupsAlreadyExist(Date date,Integer userId,String courseId,String groups){
        List<Attendance> TaAttendanceList = attendanceRepository.findByCourseAndDate(date,courseId);
        List<String> existingGroups = new ArrayList<>();

        String[] myGroups = groups.split(" ");

            for(int i=0;i<TaAttendanceList.size();i++) {
                for(int j=0;j<myGroups.length;j++){
                    if(TaAttendanceList.get(i).getUserGroup().contains(myGroups[j])
                    && (!existingGroups.contains(myGroups[j]))){
                        existingGroups.add(myGroups[j]);
                    }
                    else{
                        continue;
                    }
                }
            }

        return existingGroups;
    }
}
