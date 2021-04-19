package com.example.attendance.Deadline.Resource;

import com.example.attendance.Deadline.Model.Deadline;
import com.example.attendance.Deadline.Service.DeadlineService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Controller
@RequestMapping(path = "/deadline")
public class DeadlineResource {
    @Autowired
    private DeadlineService deadlineService;

    @GetMapping(path = "getStudentDeadlines")
    public @ResponseBody
    String getStudentDeadlines(@RequestParam Integer userId){
        return deadlineService.getStudentDeadlines(userId).toString();
    }
}
