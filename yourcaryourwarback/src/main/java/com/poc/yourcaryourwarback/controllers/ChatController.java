package com.poc.yourcaryourwarback.controllers;


import com.poc.yourcaryourwarback.models.ChatMessage;
import com.poc.yourcaryourwarback.models.ChatMessageSend;
import com.poc.yourcaryourwarback.models.User;
import com.poc.yourcaryourwarback.models.responses.LoginResponse;
import com.poc.yourcaryourwarback.repository.MessageRepository;
import com.poc.yourcaryourwarback.services.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

@RestController
@CrossOrigin(origins = "http://localhost:4200")
@RequestMapping("/api")
public class ChatController {
    private final MessageRepository messageRepository;
    private final UserService userService;

    public ChatController(UserService userService, MessageRepository messageRepository ) {
        this.userService = userService;
        this.messageRepository = messageRepository;
    }

    @PostMapping("/messages")
    public ResponseEntity<List<String>> getMessages(@RequestBody ChatMessageSend message) {
        User sender = userService.findByUsernameOrEmail(message.getSenderUsername());
        User receiver = userService.findByUsernameOrEmail(message.getReceiverUsername());

        if(sender == null || receiver == null) {
            return ResponseEntity.status(404).build();
        }

        List<ChatMessage> chatMessages =  messageRepository.findAllChatMessage(sender.getId(), receiver.getId());
        return  ResponseEntity.ok(chatMessages.stream().map(ChatMessage::getMessageText).collect(Collectors.toList()));
    }

    @PostMapping("/sendmessage")
    public ResponseEntity<List<String>> sendMessage(@RequestBody ChatMessageSend message) {
        User sender = userService.findByUsernameOrEmail(message.getSenderUsername());
        User receiver = userService.findByUsernameOrEmail(message.getReceiverUsername());

        if(sender == null || receiver == null) {
            return ResponseEntity.status(404).build();
        }

        messageRepository.save(ChatMessage.builder()
                .SenderID(sender.getId())
                .ReceiverID(receiver.getId())
                .MessageText(message.getMessageText())
                .build());

        List<ChatMessage> chatMessages =  messageRepository.findAllChatMessage(sender.getId(), receiver.getId());
        return  ResponseEntity.ok(chatMessages.stream().map(ChatMessage::getMessageText).collect(Collectors.toList()));
    }
}
