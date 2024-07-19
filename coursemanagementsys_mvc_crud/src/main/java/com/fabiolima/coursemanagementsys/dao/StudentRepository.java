package com.fabiolima.coursemanagementsys.dao;

import com.fabiolima.coursemanagementsys.entity.Student;
import org.springframework.data.jpa.repository.JpaRepository;

public interface StudentRepository extends JpaRepository<Student, Integer> {
}
