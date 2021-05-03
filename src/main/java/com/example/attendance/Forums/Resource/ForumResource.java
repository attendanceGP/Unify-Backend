package com.example.attendance.Forums.Resource;

import com.example.attendance.Forums.Service.ForumService;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.web.bind.annotation.*;

import java.util.Date;


@Controller
@RequestMapping(path = "/forums")
public class ForumResource {
    @Autowired
    private ForumService forumService;

//    Get student's forums (his courses)
    @GetMapping(path = "getStudentForums")
    public @ResponseBody
    String getStudentForums(@RequestParam Integer userId){
        return forumService.getStudentPosts(userId).toString();
    }

//    Get Post Replies
    @GetMapping(path = "getPostReplies")
    public @ResponseBody
    String getPostReplies(@RequestParam Integer postId){
        return forumService.getPostReplies(postId).toString();
    }


//    Posts
    @PostMapping(path = "addPost")
    public @ResponseBody
    String addPost(@RequestParam Integer userId, @RequestParam String courseCode,
                 @RequestParam("date") @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss") Date date,
                 @RequestParam String title, @RequestParam String content) {
        return forumService.addPost(userId, courseCode, date, title, content);
    }

    @PostMapping(path = "removePost")
    public @ResponseBody
    String removePost(@RequestParam Integer postId) {
        return forumService.removePost(postId);
    }


//    Replies
    @PostMapping(path = "addReply")
    public @ResponseBody
    String addReply(@RequestParam Integer userId, @RequestParam Integer postId,
                 @RequestParam("date") @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss") Date date,
                 @RequestParam String description) {
        return forumService.addReply(userId, postId, date, description);
    }

    @PostMapping(path = "removeReply")
    public @ResponseBody
    String removeReply(@RequestParam Integer postId, @RequestParam Integer replyId) {
        return forumService.removeReply(postId, replyId);
    }
}
