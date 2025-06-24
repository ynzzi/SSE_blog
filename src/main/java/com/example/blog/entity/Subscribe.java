package com.example.blog.entity;

import java.time.LocalDateTime;

import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Table(name = "subscribe")
@Getter @Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Subscribe {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id")
    private User subscribedTo; // 구독하는 대상 유저

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "subscriber_user_id")
    private User subscriber; // 구독한 사용자

    private LocalDateTime subscribedAt = LocalDateTime.now();
}