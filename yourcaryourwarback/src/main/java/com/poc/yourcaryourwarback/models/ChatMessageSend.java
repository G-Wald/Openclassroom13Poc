package com.poc.yourcaryourwarback.models;

import jakarta.persistence.Column;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NonNull;

import javax.validation.constraints.Size;
@Data
@AllArgsConstructor
public class ChatMessageSend {
    private String SenderUsername;

    private String ReceiverUsername;

    @Size(max = 100)
    private String MessageText;
}
