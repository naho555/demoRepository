package com.example.login.service;

import java.util.Collections;
import java.util.Optional;

import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import com.example.login.model.User;
import com.example.login.repository.UserRepository;


@Service
public class UserService implements UserDetailsService {

    private final UserRepository userRepository;
    private final BCryptPasswordEncoder passwordEncoder;

    public UserService(UserRepository userRepository) {
        this.userRepository = userRepository;
        this.passwordEncoder = new BCryptPasswordEncoder();
    }

    public boolean registerUser(String email, String password) {
        Optional<User> existingUser = userRepository.findByEmail(email);
        if (existingUser.isPresent()) {
            return false; // すでに登録済み
        }

        User user = new User();
        user.setEmail(email);
        user.setPassword(passwordEncoder.encode(password));
        userRepository.save(user);

        return true;
    }

    public boolean authenticate(String email, String password) {
        Optional<User> user = userRepository.findByEmail(email);
        return user.isPresent() && passwordEncoder.matches(password, user.get().getPassword());
    }

    @Override
    public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {
        User user = userRepository.findByEmail(email)
                .orElseThrow(() -> new UsernameNotFoundException("ユーザーが見つかりません: " + email));

        return org.springframework.security.core.userdetails.User
                .withUsername(user.getEmail())
                .password(user.getPassword())
                .roles("USER") // 権限を適切に設定
                .build();
    }


    public boolean isEmailTaken(String email) {
        return userRepository.findByEmail(email).isPresent();
    }

    // 🔹 ユーザー情報の更新
    public boolean updateUser(String email, String newPassword) {
        Optional<User> userOpt = userRepository.findByEmail(email);
        if (userOpt.isPresent()) {
            User user = userOpt.get();
            user.setPassword(passwordEncoder.encode(newPassword)); // 新しいパスワードをハッシュ化
            userRepository.save(user);
            return true;
        }
        return false;
    }

    // 🔹 ユーザー削除機能
    public boolean deleteUser(String email) {
        Optional<User> userOpt = userRepository.findByEmail(email);
        if (userOpt.isPresent()) {
            userRepository.delete(userOpt.get());
            return true;
        }
        return false;
    }
}
