package com.fabiolima.coursemanagementsys.dao.specialdao;

import com.fabiolima.coursemanagementsys.entity.Student;

public interface StudentSpecialDAO {

    Student findStudentAndEnrolledCourses(int theId);
}
