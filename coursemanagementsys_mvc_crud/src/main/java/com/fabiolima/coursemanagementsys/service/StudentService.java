package com.fabiolima.coursemanagementsys.service;

import com.fabiolima.coursemanagementsys.entity.Student;

import java.util.List;

public interface StudentService {

    List<Student> findAllStudents();

    Student findStudentById(int theId);

    Student saveStudent(Student student);

    void deleteStudentById(int theId);

    Student findStudentAndEnrolledCoursesByStudentId(int theId);

    String withdrawEnrolledCourseByStudentId(int studentId, int courseId);

    String enrollStudent(int studentId, int courseId);
}
