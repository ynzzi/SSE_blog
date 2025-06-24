package com.example.blog.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;

import com.example.blog.entity.User;
import com.example.blog.repository.UserRepository;

import jakarta.servlet.http.HttpSession;

@Controller
public class JoinController {
	
	@Autowired
	UserRepository userRepository;
	
	@GetMapping("/login")
	public String loginForm() {
		return "login";
	}
	
	@PostMapping("/loginProc")
	public String loginProc(User user, HttpSession session) {
		
		User loginUser = userRepository.findByUsernameAndPassword(user.getUsername(), user.getPassword());
		if(loginUser != null) {
			session.setAttribute("user", loginUser);
			return "redirect:/";
		}

		return "login";
	}
}
