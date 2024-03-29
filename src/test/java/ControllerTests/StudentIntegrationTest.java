package ControllerTests;

import UseCaseTestingInterfaces.AddUseCase;
import com.example.attendance.AttendanceApplication;
import com.example.attendance.User.Models.Student;
import com.example.attendance.User.Resource.StudentResource;
import com.example.attendance.Course.Service.CourseService;
import com.example.attendance.Course.Service.UserCourseService;
import com.example.attendance.User.Service.StudentService;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.Before;
import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.web.context.WebApplicationContext;

import static org.springframework.test.web.servlet.setup.MockMvcBuilders.webAppContextSetup;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@RunWith(SpringRunner.class)
@ContextConfiguration(classes = AttendanceApplication.class)
//we specify the controller we want in the application context so as not to include all controllers that are unneeded
//or won't be tested
@WebMvcTest(controllers = StudentResource.class)
class StudentIntegrationTest {
    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private WebApplicationContext webApplicationContext;

    //mocked services needed for all of our tests
    @MockBean
    private StudentService studentService;
    @MockBean
    private CourseService courseService;
    @MockBean
    private UserCourseService userCourseService;
    @MockBean
    private AddUseCase addUseCase;
    @Autowired
    private ObjectMapper objectMapper;

    // used to create a mockMvc instance to use for testing
    @Before
    public void setUp()
    {
        this.mockMvc = webAppContextSetup(webApplicationContext).build();
    }

    //case where all inputs go into the request in their correct formats
    @Test
    void whenPostAttendanceValidInput_thenReturns200() throws Exception {
        this.mockMvc.perform(post("/student/postattendance?date=22-09-2018&userGroup=G2&courseId=CS467&userId=20170349")
                .contentType("application/json"))
                .andExpect(status().isOk());
    }

    //all cases when any value in the post request is null
    @Test
    void whenPostAttendanceInvalidInput_thenReturns400() throws Exception {

        this.mockMvc.perform(post("/student/postattendance?date=null&userGroup=G2&courseId=CS467&userId=20170349"))
                .andExpect(status().isBadRequest());
        this.mockMvc.perform(post("/student/postattendance?date=null&courseId=CS467&userId=20170349"))
                .andExpect(status().isBadRequest());
        this.mockMvc.perform(post("/student/postattendance?date=null&userGroup=G2&userId=20170349"))
                .andExpect(status().isBadRequest());
        this.mockMvc.perform(post("/student/postattendance?date=null&userGroup=G2&courseId=CS467&userId=null"))
                .andExpect(status().isBadRequest());
    }

    //checks if login parameters and request are sent correctly
    @Test
    void whenLoginValidInput_thenReturns200() throws Exception {
        this.mockMvc.perform(post("/student/login?username=Youssef mohamed&password=sdsas")
                .contentType("application/json"))
                .andExpect(status().isOk());
    }

    //checks if login parameters and request are faulty
    @Test
    void whenLoginInvalidInput_thenReturns400() throws Exception {
        this.mockMvc.perform(post("/student/login?username=Youssef Mohamed")
                .contentType("application/json"))
                .andExpect(status().isBadRequest());

        this.mockMvc.perform(post("/student/login?password=party time with the bois")
                .contentType("application/json"))
                .andExpect(status().isBadRequest());

    }

    //test if data sent from the http request is mapped correctly to the java class
    @Test
    void whenAddValid_thenReturns200() throws Exception{
        Student student = new Student(20170349,"Youssef Mohamed", "s", "s",4,2.8);
        this.mockMvc.perform(post("/student/add")
                //checks for correct request content type
                .contentType("application/json")
                //requestBody(student is generated using the ObjectMapper, serializing a UserResource object to a JSON string.
                .content(objectMapper.writeValueAsString(student)))
                //checking if the request has been sent and processed correctly
                .andExpect(status().isOk());
        //if the test is successful this means that our http request has been parsed correctly and sent the parameters
        //correctly as java objects to the add function our object here being student
    }

    // test if data sent from http request to add student to the database has any null values
    @Test
    void whenAddHasNullStudent_thenReturns400() throws Exception {
        Student student = new Student(null,null,null, null, null ,null);

        this.mockMvc.perform(post("/student/add")
                .contentType("application/json")
                .content(objectMapper.writeValueAsString(student)))
                .andExpect(status().isBadRequest());
    }


    /*@Test
    void whenAddHasValidInput_thenMapsToBusinessLogic() throws Exception {
        Student student = new Student(20170349,"Youssef Mohamed",4,2.8);

        this.mockMvc.perform(post("/student/add")
                .contentType("application/json")
                .content(objectMapper.writeValueAsString(student)))
                .andExpect(status().isOk());

        ArgumentCaptor<Student> studentCaptor = ArgumentCaptor.forClass(Student.class);
        verify(addUseCase,times(1)).addStudent(studentCaptor.capture());
        assertThat(studentCaptor.getValue().getId()).isEqualTo(20170349);
        assertThat(studentCaptor.getValue().getName()).isEqualTo("Youssef Mohamed");
        assertThat(studentCaptor.getValue().getLevel()).isEqualTo(4);
        assertThat(studentCaptor.getValue().getGpa()).isEqualTo(2.8);
    }*/
}
