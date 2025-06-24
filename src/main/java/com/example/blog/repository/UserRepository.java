package com.example.blog.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.example.blog.entity.User;

public interface UserRepository extends JpaRepository<User, Integer> {
	User findByUsernameAndPassword(String username, String password);
}
