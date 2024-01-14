package com.poc.yourcaryourwarback.controllers;


import com.poc.yourcaryourwarback.models.ChatMessage;
import com.poc.yourcaryourwarback.models.ChatMessageSend;
import com.poc.yourcaryourwarback.models.User;
import com.poc.yourcaryourwarback.services.MessageService;
import com.poc.yourcaryourwarback.services.UserService;
import org.springframework.http.ResponseEntity;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.handler.annotation.SendTo;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/api")
public class ChatController {
    private final MessageService messageService;
    private final UserService userService;

    public ChatController(UserService userService, MessageService messageService ) {
        this.userService = userService;
        this.messageService = messageService;
    }

    @GetMapping("/messages/{username}")
    public ResponseEntity<List<ChatMessageSend>> getMessages(@PathVariable String username) {
        User user = userService.findByUsernameOrEmail(username);

        if(user == null) {
            return ResponseEntity.status(404).build();
        }

        List<ChatMessage> chatMessages = messageService.GetAllMessage(user.getId());

        return ResponseEntity.ok().body(chatMessages.stream()
                .map(chatMessage -> {
                    return new ChatMessageSend(userService.getUsernameById(chatMessage.getSenderID()),
                            userService.getUsernameById(chatMessage.getReceiverID()), chatMessage.getMessageText());
                }).collect(Collectors.toList()));
    }

}
