package com.poc.yourcaryourwarback.controllers;

import com.poc.yourcaryourwarback.models.ChatMessage;
import com.poc.yourcaryourwarback.models.ChatMessageSend;
import com.poc.yourcaryourwarback.models.User;
import com.poc.yourcaryourwarback.services.MessageService;
import com.poc.yourcaryourwarback.services.UserService;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.handler.annotation.SendTo;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.messaging.simp.SimpMessagingTemplate;

@Controller
public class WebSocketController {

    private final MessageService messageService;
    private final UserService userService;
    private final SimpMessagingTemplate messagingTemplate;

    public WebSocketController(UserService userService, MessageService messageService, SimpMessagingTemplate messagingTemplate ) {
        this.userService = userService;
        this.messageService = messageService;
        this.messagingTemplate = messagingTemplate;
    }

    @MessageMapping("/chat")
    //@SendTo("/topic/messages")
    public ChatMessageSend handleChatMessage(ChatMessageSend message) {
        User sender = userService.findByUsernameOrEmail(message.getSenderUsername());
        User receiver = userService.findByUsernameOrEmail(message.getReceiverUsername());

        if(sender == null || receiver == null) {
            return null;
        }
        messageService.save(new ChatMessage(sender.getId(), receiver.getId(), message.getMessageText()));

        String destination;
        if (sender.getUsername().equals("sav")) {
            destination = "/topic/" + receiver.getUsername() + "/messages";
        } else {
            destination = "/topic/" + sender.getUsername() + "/messages";
        }

        messagingTemplate.convertAndSend("/topic/messages", message);
        
        messagingTemplate.convertAndSend(destination, message);

        return message;
    }
}
