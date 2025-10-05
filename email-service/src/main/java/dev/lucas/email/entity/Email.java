package dev.lucas.email.entity;

import dev.lucas.email.enums.EmailStatus;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.time.LocalDateTime;
import java.util.UUID;

@Document
@Data
@AllArgsConstructor
@NoArgsConstructor
public class Email {

    
    @Id
    private String id;
    private String userId;
    private String from;
    private String to;
    private String subject;
    private String body;
    private LocalDateTime sentAt;
    private EmailStatus status;

}
