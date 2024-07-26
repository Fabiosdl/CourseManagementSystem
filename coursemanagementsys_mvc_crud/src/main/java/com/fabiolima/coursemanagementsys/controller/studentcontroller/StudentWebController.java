package com.fabiolima.coursemanagementsys.controller.studentcontroller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/studentWebPage")
public class StudentWebController {

    @GetMapping("/home")
    public String showHome(){
        return "home";
    }

}
