package ControllerTests;

import com.example.attendance.AttendanceApplication;
import com.example.attendance.Resource.TeachingAssistantResource;
import org.junit.Before;
import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
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

        //used to build a mockMvc instance to be used for testing requests
        @Before
        public void setUp()
        {
            this.mockMvc = webAppContextSetup(webApplicationContext).build();
        }

        //case where all inputs go into the request in their correct formats
        @Test
        void whenValidInput_thenReturns200() throws Exception {
            this.mockMvc.perform(post("/ta/postattendance?date=22-09-2018&userGroup=G2&courseId=CS467&userId=20170349")
                    .contentType("application/json"))
                    .andExpect(status().isOk());
        }

        //all cases when any value in the post request is null
    @Test
    void whenInvalidInput_thenReturns400() throws Exception {

        this.mockMvc.perform(post("/ta/postattendance?date=null&userGroup=G2&courseId=CS467&userId=20170349"))
                .andExpect(status().isBadRequest());
        this.mockMvc.perform(post("/ta/postattendance?date=null&courseId=CS467&userId=20170349"))
                .andExpect(status().isBadRequest());
        this.mockMvc.perform(post("/ta/postattendance?date=null&userGroup=G2&userId=20170349"))
                .andExpect(status().isBadRequest());
        this.mockMvc.perform(post("/ta/postattendance?date=null&userGroup=G2&courseId=CS467&userId=null"))
                .andExpect(status().isBadRequest());
    }

    }
