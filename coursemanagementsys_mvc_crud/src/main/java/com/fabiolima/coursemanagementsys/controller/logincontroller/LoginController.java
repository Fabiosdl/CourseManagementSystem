package com.fabiolima.coursemanagementsys.controller.logincontroller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/")
public class LoginController {

    @GetMapping
    public String login(){
        return "/login/login-page";
    }

    @GetMapping("/studentWebPage/studentLogin")
    public String showStudentLogin(){
        return "/login/student-login";
    }
}
