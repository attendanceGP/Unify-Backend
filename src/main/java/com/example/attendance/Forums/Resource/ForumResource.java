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

//  -------------  Posts

    /**
     * Get API.
     * API for retrieving posts
     * @param userId
     * @return JSONArray object containing all posts
     */
    @GetMapping(path = "getStudentForums")
    public @ResponseBody
    String getStudentForums(@RequestParam Integer userId){
        return forumService.getStudentPosts(userId).toString();
    }

    /**
     * Get API.
     * API for getting replies for a specific forum (post) given postID
     * @param postId
     * @return JSONArray object containing all post replies.
     */
    @GetMapping(path = "getPostReplies")
    public @ResponseBody
    String getPostReplies(@RequestParam Integer postId){
        return forumService.getPostReplies(postId).toString();
    }

    /**
     * Post API.
     * API for adding a new post to database.
     * @param userId
     * @param courseCode
     * @param date
     * @param title
     * @param content
     */
    @PostMapping(path = "addPost")
    public @ResponseBody
    void addPost(@RequestParam Integer userId, @RequestParam String courseCode,
                 @RequestParam("date") @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss") Date date,
                 @RequestParam String title, @RequestParam String content) {
        forumService.addPost(userId, courseCode, date, title, content);
    }

    /**
     * Post API.
     * API for deleting a forum(post)
     * @param postId
     */
    @PostMapping(path = "removePost")
    public @ResponseBody
    void removePost(@RequestParam Integer postId) {
        forumService.removePost(postId);
    }


//  -------------  Replies

    /**
     * Post API.
     * API for adding a reply.
     * @param userId
     * @param postId
     * @param date
     * @param description
     */
    @PostMapping(path = "addReply")
    public @ResponseBody
    void addReply(@RequestParam Integer userId, @RequestParam Integer postId,
                 @RequestParam("date") @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss") Date date,
                 @RequestParam String description) {
        forumService.addReply(userId, postId, date, description);
    }

    /**
     * Post API.
     * API for deleting a reply.
     * @param replyId
     */
    @PostMapping(path = "removeReply")
    public @ResponseBody
    void removeReply(@RequestParam Integer replyId) {
        forumService.removeReply(replyId);
    }
}
