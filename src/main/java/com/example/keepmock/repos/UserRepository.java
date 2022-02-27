package com.example.keepmock.repos;

import com.example.keepmock.beans.User;
import org.springframework.data.mongodb.repository.MongoRepository;

import java.util.Optional;

public interface UserRepository extends MongoRepository<User, String> {

    Optional<User> findByEmailAndPassword(String email, String password);
}
