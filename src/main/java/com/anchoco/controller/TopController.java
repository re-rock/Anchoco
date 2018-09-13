package com.anchoco.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.servlet.ModelAndView;

import com.anchoco.form.LoginForm;
import com.anchoco.service.LoginService;

@Controller
public class TopController {

    private static final String URL_TOP = "/top";

    @Autowired
    LoginService loginService;

    @PostMapping("/top")
    public ModelAndView initTop(@ModelAttribute LoginForm loginform) {

        // Login Check
        if (!loginService.checkLoginUser(loginform)) {
            throw new RuntimeException();
        }

        return new ModelAndView(URL_TOP);

    }
}