package com.example.attendance.Repository;

import com.example.attendance.User.Model.Professor;
import org.springframework.data.repository.CrudRepository;

public interface ProfessorRepository extends CrudRepository<Professor, Integer> {
}
