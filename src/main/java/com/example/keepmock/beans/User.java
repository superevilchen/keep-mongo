package com.example.keepmock.beans;

import lombok.*;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.index.Indexed;
import org.springframework.data.mongodb.core.mapping.Document;

import java.util.List;

@Document(value="users")
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Data
public class User {

    @Id
    private String id;

//    @Indexed(unique = true)
    private String email;

    private String password;

    public User(String email, String password) {
        this(null, email, password);
    }

    public User(String email, String password, List<Task> tasks) {
        this(null, email, password);
    }
}

// @DBRef
// private List<Task> tasks;