package com.fabiolima.coursemanagementsys.controller;

import com.fabiolima.coursemanagementsys.entity.Course;
import com.fabiolima.coursemanagementsys.service.CourseService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Controller
@RequestMapping("/courses")
public class CourseController {

    private CourseService courseService;

    @Autowired
    public CourseController(CourseService courseService){
        this.courseService = courseService;
    }

    @GetMapping
    public String showCourses(Model theModel){
        List<Course> courseList = courseService.findAllCourses();
        theModel.addAttribute("courses", courseList);
        return "/courses/list-courses";
    }

    @GetMapping("/delete/{courseId}")
    public String deleteCourse(@PathVariable int courseId){
        courseService.deleteCourseById(courseId);
        return "redirect:/courses";
    }

    @GetMapping("/showFormAddCourse")
    public String showFormAddCourse(Model theModel){
        Course theCourse = new Course();
        theModel.addAttribute("course", theCourse);
        return "courses/course-form";
    }

    @PostMapping("/save")
    public String saveNewCourse(@ModelAttribute("course") Course theCourse){
        courseService.saveCourse(theCourse);
        return "redirect:/courses";
    }
}
