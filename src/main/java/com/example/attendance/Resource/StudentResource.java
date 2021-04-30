package com.example.attendance.Resource;

import com.example.attendance.Containers.StudentCourseContainer;
import com.example.attendance.Models.*;

import com.example.attendance.Repository.StudentRepository;
import com.example.attendance.Service.CourseService;
import com.example.attendance.Service.StudentCourseService;
import com.example.attendance.Service.StudentService;
import com.example.attendance.Service.*;
import org.json.JSONObject;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.Date;
import java.util.List;
import java.util.Optional;

@Controller
@RequestMapping(path = "/student")
public class StudentResource {
    @Autowired
    private StudentService studentService;

    @Autowired
    private CourseService courseService;

    @Autowired
    private StudentCourseService studentCourseService;

    @Autowired
    private StudentRepository studentRepository;


    @Autowired
    private AttendanceService attendanceService;

    @Autowired
    private TeachingAssistantService teachingAssistantService;


/*
    @PostMapping( value = "/add", consumes = "application/json")
    public @ResponseBody void add(@Valid @RequestBody Student student){
        studentService.addStudent(student);
    }

    @PostMapping(path = "/test") // NOT ACTUAL FUNCTION
                                // DO NOT COPY INTO OTHER CLASSES
    public @ResponseBody void test()
    {
//        Student student = new Student(20170171, "Ali Samy", 4, 4.0);
//
//        Course course = new Course("CS213", "CS");
//
//        UserCourse userCourse = new UserCourse(student, course, "G1");
//
//        studentService.addStudent(student);
//        courseService.addCourse(course);
//        studentCourseService.addStudentCourse(userCourse);

//        StudentCourseContainer studentCourseContainer = studentService.getStudentCourses("x", "x");
//        System.out.println(studentCourseContainer.getStudent().getGpa());
//        System.out.println(studentCourseContainer.getCourseList()[1].getCourseCode());


//        Student student = new Student(20170171, "Ali Samy", "alialfie", "alialfie", 4, 4.0);
//        studentService.addStudent(student);
//        Student student1 = new Student(20170800, "youssef", "youssef", "youssef", 4, 4.0);
//        studentService.addStudent(student1);
//        Student student2 = new Student(20170399, "Lamya Raed", "lam", "lam", 4, 4.0);
//        studentService.addStudent(student2);
//        Student student3 = new Student(20170200, "balf", "balf", "balf", 4, 4.0);
//        studentService.addStudent(student3);
//        Student student4 = new Student(20170180, "tom hanks", "tom", "tom", 4, 4.0);
//        studentService.addStudent(student4);
//        Student student5 = new Student(20170398, "alexa", "alexa", "alexa", 4, 4.0);
//        studentService.addStudent(student5);
//        Student student6 = new Student(20170172, "lulu", "lulu", "lulu", 4, 4.0);
//        studentService.addStudent(student6);
//        Student student7 = new Student(20170600, "alaa", "alaa", "alaa", 4, 4.0);
//        studentService.addStudent(student7);
//        Student student8 = new Student(20170001, "chrissy", "chrissy", "chrissy", 4, 4.0);
//        studentService.addStudent(student8);
//        Student student9 = new Student(20170201, "cold play", "cold", "play", 4, 4.0);
//        studentService.addStudent(student9);
//        Student student10 = new Student(20170181, "adele", "adele", "adele", 4, 4.0);
//        studentService.addStudent(student10);
//        Student student11 = new Student(20170391, "sawsan", "sawsan", "sawsan", 4, 4.0);
//        studentService.addStudent(student11);
//        Student student12 = new Student(20170002, "hozier", "hozier", "hozier", 4, 4.0);
//        studentService.addStudent(student12);
//        Student student13 = new Student(20170801, "omar", "omar", "omar", 4, 4.0);
//        studentService.addStudent(student13);
//        Student student14 = new Student(20170397, "zozza", "zoz", "zoz", 4, 4.0);
//        studentService.addStudent(student14);
//        Student student15 = new Student(20170206, "rayn", "rayn", "ryonalds", 4, 4.0);
//        studentService.addStudent(student15);
//        Student student16 = new Student(20170186, "cafters", "c", "afters", 4, 4.0);
//        studentService.addStudent(student16);
//        Student student17 = new Student(20170394, "kira", "kira", "kira", 4, 4.0);
//        studentService.addStudent(student17);
//        Student student18 = new Student(20170174, "levi", "levi", "levi", 4, 4.0);
//        studentService.addStudent(student18);
//        Student student19 = new Student(20170604, "rou","rou", "rou", 4, 4.0);
//        studentService.addStudent(student19);
//        Student student20 = new Student(20170004, "joey", "joey", "joey", 4, 4.0);
//        studentService.addStudent(student20);
//        Student student21 = new Student(20170203, "jesica", "jiseca", "jiseca", 4, 4.0);
//        studentService.addStudent(student21);
//        Student student22 = new Student(20170183, "lana", "lana", "lana", 4, 4.0);
//        studentService.addStudent(student22);
//        Student student23 = new Student(20170392, "jaymes", "jaymes", "jaymes", 4, 4.0);
//        studentService.addStudent(student23);
//
//        Course course = new Course("CS464", "Genetic Algorithms");
//        courseService.addCourse(course);
//
//        UserCourse userCourse = new UserCourse(student2, course, "G2");
//        studentCourseService.addStudentCourse(userCourse);
//
        Course course = new Course("CS467", "Machine Learning");
        courseService.addCourse(course);
//
//        userCourse = new UserCourse(student, course, "G1");
//        studentCourseService.addStudentCourse(userCourse);
//        userCourse = new UserCourse(student1, course, "G1");
//        studentCourseService.addStudentCourse(userCourse);
//        userCourse = new UserCourse(student2, course, "G1");
//        studentCourseService.addStudentCourse(userCourse);
//        userCourse = new UserCourse(student3, course, "G1");
//        studentCourseService.addStudentCourse(userCourse);
//        userCourse = new UserCourse(student4, course, "G1");
//        studentCourseService.addStudentCourse(userCourse);
//        userCourse = new UserCourse(student5, course, "G1");
//        studentCourseService.addStudentCourse(userCourse);
//        userCourse = new UserCourse(student6, course, "G1");
//        studentCourseService.addStudentCourse(userCourse);
//        userCourse = new UserCourse(student7, course, "G1");
//        studentCourseService.addStudentCourse(userCourse);
//        userCourse = new UserCourse(student8, course, "G1");
//        studentCourseService.addStudentCourse(userCourse);
//        userCourse = new UserCourse(student9, course, "G1");
//        studentCourseService.addStudentCourse(userCourse);
//        userCourse = new UserCourse(student10, course, "G1");
//        studentCourseService.addStudentCourse(userCourse);
//        userCourse = new UserCourse(student11, course, "G1");
//        studentCourseService.addStudentCourse(userCourse);
//        userCourse = new UserCourse(student12, course, "G1");
//        studentCourseService.addStudentCourse(userCourse);
//        userCourse = new UserCourse(student13, course, "G1");
//        studentCourseService.addStudentCourse(userCourse);
//        userCourse = new UserCourse(student14, course, "G1");
//        studentCourseService.addStudentCourse(userCourse);
//        userCourse = new UserCourse(student15, course, "G1");
//        studentCourseService.addStudentCourse(userCourse);
//        userCourse = new UserCourse(student16, course, "G1");
//        studentCourseService.addStudentCourse(userCourse);
//        userCourse = new UserCourse(student17, course, "G1");
//        studentCourseService.addStudentCourse(userCourse);
//        userCourse = new UserCourse(student18, course, "G1");
//        studentCourseService.addStudentCourse(userCourse);
//        userCourse = new UserCourse(student19, course, "G1");
//        studentCourseService.addStudentCourse(userCourse);
//        userCourse = new UserCourse(student20, course, "G1");
//        studentCourseService.addStudentCourse(userCourse);
//        userCourse = new UserCourse(student21, course, "G1");
//        studentCourseService.addStudentCourse(userCourse);
//        userCourse = new UserCourse(student22, course, "G1");
//        studentCourseService.addStudentCourse(userCourse);
//        userCourse = new UserCourse(student23, course, "G1");
//        studentCourseService.addStudentCourse(userCourse);
        Student student = new Student(20170069, "marwa", "lotus69", "lotus69", 4, 4.0);
        studentService.addStudent(student);
        UserCourse userCourse = new UserCourse(student, course, "G1");
        studentCourseService.addStudentCourse(userCourse);
        System.out.println("done");
    }
*/

/*    @PostMapping(path = "login")
    public @ResponseBody String login(@RequestParam String username, @RequestParam String password){
        *//*
        1- we contact the uni server with the username and password
        2- if user is already logged in, we inform the user
        3- if the user is not logged in, we add the courses of the user to UserCourse
        4- if the user is valid we return a json with the user info requeired by the app
         *//*
        StudentCourseContainer studentCourseContainer = studentService.getStudentCourses(username, password);

        if(studentCourseContainer == null) return "An error has occurred.";

        Optional<Student> studentOptional = studentService.findById(studentCourseContainer.getStudent().getId());

        if(studentOptional.isPresent())  return "User already exists.";

        for(int i=0; i<studentCourseContainer.getCourseList().length; i++) {
            Optional<Course> courseOptional =
                    courseService.findById(studentCourseContainer.getCourseList()[i].getCourseCode());
            if (courseOptional.isEmpty()) {
                return "Course id: " + studentCourseContainer.getCourseList()[i].getCourseCode() + " is not found.";
            }
        }

        studentService.addStudent(studentCourseContainer.getStudent());

        for(int i=0; i<studentCourseContainer.getCourseList().length; i++){
            Optional<Course> courseOptional =
                    courseService.findById(studentCourseContainer.getCourseList()[i].getCourseCode());

            UserCourse userCourse =
                    new UserCourse(studentCourseContainer.getStudent(), courseOptional.get(),
                            studentCourseContainer.getCourseList()[i].getGroup(), 0, 0);

            studentCourseService.addStudentCourse(userCourse);
        }

        return "Success.";
    }*/

/*    @PostMapping( path = "postattendance")
    public @ResponseBody String postAttendance(@RequestParam("date") @DateTimeFormat(pattern = "dd-MM-yyyy") Date date,
                                               @RequestParam String userGroup, @RequestParam String courseId, @RequestParam Integer userId){
        return studentService.postAttendance(date, userGroup, courseId, userId);
    }*/

