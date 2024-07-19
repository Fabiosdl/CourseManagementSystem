package com.fabiolima.coursemanagementsys.controller;

import com.fabiolima.coursemanagementsys.entity.Course;
import com.fabiolima.coursemanagementsys.entity.Student;
import com.fabiolima.coursemanagementsys.service.CourseService;
import com.fabiolima.coursemanagementsys.service.StudentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Controller
@RequestMapping("/students")
public class StudentController {

    private StudentService studentService;
    private CourseService courseService;

    @Autowired
    public StudentController(StudentService studentService,
                             CourseService courseService){
        this.studentService = studentService;
        this.courseService = courseService;
    }

    @GetMapping
    public String showStudents(Model theModel){
        List<Student> studentsList = studentService.findAllStudents();
        theModel.addAttribute("students", studentsList);
        return "/students/list-students";
    }

    @GetMapping("/showFormAddStudent")
    public String showFormAddStudent(Model theModel){
        Student theStudent = new Student();//create an object to bind the data from the
        // form to this object.
        theModel.addAttribute("student", theStudent);
        //here I created an empty Student instance and bind it to the Model instance.
        return "/students/student-form";
    }
        // now in the post mapping, spring boot will bind the info to theModel and collect the data from the model and
        // bind it to the Student instance in the method below
    @PostMapping("/save")
    public String saveStudent(@ModelAttribute("student") Student theStudent){
        studentService.saveStudent(theStudent);
        return "redirect:/students";
    }

    @GetMapping("/viewStudent/{studentId}")
    public String viewStudent(@PathVariable int studentId, Model theModel){
        Student tempStudent = studentService.findStudentAndEnrolledCoursesByStudentId(studentId);
        theModel.addAttribute("student", tempStudent);
        theModel.addAttribute("studentCourse", tempStudent.getCourseList());
        return "/students/student-view";
    }

    @GetMapping("/withdrawCourse/{studentId}/{courseId}")
    public String withdrawCourse(@PathVariable int studentId,
                                 @PathVariable int courseId){
        studentService.withdrawEnrolledCourseByStudentId(studentId,courseId);
        return "redirect:/students/viewStudent/"+studentId;
    }

    @GetMapping("/enrollCourse/{studentId}")
    public String enrollCourse(@PathVariable int studentId,
                               Model theModel){
        Student tempStudent = studentService.findStudentById(studentId);
        List<Course> courseList = courseService.findAllCourses();
        theModel.addAttribute("availableCourses",courseList);
        theModel.addAttribute("student", tempStudent);
        // i want to show a list of courses for the student to enroll
        return "/students/student-enroll-courses";
    }

    @GetMapping("/enroll/{studentId}/{courseId}")
    public String enrollToSelectedCourse(@PathVariable int studentId,
                                         @PathVariable int courseId){

        studentService.enrollStudent(studentId,courseId);

        //method is not good enough.
        //create another method in the Service package to display all the courses, but do not allow the student
        // to enroll in a course if the student is already enrolled

        return "redirect:/students/viewStudent/" + studentId;
    }
}