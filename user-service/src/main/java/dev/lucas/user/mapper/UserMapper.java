package dev.lucas.user.mapper;

import dev.lucas.user.dto.UserRequest;
import dev.lucas.user.dto.UserResponse;
import dev.lucas.user.entity.UserEntity;
import lombok.experimental.UtilityClass;

@UtilityClass
public class UserMapper {

    public static UserResponse toResponse(UserEntity user) {
        return new UserResponse(
                user.getUserId(),
                user.getUsername(),
                user.getEmail()
        );
    }

    public static UserEntity toEntity(UserRequest request) {
        return UserEntity.builder()
                .username(request.username())
                .email(request.email())
                .password(request.password())
                .build();
    }
}
