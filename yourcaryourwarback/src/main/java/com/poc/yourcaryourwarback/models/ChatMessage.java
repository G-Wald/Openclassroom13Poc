package com.poc.yourcaryourwarback.models;

import jakarta.persistence.*;
import lombok.*;
import org.hibernate.annotations.UpdateTimestamp;
import org.springframework.data.annotation.CreatedDate;

import javax.validation.constraints.Size;
import java.time.LocalDateTime;

@Entity
@Table(name = "chatmessages")
@Data
@EqualsAndHashCode(of = {"id"})
@NoArgsConstructor
@RequiredArgsConstructor
@AllArgsConstructor
@Builder
@ToString
public class ChatMessage {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int MessageID;

    @NonNull
    @Column(name = "SenderID")
    private int SenderID;

    @NonNull
    @Column(name = "ReceiverID")
    private int ReceiverID;

    @NonNull
    @Size(max = 100)
    @Column(name = "MessageText")
    private String MessageText;

    @CreatedDate
    @Column(name = "Timestamp")
    private LocalDateTime Timestamp;
}