    @GetMapping(value = "/getStudentCourses")
    public @ResponseBody String[]getStudentCourses(@RequestParam Integer studentID){
    Optional<Student> student = studentService.findById(studentID);
    if (student.isEmpty()) return new String[0];
    List<UserCourse> studentCourses = studentCourseService.getUserCourses(student.get());
    if (studentCourses.isEmpty()) return new String[0];
    String [] courses = new String[studentCourses.size()];
        for (int i = 0; i <studentCourses.size() ; i++) {
            courses[i] = studentCourses.get(i).getCourse().getCourseName();
        }
        return courses;
    }

    @GetMapping(value = "/checkAttendance")
    public @ResponseBody String checkAttendance(@RequestParam Integer studentID, @RequestParam String[] courses ,
                                       @RequestParam("date") @DateTimeFormat(pattern = "dd-MM-yyyy") Date date){
        Student student = studentService.findById(studentID).get();
        List<Attendance>attendances=null;
        for (int i = 0; i <courses.length ; i++) {
            Course course = courseService.findByCourseName(courses[i]);
            UserCourse userCourse = studentCourseService.getUserCourse(student,course);
            String userGroup=userCourse.getUserGroup();
            attendances = attendanceService.findAttendanceByCourseAndUserGroupAndDateAndAbsent(course,userGroup,date,false);
            Optional<TeachingAssistant> optionalTa = null;
           // int index=0;
            for (int j = 0; j < attendances.size(); j++) {

                User user = attendances.get(j).getUser();
                int id = user.getId();
                optionalTa = teachingAssistantService.findTAById(id);
                if (!optionalTa.isEmpty()) {
                    JSONObject jsonObject = attendanceService.getJsonFromAttendance(attendances.get(j));
                    return jsonObject.toString();

                }
            }
            }
        return attendanceService.getJsonFromAttendance(new Attendance()).toString();
    }

