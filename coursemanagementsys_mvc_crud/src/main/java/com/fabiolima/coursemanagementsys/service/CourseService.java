package com.fabiolima.coursemanagementsys.service;

import com.fabiolima.coursemanagementsys.entity.Course;
import com.fabiolima.coursemanagementsys.entity.Student;

import java.util.List;

public interface CourseService {

    List<Course> findAllCourses();

    List<Course> findAllAvailableCoursesToTeach();

    Course findCourseById(int theId);

    Course saveCourse(Course theCourse);

    void deleteCourseById(int theId);

    Course findCourseAndStudents(int theId);



}