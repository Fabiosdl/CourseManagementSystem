package com.fabiolima.coursemanagementsys.controller.logincontroller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/login")
public class Login {

    @GetMapping
    public String login(){
        return "/login/login_page";
    }
}
