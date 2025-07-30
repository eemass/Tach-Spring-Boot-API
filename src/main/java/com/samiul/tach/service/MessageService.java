package com.samiul.tach.service;

import com.samiul.tach.dto.UserResponse;
import com.samiul.tach.model.Message;
import com.samiul.tach.model.User;
import com.samiul.tach.repository.MessageRepository;
import com.samiul.tach.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class MessageService {
    private final UserRepository userRepository;
    private final MessageRepository messageRepository;

    public List<UserResponse> getUsers(String userId) {
        List<User> users = userRepository.findAllExceptUserExcludingPassword(userId);

        return users.stream().map(UserResponse::new).collect(Collectors.toList());
    }

    public List<Message> getMessages(String currentUserId, String userToChatId) {

        return messageRepository.findMessagesBetween(currentUserId, userToChatId);
    }
}
