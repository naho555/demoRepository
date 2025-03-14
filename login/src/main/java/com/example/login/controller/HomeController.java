package com.example.login.controller;

import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class HomeController {

    @GetMapping("/home")
    public String showHomePage(@AuthenticationPrincipal UserDetails userDetails, Model model) {
        model.addAttribute("email", userDetails.getUsername());
        return "home"; // templates/home.html を表示
    }
}
