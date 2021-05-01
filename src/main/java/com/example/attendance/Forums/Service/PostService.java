package com.example.attendance.Forums.Service;

import com.example.attendance.Forums.Repository.PostRepository;
import com.example.attendance.Forums.Repository.ReplyRepository;
import com.example.attendance.Forums.Repository.ReplyVoteRepository;
import com.example.attendance.Repository.CourseRepository;
import com.example.attendance.User.Repository.UserRepository;
import org.springframework.stereotype.Service;
import org.json.JSONArray;
import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;



@Service
public class PostService {
    @Autowired
    private PostRepository postRepository;

    @Autowired
    private ReplyRepository replyRepository;

    @Autowired
    private ReplyVoteRepository replyVoteRepository;

    @Autowired
    private CourseRepository courseRepository;

    @Autowired
    private UserRepository userRepository;
}
