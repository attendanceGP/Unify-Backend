package com.example.attendance.Forums.Service;

import com.example.attendance.Forums.Model.Post;
import com.example.attendance.Forums.Model.Reply;
import com.example.attendance.Forums.Repository.PostRepository;
import com.example.attendance.Forums.Repository.ReplyRepository;
import com.example.attendance.Course.Model.Course;
import com.example.attendance.User.Models.User;
import com.example.attendance.Course.Repository.CourseRepository;
import com.example.attendance.User.Repository.UserRepository;
import org.json.JSONArray;
import org.json.JSONObject;
import org.springframework.stereotype.Service;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.Date;
import java.util.List;


@Service
public class ForumService {
    @Autowired
    private PostRepository postRepository;

    @Autowired
    private ReplyRepository replyRepository;

    @Autowired
    private CourseRepository courseRepository;

    @Autowired
    private UserRepository userRepository;

    public JSONArray getStudentPosts(Integer userId) {
        JSONArray jsonArray = getJsonFromPosts((postRepository.getStudentPosts(userId)));
        return jsonArray;
    }

    private JSONArray getJsonFromPosts(List<Post> Posts){
        JSONArray jsonArray = new JSONArray();

        for(Post post: Posts){
            JSONObject jsonObject = new JSONObject();

            jsonObject.put("id", post.getId());
            jsonObject.put("userId", post.getUser().getId());
            jsonObject.put("userName", post.getUser().getName());
            jsonObject.put("title", post.getTitle());
            jsonObject.put("content", post.getContent());
            jsonObject.put("courseCode", post.getCourse().getCourseCode());
            jsonObject.put("date", post.getDate());

            jsonArray.put(jsonObject);
        }

        return jsonArray;
    }

    public JSONArray getPostReplies(Integer postId){
        JSONArray jsonArray = getJsonFromReplies((replyRepository.getPostReplies(postId)));
        return jsonArray;
    }

    private JSONArray getJsonFromReplies(List<Reply> postReplies) {
        JSONArray jsonArray = new JSONArray();

        for(Reply reply: postReplies){
            JSONObject jsonObject = new JSONObject();

            jsonObject.put("id", reply.getId());
            jsonObject.put("postId", reply.getPost().getId());
            jsonObject.put("userId", reply.getUser().getId());
            jsonObject.put("userName", reply.getUser().getName());
            jsonObject.put("description", reply.getDescription());
            jsonObject.put("date", reply.getDate());

            jsonArray.put(jsonObject);
        }

        return jsonArray;
    }


    public void addPost(Integer userId, String courseCode, Date date, String title, String content) {
        User user = userRepository.findById(userId).get();
        Course course = courseRepository.findById(courseCode).get();

        Post post = new Post(user, course, date, title, content);

        postRepository.save(post);

        System.out.println(" post details : " + userId + " " + courseCode + "\t" + date + "\t" + title + "\t" + content);
    }

    public void removePost(Integer postId) {
        removePostReplies(postId);
        postRepository.deletePostById(postId);
    }

    public void addReply(Integer userId, Integer postId, Date date, String description) {
        User user = userRepository.findById(userId).get();
        Post post = postRepository.findById(postId);

        Reply reply = new Reply(user, post, date, description);

        replyRepository.save(reply);

        System.out.println(" reply details : " + userId + " " + postId + "\t" + date + "\t" + description);
    }

    public void removeReply(Integer replyId) {
        replyRepository.deleteReplyById(replyId);
    }

    public void removePostReplies(Integer postId){
        replyRepository.deleteReplyByPostId(postId);
    }

}
