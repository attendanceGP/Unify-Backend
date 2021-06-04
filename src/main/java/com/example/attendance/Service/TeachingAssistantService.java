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
                Attendance studentAttendance = new Attendance(attendingStudentIds.get(i), course, userGroup, date, true);

                attendanceRepository.save(studentAttendance);
            }
        }
        
        else{
            for(int i=0;i<groups.length; i++){
                Attendance multiAttendance = new Attendance(ta, course, groups[i], date, false);

                attendanceRepository.save(multiAttendance);

                List<User> attendingStudentIds = getRegisteredUserIds(groups[i], courseId);

                for (int j = 0; j < attendingStudentIds.size(); j++) {
                    Attendance studentAttendance = new Attendance(attendingStudentIds.get(j), course, groups[i], date, true);

                    attendanceRepository.save(studentAttendance);
                }
            }
        }
    }

    public absence[] getAbsence(Integer StudentID){

        /*List<UserCourse> StudentCourseGroup = userCourseRepository.findByStudentIDAndCourseID(StudentID, CourseID);

        if (StudentCourseGroup.isEmpty()){
            return "error: wrong data entered";
        }
        String group= StudentCourseGroup.get(0).getUserGroup();

        List<Attendance> StudentList = attendanceRepository.findByUserIDAndCourseIDAndGroup(StudentID, CourseID, group);
        //System.out.println("number of absences : " + (TAList.size()-StudentList.size()));


        ArrayList<Date> stdDates = new ArrayList<>();

        for(Attendance std: StudentList){
            if (std.isAbsent()){
                stdDates.add(std.getDate());
            }

        }
        return stdDates.toString();*/
        String[] courses=studentResource.getStudentCourses(StudentID);
        absence[] toBeReturned = new absence[courses.length];
        Course[] coursesCodes=new Course[courses.length];

        for (int i = 0; i < courses.length; i++) {
                    coursesCodes[i]=courseRepository.getCourseByCourseName(courses[i]);
        }
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
    public Recent[] getRecent(int UserId){
        List<Attendance> attendances = attendanceRepository.findByUserIDAndAbsent(UserId,true);
        Recent [] recents = new Recent[attendances.size()];
        for (int i = 0; i < recents.length; i++) {
            String taName = null;
            Date date = attendances.get(i).getDate();
            Course course = attendances.get(i).getCourse();
            String userGroup = attendances.get(i).getUserGroup();
            List<Attendance>toGetTA = attendanceRepository.findByCourseAndDateAndUserGroup(course,date,userGroup);
            for (int j = 0; j < toGetTA.size(); j++) {
                User user = toGetTA.get(j).getUser();
                if (user instanceof TeachingAssistant){
                    taName = user.getName();
                    break;
                }
            }
            recents[i]=new Recent(course.getCourseCode(),taName,date.toString(),attendances.get(i).isPenalty());
        }

        return recents;
    }

    public TaRecent[] getRecentTA(int id){
        List<Attendance> attendances = attendanceRepository.findByUserID(id);
        TaRecent[] recents = new TaRecent[attendances.size()];
        for (int i = 0; i < attendances.size(); i++) {
            ArrayList<User> toBeChecked=new ArrayList<>();
            Course course = attendances.get(i).getCourse();
            Date date = attendances.get(i).getDate();
            String userGroup = attendances.get(i).getUserGroup();
            List<Attendance>todayAttendance = attendanceRepository.findByCourseAndDateAndUserGroup(course,date,userGroup);
            int attendedCounter = 0;
            int absenceCounter=0;
            for (int j = 0; j < todayAttendance.size(); j++) {
                if (todayAttendance.get(j).getUser() instanceof TeachingAssistant)continue;
                if (toBeChecked.contains(todayAttendance.get(j).getUser())) continue;
                if (!(todayAttendance.get(j).isAbsent()) && todayAttendance.get(j).getId() > attendances.get(i).getId()){
                    attendedCounter++;
                    toBeChecked.add(todayAttendance.get(j).getUser());
                }
                else if (todayAttendance.get(j).isAbsent() && todayAttendance.get(j).getId() > attendances.get(i).getId()){
                    absenceCounter++;
                    toBeChecked.add(todayAttendance.get(j).getUser());
                }
            }
            recents[i] = new TaRecent(course.getCourseCode(),date.toString(),attendedCounter,absenceCounter);
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
}
