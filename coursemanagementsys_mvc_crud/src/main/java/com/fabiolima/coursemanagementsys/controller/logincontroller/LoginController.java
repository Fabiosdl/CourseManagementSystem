package com.fabiolima.coursemanagementsys.controller.logincontroller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
public class LoginController {

    @GetMapping("/showLoginPage") //it has to match up with the appSecurity file login form
    public String login(){
        return "login/login-page";
    }


}
