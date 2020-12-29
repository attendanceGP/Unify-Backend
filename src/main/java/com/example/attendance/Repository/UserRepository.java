package com.example.attendance.Repository;

import com.example.attendance.User.Model.User;
import org.springframework.data.repository.CrudRepository;

public interface UserRepository extends CrudRepository<User, Integer> {
}
