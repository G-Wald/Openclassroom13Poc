package com.poc.yourcaryourwarback.controllers;


import com.poc.yourcaryourwarback.models.ChatMessage;
import com.poc.yourcaryourwarback.models.ChatMessageSend;
import com.poc.yourcaryourwarback.models.User;
import com.poc.yourcaryourwarback.models.responses.LoginResponse;
import com.poc.yourcaryourwarback.repository.MessageRepository;
import com.poc.yourcaryourwarback.services.MessageService;
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
    private final MessageService messageService;
    private final UserService userService;

    public ChatController(UserService userService, MessageService messageService ) {
        this.userService = userService;
        this.messageService = messageService;
    }

    @PostMapping("/messages")
    public ResponseEntity<List<ChatMessageSend>> getMessages(@RequestBody ChatMessageSend message) {
        User sender = userService.findByUsernameOrEmail(message.getSenderUsername());
        User receiver = userService.findByUsernameOrEmail(message.getReceiverUsername());

        if(sender == null || receiver == null) {
            return ResponseEntity.status(404).build();
        }

        List<ChatMessage> chatMessages = messageService.GetAllMessage(sender.getId(), receiver.getId());

        return ResponseEntity.ok().body(chatMessages.stream()
                .map(chatMessage -> {
                    return new ChatMessageSend(userService.getUsernameById(chatMessage.getSenderID()),
                            userService.getUsernameById(chatMessage.getReceiverID()), chatMessage.getMessageText());
                }).collect(Collectors.toList()));
    }

    @PostMapping("/sendmessage")
    public ResponseEntity<List<ChatMessageSend>> sendMessage(@RequestBody ChatMessageSend message) {
        User sender = userService.findByUsernameOrEmail(message.getSenderUsername());
        User receiver = userService.findByUsernameOrEmail(message.getReceiverUsername());

        if(sender == null || receiver == null) {
            return ResponseEntity.status(404).build();
        }

        messageService.save(new ChatMessage(sender.getId(), receiver.getId(), message.getMessageText()));

        List<ChatMessage> chatMessages = messageService.GetAllMessage(sender.getId(), receiver.getId());

        return ResponseEntity.ok().body(chatMessages.stream()
                .map(chatMessage -> new ChatMessageSend(userService.getUsernameById(chatMessage.getSenderID()),
                        userService.getUsernameById(chatMessage.getReceiverID()), chatMessage.getMessageText())).collect(Collectors.toList()));
    }
}
