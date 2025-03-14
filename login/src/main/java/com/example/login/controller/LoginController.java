package com.example.login.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.example.login.service.UserService;

@Controller
public class LoginController {

    private final UserService userService;

    public LoginController(UserService userService) {
        this.userService = userService;
    }

    @GetMapping("/login")
    public String showLoginPage() {
        return "login";
    }

    @PostMapping("/login")
    public String login(@RequestParam String email, @RequestParam String password, Model model) {
        boolean isAuthenticated = userService.authenticate(email, password);

        if (isAuthenticated) {
            return "redirect:/home"; // ログイン成功
        } else {
            model.addAttribute("error", "メールアドレスまたはパスワードが間違っています。");
            return "login"; // ログイン失敗
        }
    }
}
