package com.fabiolima.coursemanagementsys.controller.registrationcontroller;

import com.fabiolima.coursemanagementsys.entity.User;
import com.fabiolima.coursemanagementsys.service.UserService;
import com.fabiolima.coursemanagementsys.user.WebUser;
import jakarta.servlet.http.HttpSession;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.propertyeditors.StringTrimmerEditor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.*;

import java.util.logging.Logger;

@Controller
@RequestMapping("/register")
public class RegistrationController {

    private Logger logger = Logger.getLogger(getClass().getName());
    private UserService userService;

    @Autowired
    public RegistrationController(UserService theUserService){
        this.userService = theUserService;
    }

    @InitBinder
    public void initBinder(WebDataBinder dataBinder) {

        StringTrimmerEditor stringTrimmerEditor = new StringTrimmerEditor(true);

        dataBinder.registerCustomEditor(String.class, stringTrimmerEditor);
    }

    @GetMapping("/showRegistrationForm")
    public String showRegistrationForm(Model theModel){

        theModel.addAttribute("webUser", new WebUser());

        return "/register/registration-form";
    }

    @PostMapping("/processRegistrationForm")
    public String processRegistrationForm(
            @Valid @ModelAttribute("webUser") WebUser theWebUser,
            BindingResult theBindingResult,
            HttpSession session, Model theModel) {

        String userName = theWebUser.getUserName();
        logger.info("Processing registration form for: " + userName);

        // form validation
        if (theBindingResult.hasErrors()){
            return "register/registration-form";
        }

        // check the database if user already exists
        User existing = userService.findByUserName(userName);
        if (existing != null){
            theModel.addAttribute("webUser", new WebUser());
            theModel.addAttribute("registrationError", "User name already exists.");

            logger.warning("User name already exists.");
            return "register/registration-form";
        }

        // create user account and store in the databse
        userService.save(theWebUser);

        logger.info("Successfully created user: " + userName);

        // place user in the web http session for later use
        session.setAttribute("user", theWebUser);

        return "register/registration-confirmation";
    }
}