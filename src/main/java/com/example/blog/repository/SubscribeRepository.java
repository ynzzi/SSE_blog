package com.example.blog.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.example.blog.entity.Subscribe;
import com.example.blog.entity.User;

public interface SubscribeRepository extends JpaRepository<Subscribe, Integer> {
	
	List<Subscribe> findBySubscriber(User user); // 구독한 유저 리스트

    List<Subscribe> findBySubscribedTo(User user); // 내가 구독받은 유저 리스트
    
    boolean existsBySubscribedToAndSubscriber(User subscribedTo, User subscriber);

    void deleteBySubscribedToAndSubscriber(User subscribedTo, User subscriber);

}
