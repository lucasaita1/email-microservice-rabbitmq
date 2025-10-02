package dev.lucas.email.dto;

import java.util.UUID;

public record EmailDto(UUID UserId,
                       String to,
                       String subject,
                       String body){
}
