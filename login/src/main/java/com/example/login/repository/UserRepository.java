package com.example.login.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.example.login.model.User;

public interface UserRepository extends JpaRepository<User, Long> {
    Optional<User> findByEmail(String email); // 🔹 メールアドレスでユーザーを検索
}
