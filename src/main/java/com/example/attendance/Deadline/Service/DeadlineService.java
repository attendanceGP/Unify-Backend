package com.example.attendance.Deadline.Service;

import com.example.attendance.Deadline.Model.Deadline;
import com.example.attendance.Deadline.Repository.DeadlineRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class DeadlineService {
    @Autowired
    private DeadlineRepository deadlineRepository;

    public List<Deadline> getStudentDeadlines(Integer userId){
        return deadlineRepository.getStudentDeadlines(userId);
    }
}
