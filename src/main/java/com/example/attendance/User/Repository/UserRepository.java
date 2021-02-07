package com.example.attendance.User.Repository;

import com.example.attendance.Models.User;
import org.springframework.data.repository.CrudRepository;

import java.util.List;
import java.util.Optional;

public interface UserRepository extends CrudRepository<User, Integer> {
    List<User> findByUsernameAndPassword(String username, String password);
}
