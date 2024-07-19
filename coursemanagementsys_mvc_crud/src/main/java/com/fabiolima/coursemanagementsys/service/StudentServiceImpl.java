package com.fabiolima.coursemanagementsys.service;

import com.fabiolima.coursemanagementsys.dao.CourseRepository;
import com.fabiolima.coursemanagementsys.dao.StudentRepository;
import com.fabiolima.coursemanagementsys.dao.specialdao.StudentSpecialDAO;
import com.fabiolima.coursemanagementsys.entity.Course;
import com.fabiolima.coursemanagementsys.entity.Student;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Iterator;
import java.util.List;
import java.util.Optional;

@Service
public class StudentServiceImpl implements StudentService{

    @Autowired
    private StudentSpecialDAO studentSpecialDAO;
    private StudentRepository studentRepository;
    private CourseRepository courseRepository;

    @Autowired
    public StudentServiceImpl(StudentRepository theStudentRepository,
                              CourseRepository courseRepository){
        studentRepository = theStudentRepository;
        this.courseRepository = courseRepository;
    }

    @Override
    public List<Student> findAllStudents() {
        return studentRepository.findAll();
    }

    @Override
    public Student findStudentById(int theId) {

        Optional<Student> result = studentRepository.findById(theId);
        if(result.isEmpty()) throw new RuntimeException("Student id: " + theId + " not found");
        else return result.get();
    }

    @Override
    public Student saveStudent(Student student) {
       return studentRepository.save(student);
    }

    @Override
    public void deleteStudentById(int theId) {

        Optional<Student> result = studentRepository.findById(theId);
        if(result.isEmpty()) throw new RuntimeException("Could not find student id: " + theId);
        else studentRepository.deleteById(theId);

    }

    @Override
    public Student findStudentAndEnrolledCoursesByStudentId(int theId) {
        Optional<Student> result = studentRepository.findById(theId);

        if(result.isEmpty())
            throw new RuntimeException("Couldn't find the Student id: " + theId);
        else if (result.get().getCourseList() == null)
            throw new RuntimeException("Student is not enrolled in any course");
        else return studentSpecialDAO.findStudentAndEnrolledCourses(theId);
    }

    @Override
    public String withdrawEnrolledCourseByStudentId(int studentId, int courseId) {

        Student tempStudent = findStudentAndEnrolledCoursesByStudentId(studentId);
        List<Course> enrolledCourse = tempStudent.getCourseList();

        boolean courseFound = false;
        Course tempCourse = new Course();
        Iterator<Course> iterator = enrolledCourse.iterator();
        while (iterator.hasNext()) {
            Course c = iterator.next();
            if (c.getId() == courseId) {
                tempCourse = c;
                iterator.remove();
                courseFound = true;
                break;
            }
        }
        if(courseFound){
            saveStudent(tempStudent);
            return tempStudent.getFirstName() + " " + tempStudent.getLastName() + " has withdrawn the " + tempCourse.getTitle();
        }
        else throw new RuntimeException(tempStudent.getFirstName() + " " + tempStudent.getLastName() + " is not enrolled in the " + tempCourse.getTitle());
    }

    @Override
    public String enrollStudent(int studentId, int courseId) {

        Student tempStudent = findStudentAndEnrolledCoursesByStudentId(studentId);
        List<Course> enrolledCourses = tempStudent.getCourseList();
        Optional<Course> optCourse = courseRepository.findById(courseId);
        Course theCourse = new Course();
        if(optCourse.isPresent()) theCourse = optCourse.get();
        else throw new RuntimeException("Course not found");

        if (enrolledCourses.contains(theCourse))
            throw new RuntimeException(tempStudent.getFirstName() + " " + tempStudent.getLastName() + " is already enrolled in the " + theCourse.getTitle());
        else{
            tempStudent.addCourse(theCourse);
            saveStudent(tempStudent);
            return tempStudent.getFirstName() + " " + tempStudent.getLastName() + " has withdrawn the " + theCourse.getTitle();
        }
    }
}