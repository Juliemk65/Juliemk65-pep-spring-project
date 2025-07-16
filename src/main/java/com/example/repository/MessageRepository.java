package com.example.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.example.entity.Message;

import java.util.List;

public interface MessageRepository extends JpaRepository <Message, Integer>{

    Optional<Message> findByPostedBy(Integer postedBy);

    boolean existsByPostedBy(Integer postedBy);

    Optional<Message> findByMessageId(Integer messageId);

    List<Message> findAllByPostedBy(Integer postedBy);
    
    
}
