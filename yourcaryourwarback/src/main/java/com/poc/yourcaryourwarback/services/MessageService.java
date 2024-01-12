package com.poc.yourcaryourwarback.services;

import com.poc.yourcaryourwarback.models.ChatMessage;
import com.poc.yourcaryourwarback.models.ChatMessageSend;
import com.poc.yourcaryourwarback.models.User;
import com.poc.yourcaryourwarback.repository.MessageRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class MessageService {

    private final MessageRepository messageRepository;

    public List<ChatMessage> GetAllMessage(int senderId, int receiverId){

        List<ChatMessage> chatMessages =  messageRepository.findAllChatMessage(senderId, receiverId);
        chatMessages.addAll(messageRepository.findAllChatMessage(receiverId, senderId));
        return chatMessages.stream()
                .sorted(Comparator.comparing(ChatMessage::getTimestamp))
                .collect(Collectors.toList());
    }

    public void save(ChatMessage message){
        messageRepository.save(message);
    }

}
