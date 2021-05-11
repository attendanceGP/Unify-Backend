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

//    public void addTeachingAssistant(TeachingAssistant TA){
//        teachingAssistantRepository.save(TA);
//        //Date date = new Date(2020,10,20);
//        //Attendance attendance = new Attendance(student, course, "G1", date);
//        //attendanceRepository.save(attendance);
//    }

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

    public List<Attendance> getAbsence(Integer StudentID, String CourseID){

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
        Course[] coursesCodes=new Course[courses.length];
        int [][]toBeReturned=new int[courses.length][2];
        for (int i = 0; i < courses.length; i++) {
                    coursesCodes[i]=courseRepository.getCourseByCourseName(courses[i]);
        }
        for (int i = 0; i < coursesCodes.length; i++) {
            List<Attendance> absence = attendanceRepository.findByUserIDAndCourseIDAndAbsent(StudentID,coursesCodes[i].getCourseCode(),true);
            int absenceCounter = absence.size();
            int pen=0;
            for (int j = 0; j <absence.size() ; j++) {
                if (absence.get(j).isPenalty())pen++;
            }
            toBeReturned[i][0]=absenceCounter;
            toBeReturned[i][1]=pen;
        }

        return null;
    }

    public List<String> getRegisteredCourses(Integer userID){
        return userCourseRepository.findCourseCodeById(userID);
        }

    public void closeTaAttendance(Date date, String userGroup, String courseId, Integer userId) {
        String[] groups = userGroup.split(" ");
        if(groups.length == 1) {
            attendanceRepository.UpdateStudentAbsence(date,userGroup,courseId,userId,true);
        }
        else{
            for(int i=0;i<groups.length; i++){
                attendanceRepository.UpdateStudentAbsence(date,groups[i],courseId,userId,true);
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
