package com.example.attendance.Forums.Resource;

import com.example.attendance.Forums.Service.PostService;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.web.bind.annotation.*;


@Controller
@RequestMapping(path = "/forum")
public class PostResource {
    @Autowired
    private PostService postService;
}
