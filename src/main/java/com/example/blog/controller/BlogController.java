package com.example.blog.controller;

import java.time.LocalDateTime;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;

import com.example.blog.entity.Board;
import com.example.blog.entity.Subscribe;
import com.example.blog.entity.User;
import com.example.blog.repository.BoardRepository;
import com.example.blog.repository.SubscribeRepository;
import com.example.blog.repository.UserRepository;

import jakarta.servlet.http.HttpSession;
import jakarta.transaction.Transactional;

@Controller
public class BlogController {
	
	@Autowired
	BoardRepository boardRepository;
	
	@Autowired
	UserRepository userRepository;
	
	@Autowired
	SubscribeRepository subscribeRepository;
	
	@GetMapping("/")
	public String boardList(Model model) {
		List<Board> boardList = boardRepository.findAll();
		model.addAttribute("list", boardList);
		return "index";
	}
	
	@GetMapping("/userList")
	public String userList(Model model) {
		List<User> userList = userRepository.findAll();
		model.addAttribute("users", userList);
		return "userList";
	}
	
	@GetMapping("/blog/{id}")
	public String blogBoard(
	    @PathVariable("id") Integer id, 
	    HttpSession session,
	    Model model) {
	    
	    User loginUser = (User) session.getAttribute("user");
	    User blogUser = userRepository.findById(id).orElseThrow();
	    List<Board> boardList = boardRepository.findByWriter(blogUser);
	    
	    boolean isSubscribed = false;
	    boolean checkMe = false;
	    
	    if (loginUser != null) {
	        isSubscribed = subscribeRepository.existsBySubscribedToAndSubscriber(blogUser, loginUser);
	        if (id.equals(loginUser.getId())) {
	            checkMe = true;
	        }
	    }
	    
	    model.addAttribute("checkMe", checkMe);
	    model.addAttribute("user", blogUser);
	    model.addAttribute("board", boardList);
	    model.addAttribute("isSubscribed", isSubscribed); // 이 값 전달
	    return "blogBoard";
	}
	
	@Transactional
	@PostMapping("/blog/{id}/subscribe")
	public String subscribe(
	    @PathVariable("id") Integer id, 
	    HttpSession session) {

	    User loginUser = (User) session.getAttribute("user");
	    if (loginUser == null) {
	        return "redirect:/login"; // 로그인 안 했으면 로그인페이지
	    }

	    User blogUser = userRepository.findById(id).orElseThrow();
	    
	    boolean isSubscribed = subscribeRepository.existsBySubscribedToAndSubscriber(blogUser, loginUser);

	    if(isSubscribed) {
	    	subscribeRepository.deleteBySubscribedToAndSubscriber(blogUser, loginUser);
	    }else {
	        Subscribe sub = Subscribe.builder()
	                .subscriber(loginUser)
	                .subscribedTo(blogUser)
	                .subscribedAt(LocalDateTime.now())
	                .build();
	        subscribeRepository.save(sub);
	    }
	    return "redirect:/blog/" + id;
	}
	
	@GetMapping("/mypage")
	public String myPage(HttpSession session, Model model) {
		User loginUser = (User)session.getAttribute("user");
		if(loginUser == null) {
			return "redirect:/login";
		}
		List<Subscribe> mySubscriptions = subscribeRepository.findBySubscriber(loginUser); // 내가 구독한 목록
		model.addAttribute("subscriptions", mySubscriptions);
		model.addAttribute("user", loginUser);
		return "mypage";
	}
	
	@GetMapping("/goRegist")
	public String goRegist(HttpSession session) {
		User loginUser = (User) session.getAttribute("user");
		if(loginUser == null) {
			return "redirect:/login";
		}
		return "registForm";
	}
	
	@PostMapping("/registProc")
	public String registProc(Board board) {
		
		boardRepository.save(board);
		
		return "redirect:/";
	}
}




