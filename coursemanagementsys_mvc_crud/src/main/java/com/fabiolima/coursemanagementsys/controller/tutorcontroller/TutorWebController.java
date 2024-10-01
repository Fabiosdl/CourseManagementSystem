package com.fabiolima.coursemanagementsys.controller.tutorcontroller;

import com.fabiolima.coursemanagementsys.entity.Tutor;
import com.fabiolima.coursemanagementsys.service.TutorService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/tutorWebPage")
public class TutorWebController {

    private TutorService tutorService;

    public TutorWebController(TutorService tutorService){
        this.tutorService = tutorService;
    }

    @GetMapping("/{tutorId}")
    public String showTutor(@PathVariable int tutorId, Model theModel){
        Tutor tempTutor = tutorService.findTutorById(tutorId);
        theModel.addAttribute("tutor", tempTutor);

        return "tutor-webpage/tutor-dashboard";
    }
}
