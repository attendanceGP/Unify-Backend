package com.example.attendance.Resource;

import com.example.attendance.Containers.StudentCourseContainer;
import com.example.attendance.Models.*;
import com.example.attendance.Repository.StudentCourseRepository;
import com.example.attendance.Repository.StudentRepository;
import com.example.attendance.Repository.TeachingAssistantRepository;
import com.example.attendance.Service.*;
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


        Student student = new Student(20170171, "Ali Samy", "alialfie", "alialfie", 4, 4.0);
        //studentRepository.delete(student);
        studentService.addStudent(student);

        Student student2 = new Student(20170399, "Lamya Raed", "s", "s", 4, 4.0);
        studentService.addStudent(student2);

        Course course = new Course("CS464", "Genetic Algorithms");
        courseService.addCourse(course);

        UserCourse userCourse = new UserCourse(student2, course, "G2");
        studentCourseService.addStudentCourse(userCourse);
        course = new Course("CS467", "Machine Learning");
        courseService.addCourse(course);

        userCourse = new UserCourse(student, course, "G1");
        studentCourseService.addStudentCourse(userCourse);


        System.out.println("done");
    }

    @PostMapping(path = "login")
    public @ResponseBody String login(@RequestParam String username, @RequestParam String password){
        /*
        1- we contact the uni server with the username and password
        2- if user is already logged in, we inform the user
        3- if the user is not logged in, we add the courses of the user to UserCourse
        4- if the user is valid we return a json with the user info requeired by the app
         */
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
    }

    @PostMapping( path = "postattendance")
    public @ResponseBody String postAttendance(@RequestParam("date") @DateTimeFormat(pattern = "dd-MM-yyyy") Date date,
                                               @RequestParam String userGroup, @RequestParam String courseId, @RequestParam Integer userId){
        return studentService.postAttendance(date, userGroup, courseId, userId);
    }
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
    //API to make the student attend the section if available
    @GetMapping(value = "/attend")
    public @ResponseBody String attend(@RequestParam Integer studentID, @RequestParam String courseName ,
                                     @RequestParam("date") @DateTimeFormat(pattern = "dd-MM-yyyy") Date date){
    Student student = studentService.findById(studentID).get();
    Course course = courseService.findByCourseName(courseName);
    UserCourse userCourse = studentCourseService.getUserCourse(student,course);
    String userGroup=userCourse.getUserGroup();
    List<Attendance> attendances = attendanceService.findAttendanceByCourseAndUserGroupAndDate(course,userGroup,date);
    Optional<TeachingAssistant> optionalTa = null;
    int index = 0;
    //loop to get the TA that started the section if available
        for (int i = 0; i <attendances.size() ; i++) {
            User user = attendances.get(i).getUser();
            int id = user.getId();
            optionalTa = teachingAssistantService.findTAById(id);
            if (!optionalTa.isEmpty()){
                index = i;
                break;
            }
        }
        //loop to check if the student is already recorded
        for (int i = 0; i <attendances.size() ; i++) {
            User user = attendances.get(i).getUser();
            int id = user.getId();
            if (id == studentID){

                return "error:Attendance already recorded";
            }
        }
        if (optionalTa == null){
            return "error: there is no section/lab attendance available";
        }
        TeachingAssistant ta = optionalTa.get();
        Attendance record = attendances.get(index);
        if (record.isAbsent()){
            return "the process is over ";
        }
        else {
            Attendance toBeAdded = new Attendance(student,course,userGroup,date,false);
            attendanceService.addAttendance(toBeAdded);
            return "error:attendance recorded successfully";
        }
    }
}
