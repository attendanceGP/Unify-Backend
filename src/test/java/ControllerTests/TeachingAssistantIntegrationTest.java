package ControllerTests;

import com.example.attendance.AttendanceApplication;
import com.example.attendance.User.Resource.TeachingAssistantResource;
import com.example.attendance.User.Service.TeachingAssistantService;
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
    @WebMvcTest(controllers = TeachingAssistantResource.class)
    class TeachingAssistantIntegrationTest {
        @Autowired
        private MockMvc mockMvc;

        @Autowired
        private WebApplicationContext webApplicationContext;

        @MockBean
        private TeachingAssistantService teachingAssistantService;

        // used to create a mockMvc instance to use for testing
        @Before
        public void setUp()
        {
            this.mockMvc = webAppContextSetup(webApplicationContext).build();
        }

        //case where all inputs go into the request in their correct formats
        @Test
        void whenPostAttendanceValidInput_thenReturns200() throws Exception {
            this.mockMvc.perform(post("/ta/postattendance?date=22-09-2018&userGroup=G2&courseId=CS467&userId=20170349")
                    .contentType("application/json"))
                    .andExpect(status().isOk());
        }
        //all cases when any value in the post request is null
    @Test
    void whenPostAttendanceInvalidInput_thenReturns400() throws Exception {

        this.mockMvc.perform(post("/ta/postattendance?date=null&userGroup=G2&courseId=CS467&userId=20170349"))
                //checks the status of the request, this will be true and have a green checkmark if the
                //request is a bad request meaning something is missing from the request and its code is 400
                .andExpect(status().isBadRequest());

        this.mockMvc.perform(post("/ta/postattendance?date=null&courseId=CS467&userId=20170349"))
                .andExpect(status().isBadRequest());

        this.mockMvc.perform(post("/ta/postattendance?date=null&userGroup=G2&userId=20170349"))
                .andExpect(status().isBadRequest());

        this.mockMvc.perform(post("/ta/postattendance?date=null&userGroup=G2&courseId=CS467&userId=null"))
                .andExpect(status().isBadRequest());
    }

}
