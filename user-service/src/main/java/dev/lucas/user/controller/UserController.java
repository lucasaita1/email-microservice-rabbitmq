package dev.lucas.user.controller;

import dev.lucas.user.dto.UserRequest;
import dev.lucas.user.dto.UserResponse;
import dev.lucas.user.entity.UserEntity;
import dev.lucas.user.mapper.UserMapper;
import dev.lucas.user.service.UserService;
import lombok.RequiredArgsConstructor;

import org.springframework.beans.BeanUtils;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;


@RestController
@RequestMapping("/users")
@RequiredArgsConstructor
public class UserController {

    private final UserService userService;

    @PostMapping("/create")
    ResponseEntity<UserResponse> saveUser(@RequestBody UserRequest user) {
        var userEntity = new  UserEntity();
        BeanUtils.copyProperties(user, userEntity);
        userService.saveAndPublish(userEntity);
        var userResponse = new UserResponse(
                userEntity.getUserId(),
                userEntity.getUsername(),
                userEntity.getEmail());
        BeanUtils.copyProperties(userEntity, userResponse);
        return ResponseEntity.status(HttpStatus.CREATED).body(userResponse);

    }
    @GetMapping
    public ResponseEntity<List<UserResponse>> getAllUsers() {
        List<UserEntity> entity = userService.findAll();
        List<UserResponse> response = entity.stream()
                .map(userEntity -> UserMapper.toResponse(userEntity))
                .toList();
        return ResponseEntity.status(HttpStatus.OK).body(response);
    }

    @GetMapping("/{id}")
    ResponseEntity<UserResponse> getUserById(@PathVariable UUID id) {
        return userService.findById(id)
                .map(user -> ResponseEntity.status(HttpStatus.OK).body(UserMapper.toResponse(user)))
                .orElse(ResponseEntity.status(HttpStatus.NOT_FOUND).build());
    }

    @PutMapping("/{id}")
    ResponseEntity<UserResponse> updateUserById(@PathVariable UUID id, @RequestBody UserRequest user) {
        return userService.updateById(id, UserMapper.toEntity(user))
                .map(updatedUser -> ResponseEntity.status(HttpStatus.OK).body(UserMapper.toResponse(updatedUser)))
                .orElse(ResponseEntity.status(HttpStatus.NOT_FOUND).build());
    }

    @DeleteMapping("/{id}")
    ResponseEntity<Void> deleteUserById(@PathVariable UUID id) {
        userService.deleteById(id);
        return ResponseEntity.status(HttpStatus.NO_CONTENT).build();
    }

}
