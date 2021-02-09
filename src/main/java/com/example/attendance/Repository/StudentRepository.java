package com.example.attendance.Repository;

import com.example.attendance.Models.*;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;

import java.util.Date;
import java.util.List;

public interface StudentRepository extends CrudRepository<Student, Integer> {
}
