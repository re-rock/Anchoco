package com.anchoco.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;

@Controller
public class LoginController {

    @GetMapping("/login")
    public Model messages(Model model) {
        String message = "Hello World!";
        model.addAttribute("message", message);
        return model;
    }

    @PostMapping("/top")
    public Model initTop(Model model) {

	return model;
    }
}