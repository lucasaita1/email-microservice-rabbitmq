package dev.lucas.email.dto;


public record EmailDto(String userId,
                       String to,
                       String subject,
                       String body){
}
