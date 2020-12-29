package com.example.attendance.Repository;

import com.example.attendance.User.Model.Student;
import org.springframework.data.repository.CrudRepository;

public interface StudentRepository extends CrudRepository<Student, Integer> {
}
