package com.example.login.controller;

import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.example.login.service.UserService;

@Controller
public class UserController {

    private final UserService userService;

    public UserController(UserService userService) {
        this.userService = userService;
    }

    @GetMapping("/profile")
    public String showProfilePage(@AuthenticationPrincipal UserDetails userDetails, Model model) {
        model.addAttribute("email", userDetails.getUsername());
        return "profile"; // templates/profile.html を表示
    }

    @PostMapping("/update-password")
    public String updatePassword(@AuthenticationPrincipal UserDetails userDetails,
                                 @RequestParam String newPassword, Model model) {
        boolean isUpdated = userService.updateUser(userDetails.getUsername(), newPassword);

        if (isUpdated) {
            model.addAttribute("message", "パスワードが更新されました。");
        } else {
            model.addAttribute("error", "パスワードの更新に失敗しました。");
        }
        return "profile";
    }

    @PostMapping("/delete-account")
    public String deleteAccount(@AuthenticationPrincipal UserDetails userDetails) {
        userService.deleteUser(userDetails.getUsername());
        return "redirect:/logout"; // 削除後はログアウト
    }
}
