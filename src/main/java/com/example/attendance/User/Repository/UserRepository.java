package com.example.attendance.User.Repository;

import com.example.attendance.User.Models.User;
import org.springframework.data.repository.CrudRepository;

import java.util.List;

public interface UserRepository extends CrudRepository<User, Integer> {
    List<User> findByUsernameAndPassword(String username, String password);
}
