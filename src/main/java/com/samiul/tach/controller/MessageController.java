package com.samiul.tach.controller;

import com.samiul.tach.dto.UserResponse;
import com.samiul.tach.model.Message;
import com.samiul.tach.model.User;
import com.samiul.tach.service.MessageService;
import lombok.AllArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/api/messages")
@AllArgsConstructor
public class MessageController {
    private final MessageService messageService;

    @GetMapping("/users")
    public ResponseEntity<List<UserResponse>> getUsers(@AuthenticationPrincipal User user) {
        List<UserResponse> users = messageService.getUsers(user.getId());

        return ResponseEntity.ok(users);
    }

    @GetMapping("/{id}")
    public ResponseEntity<?> getMessages(@PathVariable("id") String userToChatId, @AuthenticationPrincipal User currentUser) {

        List<Message> messages = messageService.getMessages(currentUser.getId(), userToChatId);

        return ResponseEntity.ok(messages);
    }
}
