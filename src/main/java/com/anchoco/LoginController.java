package com.anchoco;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class LoginController {
	
	@GetMapping("/login")
    public Model messages(Model model) {
		
		String message = "勉強しましょう！";
		
        model.addAttribute("message", message);

        return model;
    }
}
