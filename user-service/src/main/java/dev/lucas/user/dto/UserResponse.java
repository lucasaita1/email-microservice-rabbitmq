package dev.lucas.user.dto;

import lombok.Builder;

import java.util.UUID;

@Builder
public record UserResponse(UUID userId,
                           String username,
                           String email) {
}
