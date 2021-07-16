package com.example.attendance.User.Repository;

import com.example.attendance.User.Models.Student;
import org.springframework.data.repository.CrudRepository;

public interface StudentRepository extends CrudRepository<Student, Integer> {
}
