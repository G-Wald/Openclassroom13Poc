package com.poc.yourcaryourwarback.repository;

import com.poc.yourcaryourwarback.models.ChatMessage;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface MessageRepository extends JpaRepository<ChatMessage, Integer> {
    @Query("SELECT m FROM ChatMessage m WHERE m.SenderID  = :senderId AND m.ReceiverID  = :receiverId")
    List<ChatMessage> findAllChatMessage(int senderId, int receiverId);
}
