package com.anchoco.anchoco;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class TopController {
	
	@GetMapping("/top")
    public Model messages(Model model) {
		
		String message = "勉強しましょう！";
		
        model.addAttribute("message", message);

        return model;
    }
}
