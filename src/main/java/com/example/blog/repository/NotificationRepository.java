package com.example.blog.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.example.blog.entity.Notification;
import com.example.blog.entity.User;

public interface NotificationRepository extends JpaRepository<Notification, Integer> {
	
	List<Notification> findBySubscriberOrderByCreatedAt(User subscriber); // 수신자를 최신순으로 가져올 수 O
	
}
