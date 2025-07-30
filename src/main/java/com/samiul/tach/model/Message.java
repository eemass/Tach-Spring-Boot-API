package com.samiul.tach.model;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.time.Instant;

@Document(collection = "messages")
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Data
public class Message {

    @Id
    @JsonProperty("_id")
    private String id;

    private String senderId;
    private String receiverId;

    private String text;
    private String image;

    @CreatedDate
    private Instant createdDate;
}
