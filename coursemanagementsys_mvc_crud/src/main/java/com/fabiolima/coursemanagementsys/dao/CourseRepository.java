package com.fabiolima.coursemanagementsys.dao;

import com.fabiolima.coursemanagementsys.entity.Course;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CourseRepository extends JpaRepository<Course, Integer> {
}
