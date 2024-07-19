package com.fabiolima.coursemanagementsys.dao.specialdao;

import com.fabiolima.coursemanagementsys.entity.Course;
import com.fabiolima.coursemanagementsys.entity.Student;

public interface CourseSpecialDAO {

    Course findCourseAndStudentsByCourseId(int theId);

}
