package dev.lucas.email.dto;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import java.util.UUID;


public record EmailDto(UUID userId,
                       String to,
                       String subject,
                       String body){
}
