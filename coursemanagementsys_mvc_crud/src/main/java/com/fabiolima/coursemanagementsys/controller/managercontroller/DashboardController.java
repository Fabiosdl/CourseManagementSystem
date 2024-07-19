package com.fabiolima.coursemanagementsys.controller.managercontroller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/dashboard")
public class DashboardController {


    @GetMapping
    public String showDashBoard(){
        return "dashboard";
    }
}
