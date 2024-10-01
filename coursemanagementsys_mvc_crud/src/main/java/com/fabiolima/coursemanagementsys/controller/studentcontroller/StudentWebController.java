package com.fabiolima.coursemanagementsys.controller.studentcontroller;

import com.fabiolima.coursemanagementsys.entity.Course;
import com.fabiolima.coursemanagementsys.entity.Student;
import com.fabiolima.coursemanagementsys.service.StudentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.List;

@Controller
@RequestMapping("/studentWebPage")
public class StudentWebController {

    private StudentService studentService;

    @Autowired
    public StudentWebController(StudentService studentService){
        this.studentService = studentService;
    }

    @GetMapping("/{studentId}")
    public String showHome(@PathVariable int studentId,
                           Model theModel){
        Student tempStudent = studentService.findStudentAndEnrolledCoursesByStudentId(studentId);
        List<Course> courseList = tempStudent.getCourseList();
        theModel.addAttribute("student", tempStudent);
        theModel.addAttribute("courses", courseList);
        
        return "student-webpage/student-dashboard";
    }

}
