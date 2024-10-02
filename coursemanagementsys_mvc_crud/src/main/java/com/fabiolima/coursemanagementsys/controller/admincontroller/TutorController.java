package com.fabiolima.coursemanagementsys.controller.admincontroller;

import com.fabiolima.coursemanagementsys.entity.Course;
import com.fabiolima.coursemanagementsys.entity.Tutor;
import com.fabiolima.coursemanagementsys.entity.User;
import com.fabiolima.coursemanagementsys.service.CourseService;
import com.fabiolima.coursemanagementsys.service.TutorService;
import com.fabiolima.coursemanagementsys.service.UserService;
import com.fabiolima.coursemanagementsys.user.WebUser;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.logging.Logger;

@Controller
@RequestMapping("/admin/tutors")
public class TutorController {

    private Logger logger = Logger.getLogger(getClass().getName());

    private TutorService tutorService;
    private CourseService courseService;
    private UserService userService;

    @Autowired
    public TutorController(TutorService theTutorService,
                           CourseService theCourseService,
                            UserService theUserService){
        tutorService = theTutorService;
        courseService = theCourseService;
        userService = theUserService;
    }

    @GetMapping
    public String showTutor(Model theModel){
        // get the tutors from database
        List<Tutor> tutorsList = tutorService.findAllTutors();

        // add list of tutors to spring model
        theModel.addAttribute("tutors", tutorsList);
        return "admin/tutors/list-tutors";
    }

    @GetMapping("/showFormForAddTutor")
    public String showFormForAdd(Model theModel){//the object theTutor holds all the info obtained in modelattribute("tutor")

        WebUser webUser = new WebUser();
        webUser.setRole("TUTOR");
        theModel.addAttribute("tutor", webUser);
        //model attribute will bind "tutor" info obtained from the form to the  theTutor object

        return "admin/tutors/register/tutor-form";
    }

    @PostMapping("/save")
    public String saveTutor(@Valid @ModelAttribute("tutor") WebUser theTutor,
                            BindingResult bindingResult,
                            Model theModel){

        String userName = theTutor.getUserName();
        logger.info("Processing registration form for: " + userName);

        if(bindingResult.hasErrors()) return "redirect:/admin/tutors/showFormForAddTutor";

        User existingUser = userService.findByUserName(userName);

        if(existingUser != null && !existingUser.getRole().equals(theTutor.getRole())){
            theModel.addAttribute("webUser", new WebUser());
            theModel.addAttribute("registrationError", "User name already exists.");

            logger.warning("User name already exists.");
            return "redirect:/admin/tutors/showFormForAddTutor";
        }

        userService.save(theTutor);
        logger.info("Successfully created user: " + userName);

        return "redirect:/admin/tutors";


    }

    @GetMapping("/viewTutor/{tutorId}")
    public String viewTutor(@PathVariable int tutorId,
                            Model theModel){
        Tutor tempTutor = tutorService.findTutorById(tutorId);
        List<Course> courseList = tempTutor.getCourseList();
        theModel.addAttribute("tutor", tempTutor);
        theModel.addAttribute("tutorCourses", courseList);

        return "admin/tutors/tutor-view";
    }

    @GetMapping("/withdrawCourse/{tutorId}/{courseId}")
    public String withdrawCourse(@PathVariable int tutorId,
                                 @PathVariable int courseId){
        tutorService.withdrawCourseByTutorId(tutorId,courseId);
        return "redirect:/admin/tutors/viewTutor/" + tutorId;
    }

    @GetMapping("/showAvailableCourses/{tutorId}")
    public String showAvailableCourses(@PathVariable int tutorId ,Model theModel){
        List<Course> courseList = courseService.findAllAvailableCoursesToTeach();
        Tutor tempTutor = tutorService.findTutorById(tutorId);
        theModel.addAttribute("availableCourses", courseList);
        theModel.addAttribute("tutor", tempTutor);
        return "admin/tutors/tutor-teach-course";
    }

    @GetMapping("/teach/{tutorId}/{courseId}")
    public String teachCourse(@PathVariable int tutorId,
                            @PathVariable int courseId){
        tutorService.enrollTutorInCourse(tutorId,courseId);
        return"redirect:/admin/tutors/showAvailableCourses/" + tutorId;
    }

    @GetMapping("/delete/{tutorId}")
    public String deleteTutor(@PathVariable int tutorId){
        //tutorService.deleteTutorById(tutorId);
        tutorService.deleteTutorById(tutorId);
        return "redirect:/admin/tutors";
    }
}