package com.fabiolima.coursemanagementsys.service;

import com.fabiolima.coursemanagementsys.dao.CourseRepository;
import com.fabiolima.coursemanagementsys.dao.specialdao.CourseSpecialDAO;
import com.fabiolima.coursemanagementsys.entity.Course;
import com.fabiolima.coursemanagementsys.entity.Student;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Optional;

@Service
public class CourseServiceImpl implements CourseService{

    private CourseRepository courseRepository;
    @Autowired
    private CourseSpecialDAO courseSpecialDAO;

    @Autowired
    public CourseServiceImpl(CourseRepository theCourseRepository){
        courseRepository = theCourseRepository;
    }

    @Override
    public List<Course> findAllCourses() {
        return courseRepository.findAll();
    }

    @Override
    public List<Course> findAllAvailableCoursesToTeach() {

        List<Course> courseList = findAllCourses();
        List<Course> availableCourses = new ArrayList<>();

        for (Course c : courseList){
            if(c.getTutor()==null) availableCourses.add(c);
        }
        if(availableCourses.isEmpty()) throw new RuntimeException("There are no available courses");
        else return availableCourses;
    }

    @Override
    public Course findCourseById(int theId) {
        Optional<Course> result = courseRepository.findById(theId);

        if(result.isEmpty()){
            throw new RuntimeException("Course id: " + theId + " not found");
        }
        else return result.get();
    }

    @Override
    public Course saveCourse(Course theCourse) {
        return courseRepository.save(theCourse);
    }

    @Override
    public void deleteCourseById(int theId) {

        Optional<Course> result = courseRepository.findById(theId);

        if(result.isEmpty()) throw new RuntimeException("Course id: " + theId + "not found");

        else courseRepository.deleteById(theId);
    }

    @Override
    public Course findCourseAndStudents(int theId) {
        Optional<Course> result = courseRepository.findById(theId);

        if(result.isEmpty()) throw new RuntimeException("Couldn't find course id: " + theId);
        else if(result.get().getStudentList() == null)
            throw new RuntimeException("Could not find associated students to this course");
        else
            return courseSpecialDAO.findCourseAndStudentsByCourseId(theId);

    }
}
