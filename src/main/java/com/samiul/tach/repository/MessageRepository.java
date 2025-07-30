package com.samiul.tach.repository;

import com.samiul.tach.model.Message;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.data.mongodb.repository.Query;

import java.util.List;

public interface MessageRepository extends MongoRepository<Message, String> {

    @Query("{ '$or': [ { 'senderId': ?0, 'receiverId': ?1 }, { 'receiverId' : ?0, 'senderId': ?1 } ] }")
    public List<Message> findMessagesBetween(String myId, String userToChatId);
}
