package com.example.attendance.User.Service;

import com.example.attendance.Models.Professor;
import com.example.attendance.Models.Student;
import com.example.attendance.Models.TeachingAssistant;
import com.example.attendance.Models.User;
import com.example.attendance.QueryUtils;
import com.example.attendance.Repository.*;
import com.example.attendance.User.Repository.UserRepository;
import org.apache.tomcat.util.codec.binary.Base64;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.web.client.RestTemplateBuilder;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;
import org.json.JSONObject;

import javax.swing.text.html.Option;
import java.time.Duration;
import java.util.Date;
import java.util.List;
import java.util.Optional;

@Service
public class UserService {
    @Autowired
    private StudentRepository studentRepository;

    @Autowired
    private UserRepository userRepository;

    public String login(String username, String password){
        List<User> users = userRepository.findByUsernameAndPassword(username, password);

        // not found
        if(users.size() == 0){
            return "{\"errorCode\":2}"; // error code for incorrect username or password
        }

        User user = users.get(0);

        // check date and if logged in recently (30 days) deny login request
        if(user.getLastLoginDate() != null) {    // logged in before
            Date currentDate = new Date();
            long duration = ((currentDate.getTime() - user.getLastLoginDate().getTime()) / (1000 * 60 * 60 * 24));

            // if less than 30 days, return appropriate message and deny
            // if not, update date and allow login
            if (duration <= 30) {
                //TODO remove the comment when testing is done
                //return "{\"errorCode\":1}"; // error code for too many logins in 30 days
            }
        }

        // new date
        user.setLastLoginDate(new Date());

        // generate and set token
        String keySource = username + user.getLastLoginDate().toString();
        byte [] tokenByte = new Base64(true).encodeBase64(keySource.getBytes());
        String token = new String(tokenByte);

        user.setToken(token);

        userRepository.save(user);

        JSONObject jsonObject = getJsonFromUser(user);
        return jsonObject.toString();
    }

    private JSONObject getJsonFromUser(User user){
        JSONObject json = new JSONObject();

        String type;

        if(user instanceof Student){
            type = "student";
            Student s = studentRepository.findById(user.getId()).get();
            json.put("gpa", s.getGpa());
            json.put("level", s.getLevel());
        }else if (user instanceof Professor){
            type = "professor";
        }else{
            type = "TA";
        }

        json.put("type", type);
        json.put("name", user.getName());
        json.put("id", user.getId());
        json.put("token", user.getToken());

        return json;
    }

    public String getToken(Integer id){
        Optional<User> optionalUser = userRepository.findById(id);

        if(optionalUser.isEmpty()) return null;

        return optionalUser.get().getToken();
    }
}
