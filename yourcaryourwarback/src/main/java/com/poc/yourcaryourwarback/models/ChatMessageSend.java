package com.poc.yourcaryourwarback.models;

import jakarta.persistence.Column;
import lombok.*;

import javax.validation.constraints.Size;
@Data
@AllArgsConstructor
public class ChatMessageSend {
    private String senderUsername;

    private String receiverUsername;

    @Size(max = 100)
    private String messageText;
}
