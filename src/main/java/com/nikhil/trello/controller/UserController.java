package com.nikhil.trello.controller;

import com.nikhil.trello.dto.UpdateUserRequest;
import com.nikhil.trello.dto.UserResponse;
import com.nikhil.trello.entity.User;
import com.nikhil.trello.service.UserService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

import java.util.UUID;

@RestController
@RequestMapping("/api/v1")
@RequiredArgsConstructor
public class UserController {

    private final UserService userService;


    @GetMapping("/users/{userId}")
    public ResponseEntity<UserResponse> getUser(
    @PathVariable UUID userId,
    @AuthenticationPrincipal UUID authenticatedUserId
    ){
        if(!userId.equals(authenticatedUserId)){
            throw new AccessDeniedException("Cannot update another user");
        }

        User user = userService.getUserById(userId);
        UserResponse response = new UserResponse(userId, user.getName(), user.getEmail(), user.getCreatedAt());
        return ResponseEntity.status(HttpStatus.OK).body(response);
    }

    @PatchMapping("/users/{userId}")
    public ResponseEntity<UserResponse>updateUserName(
            @PathVariable UUID userId,
            @AuthenticationPrincipal UUID authenticatedUserId,
            @Valid @RequestBody UpdateUserRequest request
    ) {
        if(!userId.equals(authenticatedUserId)){
            throw new AccessDeniedException("Cannot update another user");
        }

        User user = userService.updateUserName(userId, request.name());

        UserResponse response = new UserResponse(userId, user.getName(), user.getEmail(), user.getCreatedAt());
        return ResponseEntity.status(HttpStatus.OK).body(response);
    }

}
