package com.example.blog.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.example.blog.entity.Board;
import com.example.blog.entity.User;

public interface BoardRepository extends JpaRepository<Board, Integer> {
	List<Board> findByWriter(User writer);
}
