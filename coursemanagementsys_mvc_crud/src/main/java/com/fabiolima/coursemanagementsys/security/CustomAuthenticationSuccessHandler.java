package com.fabiolima.coursemanagementsys.security;

import java.io.IOException;

import com.fabiolima.coursemanagementsys.entity.User;
import com.fabiolima.coursemanagementsys.service.UserService;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;

import org.springframework.security.core.Authentication;
import org.springframework.security.web.authentication.AuthenticationSuccessHandler;
import org.springframework.stereotype.Component;



@Component
public class CustomAuthenticationSuccessHandler implements AuthenticationSuccessHandler {

    private UserService userService;

    public CustomAuthenticationSuccessHandler(UserService theUserService) {
        userService = theUserService;
    }

    @Override
    public void onAuthenticationSuccess(HttpServletRequest request, HttpServletResponse response, Authentication authentication)
            throws IOException, ServletException {

        System.out.println("In customAuthenticationSuccessHandler");

        String userName = authentication.getName();

        System.out.println("userName=" + userName);

        User theUser = userService.findByUserName(userName);

        // now place in the session
        HttpSession session = request.getSession();
        session.setAttribute("user", theUser);

        // check what is the role of the user
        String redirectURL = new String();
        switch (theUser.getRole()){
            case "STUDENT" :
                redirectURL = "/studentWebPage/" + theUser.getId();
                break;
            case "TUTOR" :
                redirectURL = "/tutorWebPage/" + theUser.getId();
                break;
            case "ADMIN" :
                redirectURL = "/admin/dashboard";
        }

        // forward to home page
        response.sendRedirect(request.getContextPath() + redirectURL);
    }

}
