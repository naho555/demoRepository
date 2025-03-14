package com.example.login.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.example.login.service.UserService;

@Controller
public class RegisterController {

    private final UserService userService;

    public RegisterController(UserService userService) {
        this.userService = userService;
    }

    @GetMapping("/register")
    public String showRegisterPage() {
        return "register";
    }

    @PostMapping("/register")
    public String registerUser(@RequestParam String email, @RequestParam String password, Model model) {
        if (userService.isEmailTaken(email)) {
            model.addAttribute("error", "このメールアドレスは既に使用されています。");
            return "register"; // 登録失敗時に register.html を再表示
        }

        userService.registerUser(email, password);
        return "redirect:/login"; // 登録成功後にログインページへリダイレクト
    }
}
