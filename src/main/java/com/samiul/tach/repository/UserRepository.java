package com.samiul.tach.repository;

import com.samiul.tach.model.User;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.data.mongodb.repository.Query;

import java.util.List;
import java.util.Optional;

public interface UserRepository extends MongoRepository<User, String> {
    Optional<User> findByEmail(String email);

    @Query(value = "{ '_id': { $ne: ?0 } }", fields = "{ password: 0 }")
    List<User> findAllExceptUserExcludingPassword(String userId);
}
