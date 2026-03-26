package com.example;

import org.springframework.ui.Model;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
public class LoginController {

    @GetMapping("/")
    public String home() {
        return "redirect:/login";
    }

    @GetMapping("/login")
    public String loginPage(@RequestParam(required = false) String error,
                            @RequestParam(required = false) String success,
                            Model model) {
        if (error != null) {
            model.addAttribute("error", true);
        }
        if (success != null) {
            model.addAttribute("success", true);
        }
        return "hello";
    }
}