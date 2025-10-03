package dev.lucas.user.controller;

import dev.lucas.user.dto.UserRequest;
import dev.lucas.user.dto.UserResponse;
import dev.lucas.user.entity.UserEntity;
import dev.lucas.user.service.UserService;
import lombok.RequiredArgsConstructor;

import org.springframework.beans.BeanUtils;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;


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
}
