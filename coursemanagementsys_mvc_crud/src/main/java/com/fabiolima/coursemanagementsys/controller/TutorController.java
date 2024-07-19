package com.fabiolima.coursemanagementsys.controller;

import com.fabiolima.coursemanagementsys.entity.Course;
import com.fabiolima.coursemanagementsys.entity.Tutor;
import com.fabiolima.coursemanagementsys.service.CourseService;
import com.fabiolima.coursemanagementsys.service.TutorService;
import com.fasterxml.jackson.annotation.ObjectIdGenerators;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Controller
@RequestMapping("/tutors")
public class TutorController {

    private TutorService tutorService;
    private CourseService courseService;

    @Autowired
    public TutorController(TutorService theTutorService,
                           CourseService theCourseService){
        tutorService = theTutorService;
        courseService = theCourseService;
    }

    @GetMapping
    public String showTutor(Model theModel){
        // get the tutors from database
        List<Tutor> tutorsList = tutorService.findAllTutors();

        // add list of tutors to spring model
        theModel.addAttribute("tutors", tutorsList);
        return "/tutors/list-tutors";
    }

    @GetMapping("/showFormForAddTutor")
    public String showFormForAdd(Model theModel){//the object theTutor holds all the info obtained in modelattribute("tutor")

        Tutor theTutor = new Tutor();
        theModel.addAttribute("tutor", theTutor);
        //model attribute will bind "tutor" info obtained from the form to the  theTutor object

        return "tutors/tutor-form";
    }

    @PostMapping("/save")
    public String saveTutor(@ModelAttribute("tutor") Tutor theTutor){
        tutorService.saveTutor(theTutor);
        return "redirect:/tutors";
    }

    @GetMapping("/viewTutor/{tutorId}")
    public String viewTutor(@PathVariable int tutorId,
                            Model theModel){
        Tutor tempTutor = tutorService.findTutorById(tutorId);
        List<Course> courseList = tempTutor.getCourseList();
        theModel.addAttribute("tutor", tempTutor);
        theModel.addAttribute("tutorCourses", courseList);

        return "/tutors/tutor-view";
    }

    @GetMapping("/withdrawCourse/{tutorId}/{courseId}")
    public String withdrawCourse(@PathVariable int tutorId,
                                 @PathVariable int courseId){
        tutorService.withdrawCourseByTutorId(tutorId,courseId);
        return "redirect:/tutor/viewTutor/" + tutorId;
    }

    @GetMapping("/showAvailableCourses/{tutorId}")
    public String showAvailableCourses(@PathVariable int tutorId ,Model theModel){
        List<Course> courseList = courseService.findAllAvailableCoursesToTeach();
        Tutor tempTutor = tutorService.findTutorById(tutorId);
        theModel.addAttribute("availableCourses", courseList);
        theModel.addAttribute("tutor", tempTutor);
        return "/tutors/tutor-teach-course";
    }

    @GetMapping("/teach/{tutorId}/{courseId}")
    public String teachCourse(@PathVariable int tutorId,
                            @PathVariable int courseId){
        tutorService.enrollTutorInCourse(tutorId,courseId);
        return"redirect:/tutors/showAvailableCourses/" + tutorId;
    }
}