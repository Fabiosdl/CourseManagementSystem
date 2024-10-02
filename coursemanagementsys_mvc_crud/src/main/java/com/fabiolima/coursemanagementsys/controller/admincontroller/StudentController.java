package com.fabiolima.coursemanagementsys.controller.admincontroller;

import com.fabiolima.coursemanagementsys.entity.Course;
import com.fabiolima.coursemanagementsys.entity.Student;
import com.fabiolima.coursemanagementsys.entity.User;
import com.fabiolima.coursemanagementsys.service.CourseService;
import com.fabiolima.coursemanagementsys.service.StudentService;
import com.fabiolima.coursemanagementsys.service.UserService;
import com.fabiolima.coursemanagementsys.user.WebUser;
import jakarta.servlet.http.HttpSession;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.logging.Logger;

@Controller
@RequestMapping("/admin/students")
public class StudentController {

    private Logger logger = Logger.getLogger(getClass().getName());

    private StudentService studentService;
    private CourseService courseService;
    private UserService userService;

    @Autowired
    public StudentController(StudentService studentService,
                             CourseService courseService,
                             UserService userService){
        this.studentService = studentService;
        this.courseService = courseService;
        this.userService = userService;
    }

    @GetMapping
    public String showStudents(Model theModel){
        List<Student> studentsList = studentService.findAllStudents();
        theModel.addAttribute("students", studentsList);
        return "admin/students/list-students";
    }

    @GetMapping("/showFormAddStudent")
    public String showFormAddStudent(Model theModel){
        WebUser theStudent = new WebUser();//create an object to bind the data from the
        theStudent.setRole("STUDENT");
        // form to this object.
        theModel.addAttribute("student", theStudent);
        //here I created an empty Student instance and bind it to the Model instance.
        return "admin/students/student-form";
    }
        // now in the post mapping, spring boot will bind the info to theModel and collect the data from the model and
        // bind it to the Student instance in the method below
    @PostMapping("/save")
    public String saveStudent(@Valid @ModelAttribute("student") WebUser theStudent,
                              BindingResult bindingResult,
                              HttpSession httpSession,
                              Model theModel){
        String userName = theStudent.getUserName();
        logger.info("Processing registration form for: " + userName);

        if(bindingResult.hasErrors()) return "redirect:/admin/tutors/showFormForAddTutor";

        User existingUser = userService.findByUserName(userName);

        if(existingUser != null){
            theModel.addAttribute("webUser", new WebUser());
            theModel.addAttribute("registrationError", "User name already exists.");

            logger.warning("User name already exists.");
            return "redirect:/admin/students/showFormAddStudent";
        }

        userService.save(theStudent);
        logger.info("Successfully created user: " + userName);
        return "redirect:/admin/students";
    }

    @GetMapping("/viewStudent/{studentId}")
    public String viewStudent(@PathVariable int studentId, Model theModel){
        Student tempStudent = studentService.findStudentAndEnrolledCoursesByStudentId(studentId);
        theModel.addAttribute("student", tempStudent);
        theModel.addAttribute("studentCourse", tempStudent.getCourseList());
        return "admin/students/student-view";
    }

    @GetMapping("/delete/{studentId}")
    public String deleteStudent(@PathVariable int studentId, Model theModel){
        studentService.deleteStudentById(studentId);
        return "redirect:/admin/students";
    }

    @GetMapping("/withdrawCourse/{studentId}/{courseId}")
    public String withdrawCourse(@PathVariable int studentId,
                                 @PathVariable int courseId){
        studentService.withdrawEnrolledCourseByStudentId(studentId,courseId);
        return "redirect:/admin/students/viewStudent/"+studentId;
    }

    @GetMapping("/enrollCourse/{studentId}")
    public String enrollCourse(@PathVariable int studentId,
                               Model theModel){
        Student tempStudent = studentService.findStudentById(studentId);
        List<Course> courseList = courseService.findAllCourses();
        theModel.addAttribute("availableCourses",courseList);
        theModel.addAttribute("student", tempStudent);
        // i want to show a list of courses for the student to enroll
        return "admin/students/student-enroll-courses";
    }

    @GetMapping("/enroll/{studentId}/{courseId}")
    public String enrollToSelectedCourse(@PathVariable int studentId,
                                         @PathVariable int courseId){

        studentService.enrollStudent(studentId,courseId);

        //method is not good enough.
        //create another method in the Service package to display all the courses, but do not allow the student
        // to enroll in a course if the student is already enrolled

        return "redirect:/admin/students/viewStudent/" + studentId;
    }
}