package com.samiul.tach.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.samiul.tach.model.User;
import lombok.AllArgsConstructor;
import lombok.Data;

import java.time.Instant;

@Data
@AllArgsConstructor
public class UserResponse {
    @JsonProperty("_id")
    private String id;
    private String fullName;
    private String email;
    private String profilePic;

    private Instant createdAt;

    public UserResponse(User user) {
        this.id = user.getId();
        this.fullName = user.getFullName();
        this.email = user.getEmail();
        this.profilePic = user.getProfilePic();

        this.createdAt = user.getCreatedAt();
    }
}
