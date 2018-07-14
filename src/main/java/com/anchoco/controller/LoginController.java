package com.anchoco.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import com.anchoco.service.SetEC_Mark;

@Controller
public class LoginController {
    
    @Autowired
    SetEC_Mark setEC_Mark;

    @GetMapping("/login")
    public Model initLogin(Model model) {

        String str = "";
        model.addAttribute("message", str);

        return model;
    }
    @PostMapping("/index")
    public Model initIndex(Model model) {

        String message = "Hello World!";

        model.addAttribute("message", message);

        return model;
    }
    @RequestMapping(value="/address")
    public Model initAddress(Model model) {

        String message = "Hello World!";

        model.addAttribute("message", message);

        return model;
    }
    
//    @RequestMapping(value="/SetEC_Mark", method=RequestMethod.POST)
//    public String initSetEC_Mark(Model model) {
//        
//        try {
//            setEC_Mark.markFlow();
//        } catch (Exception e) {
//            // TODO Auto-generated catch block
//            e.printStackTrace();
//        }
//
//        String message = "Hello World!";
//
//        model.addAttribute("message", message);
//
//        return "/review";
//    }
}
