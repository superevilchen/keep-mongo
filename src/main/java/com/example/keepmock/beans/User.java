package com.example.keepmock.beans;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.index.Indexed;
import org.springframework.data.mongodb.core.mapping.Document;

import java.util.List;

@Document
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Data
public class User {

    @Id
    private String id;

    private String email;

    private String password;

    private List<Task> tasks;

    public User(String email, String password) {
        this(null, email, password, null);
    }

    public User(String email, String password, List<Task> tasks) {
        this(null, email, password, tasks);
    }
}