    @GetMapping(value = "/attend")
    public @ResponseBody String attend(@RequestParam Integer studentID, @RequestParam String courseName ,
                                       @RequestParam("date") @DateTimeFormat(pattern = "dd-MM-yyyy") Date date,@RequestParam long TAAttendanceID){
        Attendance attendance = attendanceService.getByID(TAAttendanceID).get();
        if (attendance.isAbsent()){
            return "the recording process is over";
        }
        Student student = studentService.findById(studentID).get();
        Course course = courseService.findByCourseName(courseName);
        UserCourse userCourse = studentCourseService.getUserCourse(student,course);
        String userGroup=userCourse.getUserGroup();
        List<Attendance> attendancesAbsent = attendanceService.findAttendanceByCourseAndUserGroupAndDateAndAbsent(course,userGroup,date,true);
        long attendanceID=0;
        for (int i = 0; i <attendancesAbsent.size() ; i++) {
            User user = attendancesAbsent.get(i).getUser();
            int id = user.getId();
            if (id == studentID){
                attendanceID=attendancesAbsent.get(i).getId();
            }
        }
        Attendance toBeAdded = new Attendance(attendanceID,student,course,userGroup,date,false);
        attendanceService.addAttendance(toBeAdded);
        return "attendance recorded successfully";
    }
}
